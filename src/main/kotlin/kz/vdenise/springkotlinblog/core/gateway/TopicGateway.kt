package kz.vdenise.springkotlinblog.core.gateway

import kz.vdenise.springkotlinblog.core.model.Topic

interface TopicGateway {

    fun all(): List<Topic>

    fun getById(id: Int): Topic?

}