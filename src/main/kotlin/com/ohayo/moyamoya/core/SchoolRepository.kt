package com.ohayo.moyamoya.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolRepository: JpaRepository<SchoolEntity, Int>