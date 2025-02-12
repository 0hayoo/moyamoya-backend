package com.ohayo.moyamoya.core.user

import com.ohayo.moyamoya.core.BaseEntity
import com.ohayo.moyamoya.core.school.SchoolEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user")
class UserEntity(
    @Column(unique = true, nullable = false)
    val phone: String,

    @JoinColumn(name = "school_id", nullable = false)
    @ManyToOne
    val school: SchoolEntity,

    @Column(nullable = false)
    val schoolGrade: Int,

    @Column(nullable = false)
    val schoolClass: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false)
    val profileImageUrl: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userRole: UserRole = UserRole.NORMAL
): BaseEntity()