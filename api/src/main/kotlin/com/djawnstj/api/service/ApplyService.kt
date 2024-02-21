package com.djawnstj.api.service

import com.djawnstj.api.producer.CouponCreateProducer
import com.djawnstj.api.repository.AppliedUserRepository
import com.djawnstj.api.repository.CouponCountRepository
import com.djawnstj.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository,
    private val couponCreateProducer: CouponCreateProducer,
    private val appliedUserRepository: AppliedUserRepository,
) {

    fun apply(userId: Long) {
        println("ApplyService::apply called -> $userId")
        val apply = appliedUserRepository.add(userId)

        if (apply != 1L) {
            return
        }
        println("ApplyService::apply $userId is applied")

        val count = couponCountRepository.increment() ?: throw RuntimeException("coupon count repository increment result is null")

        if (count > 100) {
            return
        }
        println("ApplyService::apply $userId's coupon is active")

        couponCreateProducer.create(userId)
    }

}