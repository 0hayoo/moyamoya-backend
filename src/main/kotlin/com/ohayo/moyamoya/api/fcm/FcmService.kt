package com.ohayo.moyamoya.api.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.ohayo.moyamoya.core.FcmTokenEntity
import com.ohayo.moyamoya.core.FcmTokenRepository
import com.ohayo.moyamoya.global.UserSessionHolder
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmTokenRepository: FcmTokenRepository,
    private val fcmClient: FirebaseMessaging,
    private val sessionHolder: UserSessionHolder
) {
    fun register(req: RegisterFcmTokenReq) {
        fcmTokenRepository.deleteByFcmToken(req.fcmToken)
        fcmTokenRepository.save(
            FcmTokenEntity(
                fcmToken = req.fcmToken,
                user = sessionHolder.current()
            )
        )
        fcmClient.subscribeToTopic(listOf(req.fcmToken), "/topics/all")
    }
    
    fun sendMessageToMe(title: String, body: String) {
        this.sendMessage(title, body, sessionHolder.current().id)
    }
    
    fun sendMessage(title: String, body: String, userId: Int) {
        val tokens = fcmTokenRepository.findByUserId(userId)
        
        tokens.forEach {
            fcmClient.send(
                Message.builder()
                    .setToken(it.fcmToken)
                    .setNotification(
                        Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build()
                    )
                    .build()
            )
        }
    }
}