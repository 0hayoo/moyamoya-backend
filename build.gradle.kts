plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.ohayo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    
    api("com.google.firebase:firebase-admin:9.3.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20210629-1.32.1")
    implementation("com.google.api-client:google-api-client:1.34.1")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.16.0")
    
    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("net.nurigo:javaSDK:2.2")
    
    // JWT
    val jwtVersion = "0.12.5"
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
    
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.mockk:mockk:1.13.16")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.apply {
    bootJar {
        // 실행 가능한 JAR 생성 설정
        // XX.jar
        enabled = true
    }
    jar {
        // 일반 JAR 파일 생성 비활성화
        // XXplain.jar
        enabled = false
    }
}
