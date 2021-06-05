package kz.vdenise.springkotlinblog.core.model

import java.time.LocalDateTime

data class Comment(
    val id: Int? = null,
    val author: User,
    val text: String,
    val articleId: Int,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null
)