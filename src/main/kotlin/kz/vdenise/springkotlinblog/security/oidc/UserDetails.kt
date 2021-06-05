package kz.vdenise.springkotlinblog.security.oidc

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

/**
 * Собственная реализация OidcUser для подмены
 * Authorities от Identity Provider на собственные.
 */
class UserDetails(
    private val userEmail: String,
    private val authorities: MutableList<out GrantedAuthority>,

    oidcUserRequest: OidcUserRequest
) : OidcUser {

    private val oidcIdToken: OidcIdToken = oidcUserRequest.idToken
    private val claims: MutableMap<String, Any> = HashMap(oidcUserRequest.idToken.claims)
    private val attributes: MutableMap<String, Any> = HashMap(
        oidcUserRequest.clientRegistration.providerDetails.configurationMetadata
    )

    override fun getName(): String = userEmail

    override fun getAttributes(): MutableMap<String, Any> = HashMap(attributes)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        ArrayList(authorities)


    override fun getClaims(): MutableMap<String, Any> = HashMap(claims)

    override fun getUserInfo(): OidcUserInfo = OidcUserInfo(claims)

    override fun getIdToken(): OidcIdToken = oidcIdToken

}