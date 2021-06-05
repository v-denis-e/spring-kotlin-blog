package kz.vdenise.springkotlinblog.core.model

import java.time.LocalDateTime

data class Article(
    val id: Int? = null,
    val title: String,
    val pictureUri: String? = null,
    val annotation: String,
    val tags: List<String> = listOf(),
    val likes: Long = 0,
    val author: User,
    val content: String,
    val state: ArticleState = ArticleState.CREATED,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null,
    val publishedDate: LocalDateTime? = null
)

/**
 * Возможные статусы статьи.
 */
enum class ArticleState {
    /**
     * Статус присваивается по умолчанию для всех новых статей.
     */
    CREATED,

    /**
     * Автор отправил запрос на проверку к редактору.
     */
    PENDING_ACCEPTANCE,

    /**
     * Редактор одобрил статью.
     */
    ACCEPTED,

    /**
     * Статья была опубликована.
     */
    PUBLISHED,

    /**
     * Статья была отозвана автором или редактором.
     */
    WITHDRAWN
}
