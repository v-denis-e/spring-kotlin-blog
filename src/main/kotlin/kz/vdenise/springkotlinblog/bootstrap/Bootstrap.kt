package kz.vdenise.springkotlinblog.bootstrap

import kz.vdenise.springkotlinblog.db.model.RoleEntity
import kz.vdenise.springkotlinblog.db.repository.RoleEntityRepository
import kz.vdenise.springkotlinblog.security.Roles
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Загрузка необходимых базовых данных при запуске со встроенной БД
 */
@Component
class Bootstrap(
    @Value("\${spring.datasource.platform}")
    private val platform: String?,
    private val roleEntityRepository: RoleEntityRepository
): CommandLineRunner {

    override fun run(vararg args: String?) {
        if (platform == "h2") {
            roleEntityRepository.save(RoleEntity(name = Roles.USER))
        }
    }

}