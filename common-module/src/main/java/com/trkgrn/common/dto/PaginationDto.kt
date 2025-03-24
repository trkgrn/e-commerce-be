package com.trkgrn.common.dto

import java.io.Serializable

class PaginationDto private constructor(
    val currentPage: Int?,
    val pageSize: Int?,
    val totalPages: Int?,
    val totalResults: Long?
) : Serializable {

    class Builder {
        private var currentPage: Int? = null
        private var pageSize: Int? = null
        private var totalPages: Int? = null
        private var totalResults: Long? = null

        fun currentPage(currentPage: Int?) = apply { this.currentPage = currentPage }
        fun pageSize(pageSize: Int?) = apply { this.pageSize = pageSize }
        fun totalPages(totalPages: Int?) = apply { this.totalPages = totalPages }
        fun totalResults(totalResults: Long?) = apply { this.totalResults = totalResults }

        fun build() = PaginationDto(currentPage, pageSize, totalPages, totalResults)
    }
}