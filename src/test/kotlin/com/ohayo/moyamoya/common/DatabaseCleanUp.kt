package com.ohayo.moyamoya.common

import jakarta.annotation.PostConstruct
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Table
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DatabaseCleanUp {
    @PersistenceContext
    private lateinit var entityManager: EntityManager
    private lateinit var tableNames: MutableList<String>

    @PostConstruct
    fun init() {
        tableNames = entityManager.metamodel.entities.stream()
            .filter { it.javaType.getAnnotation(Entity::class.java) != null }
            .filter { it.javaType.getAnnotation(Table::class.java) == null }
            .map { it.name.lowercase(Locale.getDefault()) }
            .toList().toMutableList()
        val tableNamesWithAnnotation = entityManager.metamodel.entities.stream()
            .filter { it.javaType.getAnnotation(Table::class.java) != null }
            .map {
                it.javaType.getAnnotation(Table::class.java)
                    .name
                    .lowercase(Locale.getDefault())
            }
            .toList()
        println(tableNames)
        println(tableNamesWithAnnotation)
        tableNames.addAll(tableNamesWithAnnotation)
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        // 제약 조건 무효화 - 데이터를 지우는데 외래키, 유일키 등의 제약조건에 영향을 받지 않기 위해
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()

        // 테이블을 돌면서 데이터 TRUNCATE, 컬럼 ID 시작 값을 1로 초기화
        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
        }

        // 무효화한 제약 조건 다시 되돌림
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}