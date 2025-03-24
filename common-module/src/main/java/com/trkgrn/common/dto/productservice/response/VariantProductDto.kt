package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

data class VariantProductDto @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var sku: String? = null,
    var color: String? = null,
    var size: String? = null,
    var material: String? = null,
    var baseProductId: Long? = null,
    var approvalStatus: String? = null
) : Serializable