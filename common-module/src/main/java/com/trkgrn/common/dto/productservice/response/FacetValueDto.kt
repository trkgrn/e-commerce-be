package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

class FacetValueDto private constructor(
    val value: String?,
    val count: Long?,
    val query: String?,
    val selected: Boolean?
) : Serializable {

    class Builder {
        private var value: String? = null
        private var count: Long? = null
        private var query: String? = null
        private var selected: Boolean? = null

        fun value(value: String) = apply { this.value = value }
        fun count(count: Long) = apply { this.count = count }
        fun query(query: String) = apply { this.query = query }
        fun selected(selected: Boolean) = apply { this.selected = selected }

        fun build() = FacetValueDto(value, count, query, selected)
    }

}