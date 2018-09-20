package jdregistry.client.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DockerRepositoryNameTests {

    private lateinit var repos: List<DockerRepositoryName>
    private lateinit var tags: List<DockerTag>

    @Before
    fun before() {

        repos = listOf(
                DockerRepositoryName("namespace/repo1"),
                DockerRepositoryName("repo2")
        )
        tags = listOf(
                DockerTag.LATEST,
                DockerTag.of("other")
        )
    }

    @Test
    fun resolve_tag() {

        val expected = listOf(
                "namespace/repo1:latest",
                "namespace/repo1:other",
                "repo2:latest",
                "repo2:other"
        )
        cross(repos, tags).forEachIndexed { index, pair ->

            Assert.assertEquals(pair.first.resolve(pair.second), expected[index])
        }
    }
}
