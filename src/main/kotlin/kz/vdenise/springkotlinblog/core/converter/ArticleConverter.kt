package kz.vdenise.springkotlinblog.core.converter

import kz.vdenise.springkotlinblog.core.model.PageModel
import kz.vdenise.springkotlinblog.core.model.article.*


fun toShortPublishedPage(source: PageModel<Article>): PageModel<ShortPublishedArticle> =
    convertPageModel(source) { toShortPublishedArticle(it) }

private fun toShortPublishedArticle(source: Article): ShortPublishedArticle =
    ShortPublishedArticle(
        id = source.id,
        title = source.title,
        pictureUri = source.pictureUri,
        annotation = source.annotation,
        tags = source.tags,
        topic = source.topic,
        likes = source.likes,
        author = source.author,
        publishedDate = source.publishedDate!!
    )

fun toShortOwnPage(source: PageModel<Article>): PageModel<ShortOwnArticle> =
    convertPageModel(source) { toShortOwnArticle(it) }

private fun toShortOwnArticle(source: Article): ShortOwnArticle =
    ShortOwnArticle(
        id = source.id,
        title = source.title,
        pictureUri = source.pictureUri,
        annotation = source.annotation,
        tags = source.tags,
        topic = source.topic,
        likes = source.likes,
        state = source.state,
        createdDate = source.createdDate!!,
        lastModifiedDate = source.lastModifiedDate!!
    )

private fun <S, T> convertPageModel(source: PageModel<S>, convertData: (S) -> T): PageModel<T> =
    PageModel(
        data = source.data.map(convertData),
        page = source.page,
        size = source.size,
        totalPages = source.totalPages,
        totalElements = source.totalElements
    )

fun toPublishedArticle(source: Article): PublishedArticle =
    PublishedArticle(
        id = source.id,
        title = source.title,
        pictureUri = source.pictureUri,
        annotation = source.annotation,
        tags = source.tags,
        topic = source.topic,
        likes = source.likes,
        author = source.author,
        content = source.content,
        publishedDate = source.publishedDate
    )
