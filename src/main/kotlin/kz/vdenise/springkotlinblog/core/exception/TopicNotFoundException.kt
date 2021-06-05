package kz.vdenise.springkotlinblog.core.exception

class TopicNotFoundException(
    id: Int
): RuntimeException("Topic with $id not found!")
