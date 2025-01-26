package com.ohayo.moyamoya.core.play

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayEventReviewRepository : JpaRepository<PlayEventReviewEntity, Int>