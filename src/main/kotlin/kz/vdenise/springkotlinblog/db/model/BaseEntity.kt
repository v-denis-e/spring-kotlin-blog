package kz.vdenise.springkotlinblog.db.model

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Базовый класс для всех сущностей, которым необходим
 * автоматически генерируемый идентификатор
 */
@MappedSuperclass
open class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

}