package kz.vdenise.springkotlinblog.core.model.article

import kz.vdenise.springkotlinblog.core.model.Topic
import java.time.LocalDateTime

/**
 * Краткая информация по статье для автора статьи.
 */
data class ShortOwnArticle(
    val id: Int,
    val title: String,
    val pictureUri: String? = null,
    val annotation: String,
    val tags: List<String> = listOf(),
    val topic: Topic,
    val likes: Long = 0,
    val state: ArticleState,
    val createdDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime
)