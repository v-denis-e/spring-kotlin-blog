package kz.vdenise.springkotlinblog.core.usecase

import kz.vdenise.springkotlinblog.core.exception.TopicNotFoundException
import kz.vdenise.springkotlinblog.core.gateway.TopicGateway
import kz.vdenise.springkotlinblog.core.model.Topic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class TopicsTest {

    @Mock
    internal lateinit var gateway: TopicGateway
    @InjectMocks
    internal lateinit var topics: Topics

    @Test
    internal fun `all must call gateway's all`() {
        val expected = Topic(1, "test")
        given(gateway.all())
            .willReturn(listOf(expected))

        val actual = topics.all()

        then(gateway).should().all()
        assertThat(actual).hasSize(1).contains(expected)
    }

    @Test
    internal fun `get by id must call gateway's getById`() {
        val expected = Topic(1, "test")
        given(gateway.getById(expected.id))
            .willReturn(expected)

        val actual = topics.getById(expected.id)

        then(gateway).should().getById(expected.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `get by id must throw exception when topic does not exist`() {
        val invalid = 123
        given(gateway.getById(invalid))
            .willReturn(null)

        assertThrows<TopicNotFoundException> {
            topics.getById(invalid)
        }
    }
}