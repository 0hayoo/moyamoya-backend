package com.ohayo.moyamoya.api.fcm

import com.fasterxml.jackson.annotation.JsonCreator

data class RegisterFcmTokenReq @JsonCreator constructor(
    val fcmToken: String
)