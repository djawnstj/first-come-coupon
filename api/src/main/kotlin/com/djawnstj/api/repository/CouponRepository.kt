package com.djawnstj.api.repository

import com.djawnstj.api.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository: JpaRepository<Coupon, Long> {
}