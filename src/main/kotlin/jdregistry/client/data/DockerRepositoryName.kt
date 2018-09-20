package jdregistry.client.data

/**
 * Represents a Docker Repository Name, as specified by the official V2 API of Docker Registry
 *
 * @author Lukas Zimmermann
 * @since 0.0.3
 */
data class DockerRepositoryName(
    val firstPathComponent: String,
    private val moreComponents: List<String> = emptyList()
) {

    /**
     * Convenient Constructor to also allow greating [DockerRepositoryName] instance by supplying the
     * String representation of the repository
     *
     */
    constructor(input: String) : this(input.split(SEP)[0], input.split(SEP).drop(1))

    init {
        // First component must be a valid path component
        require(isValidPathComponent(firstPathComponent)) {

            "The first Path Component of the DockerRepositoryName is not valid: $firstPathComponent"
        }
        require(moreComponents.all { isValidPathComponent(it) }) {

            "One of the additional Path Components is not valid"
        }

        // The total length is determined by the length of the first component plus the total
        // length of the other comoponents plus the number of '/' signs, which is equal to the
        // number of moreComponents (as each of those is prefixed by '/)'
        val totalLength = firstPathComponent.length + moreComponents.sumBy { it.length } + moreComponents.size

        require(totalLength < 256) {

            "The maximal length of the DockerRepositoryName is exceeded. Allowed: 255, have: $totalLength"
        }
    }

    val numberOfComponents = 1 + moreComponents.size
    val hasMoreComponents = numberOfComponents > 1

    operator fun get(index: Int) = if (index == 0) firstPathComponent else moreComponents[index]

    fun asString() = firstPathComponent +
            moreComponents
                    .joinToString(SEP)
                    .let { if (it.isEmpty()) "" else "$SEP$it" } // append separator

    override fun toString() = asString()

    fun resolve(tag: DockerTag = DockerTag.LATEST, host: String? = null): String {

        // Ensures that if the host is not null that the string will be terminated by one single /
        val hostPrefix = host?.let { if (it.endsWith(SEP)) it else "$it$SEP" } ?: ""

        return "$hostPrefix${this.asString()}:${tag.repr}"
    }

    private companion object {

        const val SEP = "/"
        val pathComponentRegex = Regex("[a-z0-9]+(?:[._-][a-z0-9]+)*")
        fun isValidPathComponent(item: String) = item.matches(pathComponentRegex)
    }
}
