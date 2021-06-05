FROM openjdk:11

ENV OKTA_OAUTH2_ISSUER="http://example.com" \
    OKTA_OAUTH2_CLIENT-ID="test" \
    OKTA_OAUTH2_CLIENT-SECRET="test"

RUN useradd -ms /bin/bash blog

USER blog
WORKDIR /home/blog

COPY ./target/spring-kotlin-blog.jar ./app/spring-kotlin-blog.jar

ENTRYPOINT ["java", "-jar", "/home/blog/app/spring-kotlin-blog.jar"]
