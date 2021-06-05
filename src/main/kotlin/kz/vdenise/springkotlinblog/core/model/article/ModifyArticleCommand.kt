package kz.vdenise.springkotlinblog.core.model.article

/**
 * Команда для создания/изменения статьи.
 */
class ModifyArticleCommand(
    val title: String,
    val picture: ByteArray? = null,
    val annotation: String,
    val tags: List<String> = listOf(),
    val topicId: Int,
    val content: String
)
