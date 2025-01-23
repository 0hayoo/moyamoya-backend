package com.ohayo.moyamoya.api.user.profile.core

import com.ohayo.moyamoya.core.user.Gender
import com.ohayo.moyamoya.core.user.profile.*
import com.ohayo.moyamoya.global.CustomException
import org.springframework.http.HttpStatus

object MatchingHelper {
    fun matchUsers(users: List<UserProfileEntity>): List<MatchingResult> {
        val graph = arrayListOf<MatchingGraphEdge>()
        val males = users.filter { it.user.gender == Gender.MALE }
        val females = users.filter { it.user.gender == Gender.FEMALE }
        val heights = users.map { it.myType.height }

        males.forEach { male ->
            females.forEach { female ->
                val maleToFemaleScore = getScore(from = male, to = female, heights = heights)
                val femaleToMaleScore = getScore(from = female, to = female, heights = heights)

                graph.add(
                    MatchingGraphEdge(
                        score = maleToFemaleScore + femaleToMaleScore,
                        male = male,
                        female = female
                    )
                )
            }
        }

        val sortedGraph = graph.sortedByDescending { it.score }.toMutableList()
        val matchingResults = arrayListOf<MatchingResult>()

        sortedGraph.forEach { edge ->
            if (edge.visited) return@forEach

            matchingResults.add(
                MatchingResult(
                    male = edge.male,
                    female = edge.female,
                )
            )

            // 방문 처리
            sortedGraph
                .filter { it.male == edge.male || it.female == edge.female }
                .forEach {
                    it.visited = true
                }
        }

        return matchingResults
    }

    private fun getScore(from: UserProfileEntity, to: UserProfileEntity, heights: List<Int>): Int {
        val idealTypeHeightLevels = getHeightLevels(height = to.myType.height, heights = heights)
        val score = (if (from.idealType.messageInterval == to.myType.messageInterval) 10 else -10) +  // 연락 텀
                (FashionStyle.listOf(from.idealType.fashionStyle)
                    .intersect(FashionStyle.listOf(to.myType.fashionStyle).toSet()).size * 5) + // 패션 스타일
                (if (from.idealType.hasGlasses == to.myType.hasGlasses) 5 else 0) + // 안경
                (if (from.idealType.faceType == to.myType.faceType) 10 else -10) + // 얼굴상
                (if (from.idealType.bodyType == to.myType.bodyType) 10 else -5) + // 체형
                (if (from.idealType.hairStyle.length == to.myType.hairStyle.length) 5 else -5) +
                (if (from.idealType.hairStyle.isCurly == to.myType.hairStyle.isCurly) 5 else -5) +
                (if (from.idealType.hairStyle.hasPerm == to.myType.hairStyle.hasPerm) 5 else -5) +
                (if (from.idealType.hairStyle.hasBang == to.myType.hairStyle.hasBang) 5 else -5) +
                (if (from.idealType.skinColor == to.myType.skinColor) 10 else -10) + // 피부색
                (if (
                    from.idealType.heightLevel != HeightLevel.ANY &&
                    idealTypeHeightLevels.contains(from.idealType.heightLevel)
                ) 10 else 0) + // 키
                (if (from.idealType.ageType.isMatched(from.user.schoolGrade, to.user.schoolGrade)) 10 else -10) + // 나이
                from.myType.mbti.score(to.myType.mbti) // 성격 (mbti)
        return score
    }

    private fun getHeightLevels(height: Int, heights: List<Int>): List<HeightLevel> {
        val sortedHeights = heights.sorted()

        // 키가 몇 %인지 계산
        val rank = sortedHeights.indexOfFirst { it >= height } + 1
        val percentile = (rank.toDouble() / sortedHeights.size) * 100

        return when {
            percentile <= 10 -> listOf(HeightLevel.LARGE)
            percentile in 11.0..30.0 -> listOf(HeightLevel.LARGE, HeightLevel.MEDIUM)
            percentile in 31.0..69.0 -> listOf(HeightLevel.MEDIUM)
            percentile in 70.0..100.0 -> listOf(HeightLevel.MEDIUM, HeightLevel.SMALL)
            else -> throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}