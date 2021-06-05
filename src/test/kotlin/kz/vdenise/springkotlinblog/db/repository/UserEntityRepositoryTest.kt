package kz.vdenise.springkotlinblog.db.repository

import kz.vdenise.springkotlinblog.db.model.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class UserEntityRepositoryTest {

    @Autowired
    internal lateinit var repository: UserEntityRepository

    @AfterEach
    internal fun tearDown() {
        repository.deleteAll()
    }

    @Test
    internal fun `find by email`() {
        val expected = repository.save(
            UserEntity("test@example.com", "test", "test")
        )
        repository.save(UserEntity("unexpected", "unexpected", "unexpected"))

        val actual = repository.findByEmail(expected.email!!)

        assertThat(actual).isPresent.contains(expected)
    }
}