package kz.vdenise.springkotlinblog.core.usecase

import kz.vdenise.springkotlinblog.core.exception.TopicNotFoundException
import kz.vdenise.springkotlinblog.core.gateway.TopicGateway
import kz.vdenise.springkotlinblog.core.model.Topic

/**
 * Варианты использования тематик
 */
class Topics(
    private val gateway: TopicGateway
) {

    fun all(): List<Topic> = gateway.all()

    fun getById(id: Int): Topic =
        gateway.getById(id) ?: throw TopicNotFoundException(id)

}