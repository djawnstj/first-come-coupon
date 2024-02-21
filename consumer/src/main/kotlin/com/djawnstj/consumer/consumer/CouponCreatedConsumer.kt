package com.djawnstj.consumer.consumer

import com.djawnstj.consumer.domain.Coupon
import com.djawnstj.consumer.repository.CouponRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CouponCreatedConsumer(
    private val couponRepository: CouponRepository
) {

    @KafkaListener(topics = ["coupon_create"], groupId = "group_1")
    fun listener(received: String) {
        val userId: Long = (received.map { it.code }.filterNot { it == 0 }.firstOrNull() ?: 0).toLong()
        println("CouponCreatedConsumer: coupon_create topic listen -> $userId")
        couponRepository.save(Coupon(userId))
    }
}