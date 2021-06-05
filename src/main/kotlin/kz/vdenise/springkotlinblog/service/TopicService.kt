package kz.vdenise.springkotlinblog.service

import kz.vdenise.springkotlinblog.core.gateway.TopicGateway
import kz.vdenise.springkotlinblog.core.model.Topic
import kz.vdenise.springkotlinblog.db.model.TopicEntity
import kz.vdenise.springkotlinblog.db.repository.TopicEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Сервис по умолчанию для работы с топиками, которые
 * хранятся в RDB через JPA.
 */
@Service
class TopicService(
    private val repository: TopicEntityRepository
) : TopicGateway {

    override fun all(): List<Topic> =
        repository.findAll().map { convert(it) }

    override fun getById(id: Int): Topic? =
        repository.findByIdOrNull(id)?.let { convert(it) }

    private fun convert(entity: TopicEntity): Topic =
        Topic(
            id = entity.id!!,
            name = entity.name!!
        )

}