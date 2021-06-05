package kz.vdenise.springkotlinblog.db.repository

import kz.vdenise.springkotlinblog.db.model.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleEntityRepository: JpaRepository<RoleEntity, Int> {
    fun findByName(name: String): Optional<RoleEntity>
}