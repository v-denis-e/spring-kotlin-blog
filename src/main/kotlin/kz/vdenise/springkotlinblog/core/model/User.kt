package kz.vdenise.springkotlinblog.core.model

/**
 * Представляет авторизованного пользователя блога.
 */
data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val bio: String? = null,
    val pictureUri: String? = null
)
