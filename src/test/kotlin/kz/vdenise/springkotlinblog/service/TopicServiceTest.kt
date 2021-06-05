package kz.vdenise.springkotlinblog.service

import kz.vdenise.springkotlinblog.core.model.Topic
import kz.vdenise.springkotlinblog.db.model.TopicEntity
import kz.vdenise.springkotlinblog.db.repository.TopicEntityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class TopicServiceTest {

    @Mock
    internal lateinit var repository: TopicEntityRepository

    @InjectMocks
    internal lateinit var service: TopicService

    @Test
    internal fun `all must call repository's findAll`() {
        val expected = TopicEntity(1, "test")
        given(repository.findAll())
            .willReturn(listOf(expected))

        val actual = service.all()

        then(repository).should().findAll()
        assertThat(actual).hasSize(1)
        val topic = actual[0]
        assertTopicsEquals(topic, expected)
    }

    @Test
    internal fun `get by id must call repository's findById`() {
        val expected = TopicEntity(1, "test")
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        val actual = service.getById(expected.id!!)

        actual?.let { assertTopicsEquals(it, expected) }
            ?: fail { "Actual topic cannot be null!" }
    }



    private fun assertTopicsEquals(actual: Topic, expected: TopicEntity) {
        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.name).isEqualTo(expected.name)
    }

}