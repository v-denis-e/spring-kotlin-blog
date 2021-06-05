package kz.vdenise.springkotlinblog.core.usecase

import kz.vdenise.springkotlinblog.core.exception.ArticleNotFoundException
import kz.vdenise.springkotlinblog.core.gateway.ArticleGateway
import kz.vdenise.springkotlinblog.core.model.PageModel
import kz.vdenise.springkotlinblog.core.model.article.ArticleSearchParameters
import kz.vdenise.springkotlinblog.core.model.article.ArticleState
import kz.vdenise.springkotlinblog.core.model.article.ModifyArticleCommand
import org.assertj.core.api.Assertions.assertThat
import org.h2.util.MathUtils.randomInt
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ArticlesManagementTest {

    companion object {
        const val ARTICLE_NOT_FOUND = true
    }

    @Mock
    internal lateinit var gateway: ArticleGateway
    @InjectMocks
    internal lateinit var articles: Articles

    @Test
    internal fun `create must call gateway's create`() {
        val userId = 123
        val command = testArticleCommand()
        val articleId = randomInt(150)
        given(gateway.create(userId, command))
            .willReturn(articleId)

        val actual = articles.create(userId, command)

        then(gateway).should().create(userId, command)
        assertThat(actual).isEqualTo(articleId)
    }

    @Test
    internal fun `update must call gateway's update`() {
        val userId = 123
        val command = testArticleCommand()
        val articleId = 321

        withMockedGateway(userId, articleId) {
            articles.update(userId, articleId, command)

            then(gateway).should().update(articleId, command)
        }
    }

    @Test
    internal fun `update must throw exception if requested article does not exist`() {
        val userId = 123
        val command = testArticleCommand()
        val articleId = 321

        withMockedGateway(userId, articleId, ARTICLE_NOT_FOUND) {

            assertThrows<ArticleNotFoundException> {
                articles.update(userId, articleId, command)
            }

        }
    }

    @Test
    internal fun `sendToAccepting must call gateway's changeState`() {
        val state = ArticleState.PENDING_ACCEPTANCE

        assertUpdatesStateTo(state) { userId, articleId ->
            articles.sendToAccepting(userId, articleId)
        }
    }

    @Test
    internal fun `sendToAccepting must throw exception if article not found`() {
        val state = ArticleState.PENDING_ACCEPTANCE

        assertUpdatesStateTo(state, ARTICLE_NOT_FOUND) { userId, articleId ->
            assertThrows<ArticleNotFoundException> {
                articles.sendToAccepting(userId, articleId)
            }
        }
    }

    @Test
    internal fun `accept must call gateway's changeState`() {
        val state = ArticleState.ACCEPTED

        assertUpdatesStateTo(state) { userId, articleId ->
            articles.accept(userId, articleId)
        }
    }

    @Test
    internal fun `accept must throw exception if article not found`() {
        val state = ArticleState.ACCEPTED

        assertUpdatesStateTo(state, ARTICLE_NOT_FOUND) { userId, articleId ->
            assertThrows<ArticleNotFoundException> {
                articles.accept(userId, articleId)
            }
        }
    }

    @Test
    internal fun `publish must call gateway's changeState`() {
        val state = ArticleState.PUBLISHED

        assertUpdatesStateTo(state) { userId, articleId ->
            articles.publish(userId, articleId)
        }
    }

    @Test
    internal fun `publish must throw exception if article not found`() {
        val state = ArticleState.PUBLISHED

        assertUpdatesStateTo(state, ARTICLE_NOT_FOUND) { userId, articleId ->
            assertThrows<ArticleNotFoundException> {
                articles.publish(userId, articleId)
            }
        }
    }

    @Test
    internal fun `withdraw must call gateway's changeState`() {
        val state = ArticleState.WITHDRAWN

        assertUpdatesStateTo(state) { userId, articleId ->
            articles.withdraw(userId, articleId)
        }
    }

    @Test
    internal fun `withdraw must throw exception if article not found`() {
        val state = ArticleState.WITHDRAWN

        assertUpdatesStateTo(state, ARTICLE_NOT_FOUND) { userId, articleId ->
            assertThrows<ArticleNotFoundException> {
                articles.withdraw(userId, articleId)
            }
        }
    }

    @Test
    internal fun `like must call gateway's like`() {
        val userId = 123
        val articleId = 321

        withMockedGateway(articleId = articleId) {
            articles.like(userId, articleId)

            then(gateway).should().like(userId, articleId)
        }
    }

    @Test
    internal fun `like must throw exception if article not found`() {
        val userId = 123
        val articleId = 312

        withMockedGateway(articleId = articleId, empty = ARTICLE_NOT_FOUND) {
            assertThrows<ArticleNotFoundException> {
                articles.like(userId, articleId)
            }
        }
    }

    @Test
    internal fun `delete must call gateway's delete`() {
        val userId = 123
        val articleId = 321

        withMockedGateway(userId, articleId) {
            articles.delete(userId, articleId)

            then(gateway).should().delete(articleId)
        }
    }

    @Test
    internal fun `delete must throw exception if article not found`() {
        val userId = 123
        val articleId = 534

        withMockedGateway(userId, articleId, ARTICLE_NOT_FOUND) {
            assertThrows<ArticleNotFoundException> {
                articles.delete(userId, articleId)
            }
        }
    }

    private fun assertUpdatesStateTo(
        state: ArticleState,
        empty: Boolean = false,
        `when`: (Int, Int) -> Unit
    ) {
        val userId = randomInt(150)
        val articleId = randomInt(150)

        withMockedGateway(userId, articleId, empty) {
            `when`(userId, articleId)

            if (empty) {
                then(gateway).should(never()).changeState(articleId, state)
            } else {
                then(gateway).should().changeState(articleId, state)
            }
        }
    }

    private fun withMockedGateway(
        userId: Int? = null,
        articleId: Int,
        empty: Boolean = false,
        `when`: () -> Unit
    ) {
        val params = ArticleSearchParameters(id = articleId, authorId = userId)
        val article = testArticle()
        given(gateway.search(params)).willReturn(PageModel(
            if (empty) listOf() else listOf(article)
        ))

        `when`()

        then(gateway).should().search(params)
    }

}