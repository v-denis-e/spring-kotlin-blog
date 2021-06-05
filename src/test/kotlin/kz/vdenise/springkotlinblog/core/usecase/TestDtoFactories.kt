package kz.vdenise.springkotlinblog.core.usecase

import kz.vdenise.springkotlinblog.core.model.Topic
import kz.vdenise.springkotlinblog.core.model.User
import kz.vdenise.springkotlinblog.core.model.article.Article
import kz.vdenise.springkotlinblog.core.model.article.ModifyArticleCommand
import org.h2.util.MathUtils
import java.time.LocalDateTime

fun testArticle() =
    Article(
        id = MathUtils.randomInt(1000),
        title = "test",
        annotation = "test",
        topic = Topic(1, "test"),
        author = User("test", "test", "test"),
        content = "test",
        createdDate = LocalDateTime.now(),
        lastModifiedDate = LocalDateTime.now(),
        publishedDate = LocalDateTime.now()
    )

fun testArticleCommand() =
    ModifyArticleCommand(
        title = "test",
        annotation = "test",
        topicId = 3,
        content = "some"
    )
