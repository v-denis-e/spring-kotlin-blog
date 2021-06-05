package kz.vdenise.springkotlinblog.db.model

import javax.persistence.*

@Entity
@Table(schema = "blog", name = "user")
class UserEntity(): BaseEntity() {

    @Column(length = 512, nullable = false)
    var email: String? = null
    @Column(length = 512, nullable = false)
    var firstName: String? = null
    @Column(length = 512, nullable = false)
    var lastName: String? = null
    @Column(length = 500)
    var bio: String? = null
    @Lob
    var picture: ByteArray? = null

    @Column(nullable = false)
    var blocked: Boolean = false

    @ManyToMany
    @JoinTable(
        schema = "blog",
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableList<RoleEntity> = mutableListOf()

    constructor(
        email: String,
        firstName: String,
        lastName: String,
        bio: String? = null,
        picture: ByteArray? = null,
        blocked: Boolean = false,
        roles: MutableList<RoleEntity> = mutableListOf()
    ): this() {
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.bio = bio
        this.picture = picture
        this.blocked = blocked
        this.roles = roles
    }

    fun addRole(role: RoleEntity) {
        this.roles.add(role)
    }

}