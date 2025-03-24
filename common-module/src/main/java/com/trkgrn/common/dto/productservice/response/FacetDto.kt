package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

class FacetDto private constructor(
    var name: String?,
    var values: MutableList<FacetValueDto>?
) : Serializable {
    class Builder {
        private var name: String? = null
        private var values: MutableList<FacetValueDto>? = null

        fun name(name: String) = apply { this.name = name }
        fun values(values: MutableList<FacetValueDto>) = apply { this.values = values }

        fun build() = FacetDto(name, values)
    }
}