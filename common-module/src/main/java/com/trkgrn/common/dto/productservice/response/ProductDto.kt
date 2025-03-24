package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

data class ProductDto @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var sku: String? = null,
    var variants: MutableList<VariantProductDto>? = null,
    var approvalStatus: String? = null
) : Serializable