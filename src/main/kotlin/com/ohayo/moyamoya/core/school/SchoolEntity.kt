package com.ohayo.moyamoya.core.school

import com.ohayo.moyamoya.core.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tbl_school")
class SchoolEntity(
    @Column(nullable = false)
    val name: String,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val type: SchoolType,

    @Column(nullable = false)
    val cityName: String,

    val postalCode: String?,
    val address: String?,
    val addressDetail: String?,

    @Column(nullable = false)
    val phone: String,

    val website: String?,

    @Column(nullable = false)
    val foundedAt: LocalDate,

    @Column(nullable = false)
    val anniversary: LocalDate,

    @Column(nullable = false)
    val schoolCode: String,

    @Column(nullable = false)
    val officeCode: String,

    @Column(nullable = false)
    var waitingCount: Long
): BaseEntity() {
    fun modifyWaitingCount(waitingCount: Long) {
        this.waitingCount += waitingCount
    }
}