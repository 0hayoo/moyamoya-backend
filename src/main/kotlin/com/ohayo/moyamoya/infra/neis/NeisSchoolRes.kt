package com.ohayo.moyamoya.infra.neis

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class NeisSchoolRes(
    val schoolInfo: List<SchoolInfo>?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class SchoolInfo(
        val row: List<Row>?
    ) {
        data class Row(
            /** 시도교육청코드 */
            @JsonProperty("ATPT_OFCDC_SC_CODE") val atptOfcdcScCode: String,

            /** 시도교육청명 */
            @JsonProperty("ATPT_OFCDC_SC_NM") val atptOfcdcScNm: String,

            /** 행정표준코드 */
            @JsonProperty("SD_SCHUL_CODE") val sdSchulCode: String,

            /** 학교명 */
            @JsonProperty("SCHUL_NM") val schulNm: String,

            /** 영문학교명 */
            @JsonProperty("ENG_SCHUL_NM") val engSchulNm: String?,

            /** 학교종류명 */
            @JsonProperty("SCHUL_KND_SC_NM") val schulKndScNm: String?,

            /** 시도명 */
            @JsonProperty("LCTN_SC_NM") val lctnScNm: String,

            /** 관할조직명 */
            @JsonProperty("JU_ORG_NM") val juOrgNm: String,

            /** 설립명 */
            @JsonProperty("FOND_SC_NM") val fondScNm: String?,

            /** 도로명우편번호 */
            @JsonProperty("ORG_RDNZC") val orgRdnzc: String?,

            /** 도로명주소 */
            @JsonProperty("ORG_RDNMA") val orgRdnma: String?,

            /** 도로명상세주소 */
            @JsonProperty("ORG_RDNDA") val orgRdnda: String?,

            /** 전화번호 */
            @JsonProperty("ORG_TELNO") val orgTelno: String,

            /** 홈페이지주소 */
            @JsonProperty("HMPG_ADRES") val hmpgAdres: String?,

            /** 남녀공학구분명 */
            @JsonProperty("COEDU_SC_NM") val coeduScNm: String,

            /** 팩스번호 */
            @JsonProperty("ORG_FAXNO") val orgFaxno: String?,

            /** 고등학교구분명 */
            @JsonProperty("HS_SC_NM") val hsScNm: String?,

            /** 산업체특별학급존재여부 */
            @JsonProperty("INDST_SPECL_CCCCL_EXST_YN") val indstSpeclCcclExstYn: String,

            /** 고등학교일반전문구분명 */
            @JsonProperty("HS_GNRL_BUSNS_SC_NM") val hsGnrlBusnsScNm: String?,

            /** 특수목적고등학교계열명 */
            @JsonProperty("SPCLY_PURPS_HS_ORD_NM") val spclyPurpsHsOrdNm: String?,

            /** 입시전후기구분명 */
            @JsonProperty("ENE_BFE_SEHF_SC_NM") val eneBfeSehfScNm: String,

            /** 주야구분명 */
            @JsonProperty("DGHT_SC_NM") val dghtScNm: String,

            /** 설립일자 */
            @JsonProperty("FOND_YMD") val fondYmd: String,

            /** 개교기념일 */
            @JsonProperty("FOAS_MEMRD") val foasMemrd: String,

            /** 수정일자 */
            @JsonProperty("LOAD_DTM") val loadDtm: String
        )
    }
}