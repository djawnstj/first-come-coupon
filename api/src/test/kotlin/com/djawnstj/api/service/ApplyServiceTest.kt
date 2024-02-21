package com.djawnstj.api.service

import com.djawnstj.api.repository.CouponRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.random.Random

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private lateinit var applyService: ApplyService

    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Test
    fun `한번만 응모`() {
        // given
        applyService.apply(1L)

        // when
        val count = couponRepository.count()

        // then
        assertThat(count).isEqualTo(1L)
    }

    @Test
    fun `여러명 응모`() {
        // given
        val threadCount = 1000
        val executorService = Executors.newFixedThreadPool(32)

        val latch = CountDownLatch(threadCount)

        // when
        for (i in 0 until threadCount) {
            val userId = i.toLong()
            executorService.submit {
                try {
                    applyService.apply(userId)
                } finally {
                    latch.countDown()
                }

            }
        }

        latch.await()

        Thread.sleep(10000)

        val count = couponRepository.count()

        // then
        assertThat(count).isEqualTo(100)
    }


    @Test
    fun `한명당 한번만 응모`() {
        // given
        val threadCount = 1000
        val executorService = Executors.newFixedThreadPool(32)

        val latch = CountDownLatch(threadCount)

        // when
        for (i in 0 until threadCount) {
            val userId = Random.nextLong(500)
            executorService.submit {
                try {
                    applyService.apply(userId)
                } finally {
                    latch.countDown()
                }

            }
        }

        latch.await()

        Thread.sleep(10000)

        val count = couponRepository.count()

        // then
        assertThat(count).isEqualTo(100)
    }

}