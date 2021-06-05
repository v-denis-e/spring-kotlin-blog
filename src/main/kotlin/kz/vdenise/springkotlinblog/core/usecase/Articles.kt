package kz.vdenise.springkotlinblog.core.usecase

import kz.vdenise.springkotlinblog.core.converter.toPublishedArticle
import kz.vdenise.springkotlinblog.core.converter.toShortOwnPage
import kz.vdenise.springkotlinblog.core.converter.toShortPublishedPage
import kz.vdenise.springkotlinblog.core.exception.ArticleNotFoundException
import kz.vdenise.springkotlinblog.core.gateway.ArticleGateway
import kz.vdenise.springkotlinblog.core.model.PageModel
import kz.vdenise.springkotlinblog.core.model.article.*

/**
 * Варианты использование статей
 */
class Articles(
    private val gateway: ArticleGateway
) {

    /**
     * Поиск опубликованных статей для всех пользователей.
     */
    fun searchPublished(
        title: String? = null,
        tags: List<String> = listOf(),
        topicId: Int? = null,
        authorId: Int? = null,
        page: Int = 0,
        sortMode: ArticlesSortMode = ArticlesSortMode.LATEST
    ): PageModel<ShortPublishedArticle> {
        val result = gateway.search(ArticleSearchParameters(
            title = title,
            tags = tags,
            topicId = topicId,
            authorId = authorId,
            page = page,
            sortMode = sortMode
        ))
        return toShortPublishedPage(result)
    }

    /**
     * Поиск собственных статей, на редактирование которых
     * у авторизованного пользователя есть права.
     */
    fun searchOwn(
        currentUserId: Int,
        title: String? = null,
        state: ArticleState? = null,
        page: Int = 0
    ): PageModel<ShortOwnArticle> {
        val result = gateway.search(ArticleSearchParameters(
            title = title,
            authorId = currentUserId,
            state = state,
            page = page
        ))
        return toShortOwnPage(result)
    }

    /**
     * Возвращает полное содержание опубликованной статьи для
     * всех пользователей.
     */
    fun getPublishedArticle(id: Int): PublishedArticle {
        val result = gateway.search(ArticleSearchParameters(id = id))
        return toPublishedArticle(extractOneArticle(result))
    }

    /**
     * Возвращает полное содержание статьи для автора статьи.
     */
    fun getOwnArticle(currentUserId: Int, id: Int): Article {
        val result = gateway.search(ArticleSearchParameters(id = id, authorId = currentUserId))
        return extractOneArticle(result)
    }

    private fun extractOneArticle(page: PageModel<Article>): Article {
        if (page.data.isEmpty()) {
            throw ArticleNotFoundException()
        }
        return page.data[0]
    }

    fun create(currentUserId: Int, command: ModifyArticleCommand): Int
        = gateway.create(currentUserId, command)

    fun update(currentUserId: Int, id: Int, command: ModifyArticleCommand) {
        getOwnArticle(currentUserId, id)
        gateway.update(id, command)
    }

    fun sendToAccepting(currentUserId: Int, id: Int) {
        getOwnArticle(currentUserId, id)
        gateway.changeState(id, ArticleState.PENDING_ACCEPTANCE)
    }

    fun accept(currentUserId: Int, id: Int) {
        getOwnArticle(currentUserId, id)
        gateway.changeState(id, ArticleState.ACCEPTED)
    }

    fun publish(currentUserId: Int, id: Int) {
        getOwnArticle(currentUserId, id)
        gateway.changeState(id, ArticleState.PUBLISHED)
    }

    fun withdraw(currentUserId: Int, id: Int) {
        getOwnArticle(currentUserId, id)
        gateway.changeState(id, ArticleState.WITHDRAWN)
    }

    fun like(currentUserId: Int, id: Int) {
        getPublishedArticle(id)
        gateway.like(currentUserId, id)
    }

    fun delete(currentUserId: Int, id: Int) {
        getOwnArticle(currentUserId, id)
        gateway.delete(id)
    }

}
