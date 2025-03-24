package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

data class BasicProductDto @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var sku: String? = null,
    var approvalStatus: String? = null,
    var galleries: List<ProductGalleryDto>? = null
) : Serializable