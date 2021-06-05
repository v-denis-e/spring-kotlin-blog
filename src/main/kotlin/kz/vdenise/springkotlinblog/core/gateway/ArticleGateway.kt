package kz.vdenise.springkotlinblog.core.gateway

import kz.vdenise.springkotlinblog.core.model.PageModel
import kz.vdenise.springkotlinblog.core.model.article.Article
import kz.vdenise.springkotlinblog.core.model.article.ArticleSearchParameters
import kz.vdenise.springkotlinblog.core.model.article.ArticleState
import kz.vdenise.springkotlinblog.core.model.article.ModifyArticleCommand

interface ArticleGateway {

    fun search(parameters: ArticleSearchParameters): PageModel<Article>

    fun create(authorId: Int, command: ModifyArticleCommand): Int

    fun update(id: Int, command: ModifyArticleCommand)

    fun changeState(id: Int, newState: ArticleState)

    fun like(userId: Int, id: Int)

    fun delete(id: Int)

}