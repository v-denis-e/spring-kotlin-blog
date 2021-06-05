package kz.vdenise.springkotlinblog.db.repository

import kz.vdenise.springkotlinblog.db.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserEntityRepository: JpaRepository<UserEntity, Int> {
    fun findByEmail(email: String): Optional<UserEntity>
}