package kz.vdenise.springkotlinblog.core.model.article

data class ArticleSearchParameters(
    val id: Int? = null,
    val title: String? = null,
    val tags: List<String> = listOf(),
    val topicId: Int? = null,
    val authorId: Int? = null,
    val state: ArticleState? = null,
    val page: Int = 0,
    val sortMode: ArticlesSortMode = ArticlesSortMode.LATEST
)

enum class ArticlesSortMode {
    LATEST, MOST_POPULAR
}
