package com.djawnstj.consumer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> = mapOf(
        (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"),
        (ConsumerConfig.GROUP_ID_CONFIG to "group_1"),
        (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java),
        (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java)
    ).let { DefaultKafkaConsumerFactory(it) }

    @Bean
    fun kafkaConsumerListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> =
        ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = consumerFactory()
        }

}