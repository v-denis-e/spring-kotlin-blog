package kz.vdenise.springkotlinblog.config

import kz.vdenise.springkotlinblog.core.gateway.TopicGateway
import kz.vdenise.springkotlinblog.core.usecase.Topics
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfig {

    @Bean
    fun topics(gateway: TopicGateway): Topics = Topics(gateway)

}