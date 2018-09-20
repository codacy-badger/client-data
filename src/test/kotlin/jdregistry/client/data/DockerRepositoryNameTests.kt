package jdregistry.client.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

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

    @Test
    fun some_valid_repos() {

        DockerRepositoryName("foo/bar")
        DockerRepositoryName("foo")
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalid_first_path_1() {

        DockerRepositoryName(invalidIdentifier)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalid_first_path_2() {

        DockerRepositoryName(EMPTY)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalid_second_path_1() {

        DockerRepositoryName("jboss", listOf(EMPTY))
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalid_second_path_2() {

        DockerRepositoryName("jboss", listOf(invalidIdentifier))
    }

    @Test(expected = IllegalArgumentException::class)
    fun too_long_repository_name() {

        DockerRepositoryName("a".repeat(400))
    }

    @Test
    fun get() {

        val repo0 = repos[0]
        val repo1 = repos[1]
        Assert.assertEquals(repo0[0], repo0.firstPathComponent)
        Assert.assertEquals(repo1[0], repo1.firstPathComponent)
        Assert.assertEquals(repo0[0], "namespace")
        Assert.assertEquals(repo1[0], "repo2")
        Assert.assertEquals(repo0[1], "repo1")
    }

    @Test
    fun to_string() {

        val repo0 = repos[0]
        val repo1 = repos[1]
        Assert.assertEquals(repo0.toString(), "namespace/repo1")
        Assert.assertEquals(repo1.toString(), "repo2")
    }

    @Test
    fun as_string() {

        val repo0 = repos[0]
        val repo1 = repos[1]
        Assert.assertEquals(repo0.asString(), "namespace/repo1")
        Assert.assertEquals(repo1.asString(), "repo2")
    }

    @Test
    fun to_string_and_as_string_are_the_same() {

        val repo0 = repos[0]
        val repo1 = repos[1]
        Assert.assertEquals(repo0.asString(), repo0.toString())
        Assert.assertEquals(repo1.asString(), repo1.toString())
    }
}
