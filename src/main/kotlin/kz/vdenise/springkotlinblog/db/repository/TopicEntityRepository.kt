package kz.vdenise.springkotlinblog.db.repository

import kz.vdenise.springkotlinblog.db.model.TopicEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicEntityRepository : JpaRepository<TopicEntity, Int>
