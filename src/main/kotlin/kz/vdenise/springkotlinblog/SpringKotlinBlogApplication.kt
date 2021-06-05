package kz.vdenise.springkotlinblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class SpringKotlinBlogApplication {

	@GetMapping("/")
	fun hallo(@AuthenticationPrincipal oidcUser: OidcUser): String {
		return "Hi! ${oidcUser.fullName}!"
	}

}

fun main(args: Array<String>) {
	runApplication<SpringKotlinBlogApplication>(*args)
}
