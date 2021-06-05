package kz.vdenise.springkotlinblog.security.oidc

import kz.vdenise.springkotlinblog.db.model.RoleEntity
import kz.vdenise.springkotlinblog.db.model.UserEntity
import kz.vdenise.springkotlinblog.db.repository.RoleEntityRepository
import kz.vdenise.springkotlinblog.db.repository.UserEntityRepository
import kz.vdenise.springkotlinblog.security.Roles
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.StandardClaimNames
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class OidcUserDetailsServiceTest{

    @Mock
    internal lateinit var roleEntityRepository: RoleEntityRepository
    @Mock
    internal lateinit var userEntityRepository: UserEntityRepository
    @InjectMocks
    internal lateinit var service: OidcUserDetailsService

    @Test
    internal fun `load user must call findByEmail of user repository`() {
        val email = "test@example.con"

        withMockedFindByEmail(email, testUser(email)) { repository ->

            withTestRequest(email) { request ->
                service.loadUser(request)
            }

            then(repository).should().findByEmail(email)
        }
    }

    @Test
    internal fun `load user must create new one if requested user does not exist`() {
        val email = "newuser@example.com"

        withMockedFindByEmailAndSave(email, null) { repository ->

            withTestRequest(email) { request ->
                service.loadUser(request)
            }

            then(repository).should().save(any())
        }
    }

    @Test
    internal fun `load user with null request must throw runtime exception`() {
        assertThrows<RuntimeException> {
            service.loadUser(null)
        }
    }

    private fun withTestRequest(email: String, func: (OidcUserRequest) -> Unit) {
        func(OidcUserRequest(createRegistration(), createAccessToken(), createOidcToken(email)))
    }

    private fun createRegistration() =
        ClientRegistration.withRegistrationId("123")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientId("test")
            .redirectUri("test")
            .authorizationUri("test")
            .tokenUri("test")
            .build()

    private fun createAccessToken() =
        OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            "some token",
            Instant.MIN,
            Instant.MAX
        )

    private fun createOidcToken(email: String) =
        OidcIdToken(
            "some toke",
            Instant.MIN,
            Instant.MAX,
            mapOf(
                StandardClaimNames.EMAIL to email,
                StandardClaimNames.FAMILY_NAME to "test",
                StandardClaimNames.GIVEN_NAME to "test"
            )
        )

    private fun withMockedFindByEmailAndSave(
        email: String,
        result: UserEntity?,
        func: (UserEntityRepository) -> Unit
    ) {
        withMockedFindByEmail(email, result) { repository ->
            given(roleEntityRepository.findByName(Roles.USER))
                .willReturn(Optional.of(RoleEntity(1, Roles.USER)))
            given(repository.save(any())).will { it.arguments[0] }
            func(repository)
        }
    }

    private fun withMockedFindByEmail(
        email: String,
        result: UserEntity?,
        func: (UserEntityRepository) -> Unit
    ) {
        given(userEntityRepository.findByEmail(email))
            .willReturn(Optional.ofNullable(result))

        func(userEntityRepository)
    }

    private fun testUser(email: String) =
        UserEntity(email, "test", "test")

}