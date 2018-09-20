package jdregistry.client.data

import org.junit.Assert
import org.junit.Test

class DockerTagTests {

    @Test
    fun latest_tag_equality() {

        Assert.assertEquals(DockerTag.LATEST, DockerTag.of("latest"))
    }

    @Test
    fun latest_tag_identity() {

        Assert.assertTrue(DockerTag.LATEST === DockerTag.of("latest"))
    }
}
