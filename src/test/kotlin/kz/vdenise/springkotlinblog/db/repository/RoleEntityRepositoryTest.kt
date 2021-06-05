package kz.vdenise.springkotlinblog.db.repository

import kz.vdenise.springkotlinblog.db.model.RoleEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RoleEntityRepositoryTest {

    @Autowired
    internal lateinit var repository: RoleEntityRepository

    @AfterEach
    internal fun tearDown() {
        repository.deleteAll()
    }

    @Test
    internal fun `find by name`() {
        val expected = repository.save(RoleEntity(name = "expected"))
        repository.save(RoleEntity(name = "unexpected"))

        val actual = repository.findByName(expected.name!!)

        assertThat(actual).isPresent.contains(expected)
    }
}