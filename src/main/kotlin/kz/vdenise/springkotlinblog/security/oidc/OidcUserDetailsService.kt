package kz.vdenise.springkotlinblog.security.oidc

import kz.vdenise.springkotlinblog.db.model.RoleEntity
import kz.vdenise.springkotlinblog.db.model.UserEntity
import kz.vdenise.springkotlinblog.db.repository.RoleEntityRepository
import kz.vdenise.springkotlinblog.db.repository.UserEntityRepository
import kz.vdenise.springkotlinblog.security.Roles
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

/**
 * Сервис при успешной аутентификации у Identity Provider
 * обрабатывает OpenIDConnect запрос, чтобы найти или создать
 * пользователя в собственной базе данных.
 */
@Service
class OidcUserDetailsService(
    private val users: UserEntityRepository,
    private val roles: RoleEntityRepository
) : OAuth2UserService<OidcUserRequest, OidcUser> {

    override fun loadUser(request: OidcUserRequest?): OidcUser {
        // Дополнительная проверка "на всякий случай" для компилятора kotlin
        if (request == null) {
            throw RuntimeException("Unexpected null on process Oidc user request!")
        }

        val email = request.idToken.email
        val lastName = request.idToken.familyName
        val firstName = request.idToken.givenName

        val existingUser = users.findByEmail(email)
            .orElseGet { createNewUser(email, firstName, lastName) }

        return UserDetails(email, toAuthorities(existingUser.roles), request)
    }

    private fun createNewUser(email: String, firstName: String, lastName: String): UserEntity {
        val newUser = UserEntity(email, firstName, lastName)
        addDefaultRole(newUser)
        return users.save(newUser)
    }

    private fun addDefaultRole(user: UserEntity): UserEntity {
        val defaultRole = roles.findByName(Roles.USER)
            .orElseThrow { RuntimeException("Standard role not found!") }

        user.addRole(defaultRole)

        return user
    }

    private fun toAuthorities(roles: List<RoleEntity>) =
        roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }.toMutableList()

}