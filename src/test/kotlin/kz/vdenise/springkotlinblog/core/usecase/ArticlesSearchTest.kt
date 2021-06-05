package kz.vdenise.springkotlinblog.core.usecase

import kz.vdenise.springkotlinblog.core.exception.ArticleNotFoundException
import kz.vdenise.springkotlinblog.core.gateway.ArticleGateway
import kz.vdenise.springkotlinblog.core.model.PageModel
import kz.vdenise.springkotlinblog.core.model.Topic
import kz.vdenise.springkotlinblog.core.model.User
import kz.vdenise.springkotlinblog.core.model.article.Article
import kz.vdenise.springkotlinblog.core.model.article.ArticleSearchParameters
import kz.vdenise.springkotlinblog.core.model.article.ArticleState
import kz.vdenise.springkotlinblog.core.model.article.ArticlesSortMode
import org.assertj.core.api.Assertions.assertThat
import org.h2.util.MathUtils.randomInt
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
internal class ArticlesSearchTest {

    companion object {
        const val EMPTY_DATA = true
    }

    @Mock
    internal lateinit var gateway: ArticleGateway
    @InjectMocks
    internal lateinit var articles: Articles

    @Test
    internal fun `searchPublished must call gateway's search`() {
        val params = ArticleSearchParameters(
            title = "test",
            tags = listOf("Test"),
            topicId = 1,
            authorId = 2,
            page = 3,
            sortMode = ArticlesSortMode.MOST_POPULAR
        )

        withMockedGateway(params) { expected ->
            val actual = articles.searchPublished(
                params.title,
                params.tags,
                params.topicId,
                params.authorId,
                params.page,
                params.sortMode
            ).data

            assertThat(actual).hasSize(1)
            val actualArticle = actual[0]
            assertThat(actualArticle.id).isEqualTo(expected!!.id)
        }
    }

    @Test
    internal fun `searchOwn must call gateway's search`() {
        val params = ArticleSearchParameters(
            title = "test",
            authorId = 123,
            state = ArticleState.PUBLISHED,
            page = 5
        )

        withMockedGateway(params) { expected ->
            val actual = articles.searchOwn(
                params.authorId!!,
                params.title,
                params.state,
                params.page
            ).data

            assertThat(actual).hasSize(1)
            val actualArticle = actual[0]
            assertThat(actualArticle.id).isEqualTo(expected!!.id)
        }
    }

    @Test
    internal fun `getPublishedArticle must call gateway's search`() {
        val params = ArticleSearchParameters(id = 123)

        withMockedGateway(params) { expected ->
            val actual = articles.getPublishedArticle(params.id!!)

            assertThat(actual.id).isEqualTo(expected!!.id)
        }
    }

    @Test
    internal fun `getPublishedArticle must throw exception if gateway returns empty data`() {
        val params = ArticleSearchParameters(id = 123)

        withMockedGateway(params, EMPTY_DATA) {

            assertThrows<ArticleNotFoundException> {
                articles.getPublishedArticle(params.id!!)
            }

        }
    }

    @Test
    internal fun `getOwnArticle must call gateway's search`() {
        val params = ArticleSearchParameters(id = 123, authorId = 321)

        withMockedGateway(params) { expected ->
            val actual = articles.getOwnArticle(params.authorId!!, params.id!!)

            assertThat(actual.id).isEqualTo(expected!!.id)
        }
    }

    @Test
    internal fun `getOwnArticle must throw exception if gateway returns empty data`() {
        val params = ArticleSearchParameters(id = 123, authorId = 321)

        withMockedGateway(params, EMPTY_DATA) {

            assertThrows<ArticleNotFoundException> {
                articles.getOwnArticle(params.authorId!!, params.id!!)
            }

        }
    }

    private fun withMockedGateway(
        params: ArticleSearchParameters,
        empty: Boolean = false,
        `when`: (Article?) -> Unit
    ) {
        val givenArticle = testArticle()
        given(gateway.search(params))
            .willReturn(PageModel(
                if (empty) listOf() else listOf(givenArticle)
            ))

        `when`(if (empty) null else givenArticle)

        then(gateway).should().search(params)
    }

}