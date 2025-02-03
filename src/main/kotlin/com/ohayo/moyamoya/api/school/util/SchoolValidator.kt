package com.ohayo.moyamoya.api.school.util

import com.ohayo.moyamoya.global.CustomException
import org.springframework.http.HttpStatus

object SchoolValidator {
    fun validateSchoolWaitingCount(waitCount: Long) {
        if (5000 < waitCount) throw CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "적절하지 못한 값입니다.")
    }
}
