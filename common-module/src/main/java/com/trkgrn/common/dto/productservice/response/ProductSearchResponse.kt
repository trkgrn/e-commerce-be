package com.trkgrn.common.dto.productservice.response

import com.trkgrn.common.dto.PaginationDto
import com.trkgrn.common.dto.SortDto
import java.io.Serializable

data class ProductSearchResponse @JvmOverloads constructor(
    var products: MutableList<ProductIndexDto>? = null,
    var facets: MutableList<FacetDto>? = null,
    var pagination: PaginationDto? = null,
    var sorts: MutableList<SortDto>? = null,
    var suggestions: MutableList<String>? = null
) : Serializable