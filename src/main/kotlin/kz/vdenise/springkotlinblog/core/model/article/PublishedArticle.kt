package kz.vdenise.springkotlinblog.core.model.article

import kz.vdenise.springkotlinblog.core.model.Topic
import kz.vdenise.springkotlinblog.core.model.User
import java.time.LocalDateTime

data class PublishedArticle(
    val id: Int,
    val title: String,
    val pictureUri: String? = null,
    val annotation: String,
    val tags: List<String> = listOf(),
    val topic: Topic,
    val likes: Long = 0,
    val author: User,
    val content: String,
    val publishedDate: LocalDateTime? = null
)