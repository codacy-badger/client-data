package jdregistry.client.data

import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException

class DockerTagTests {

    @Test
    fun latest_tag_equality() {

        Assert.assertEquals(DockerTag.LATEST, DockerTag.of("latest"))
    }

    @Test
    fun latest_tag_identity() {

        Assert.assertTrue(DockerTag.LATEST === DockerTag.of("latest"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalid_tag() {

        DockerTag.of(invalidIdentifier)
    }

    @Test(expected = IllegalArgumentException::class)
    fun too_long_tag() {

        DockerTag.of(tooLongIdentifier)
    }

    @Test(expected = IllegalArgumentException::class)
    fun empty_tag() {

        DockerTag.of(EMPTY)
    }
}
