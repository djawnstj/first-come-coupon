package com.djawnstj.consumer.repository

import com.djawnstj.consumer.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository: JpaRepository<Coupon, Long> {
}