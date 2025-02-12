package com.ohayo.moyamoya.infra.token

import com.ohayo.moyamoya.core.user.UserEntity
import com.ohayo.moyamoya.global.CustomException
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtClient(
    private val jwtProperties: JwtProperties,
) {
    fun generate(user: UserEntity) = Token(
        accessToken = createToken(
            user = user,
            tokenExpired = jwtProperties.expired.access,
        ),
        refreshToken = createToken(
            user = user,
            tokenExpired = jwtProperties.expired.refresh,
        )
    )

    fun payload(key: JwtPayloadKey, token: String): String =
        payload(key, parseToken(token))

    fun parseToken(token: String): Jws<Claims> =
        try {
            Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            throw CustomException(HttpStatus.FORBIDDEN, "expired jwt")
        } catch (e: SignatureException) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "invalid signature")
        } catch (e: MalformedJwtException) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "malformed jwt")
        } catch (e: UnsupportedJwtException) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "unsupported jwt")
        } catch (e: IllegalArgumentException) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "illegal argument error (jwt)")
        } catch (e: Exception) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "invalid token")
        }
    
    private fun payload(key: JwtPayloadKey, claims: Jws<Claims>) =
        claims.payload.get(key.key, String::class.java)
            ?: throw CustomException(HttpStatus.UNAUTHORIZED, "invalid token")

    private fun createToken(user: UserEntity, tokenExpired: Long) =
        Jwts.builder()
            .claim(JwtPayloadKey.ID.key, user.id)
            .claim(JwtPayloadKey.PHONE.key, user.phone)
            .claim(JwtPayloadKey.ROLE.key, user.userRole)
            .issuedAt(Date())
            .expiration(Date(Date().time + tokenExpired))
            .signWith(secretKey())
            .compact()

    private fun secretKey() = SecretKeySpec(
        jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )
}