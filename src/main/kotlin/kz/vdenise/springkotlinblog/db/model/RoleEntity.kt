package kz.vdenise.springkotlinblog.db.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "blog", name = "role")
class RoleEntity(): BaseEntity() {

    @Column(length = 20, nullable = false)
    var name: String? = null

    constructor(id: Int? = null, name: String): this() {
        this.id = id
        this.name = name
    }

}