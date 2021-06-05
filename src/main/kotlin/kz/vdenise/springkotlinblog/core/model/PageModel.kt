package kz.vdenise.springkotlinblog.core.model

data class PageModel<T>(
    val data: List<T>,
    val page: Int = 0,
    val size: Int = 0,
    val totalPages: Int = 0,
    val totalElements: Long = 0
)