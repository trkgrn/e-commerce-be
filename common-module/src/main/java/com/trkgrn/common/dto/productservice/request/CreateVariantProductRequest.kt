package com.trkgrn.common.dto.productservice.request

import com.trkgrn.common.model.enums.ApprovalStatus
import java.io.Serializable

data class CreateVariantProductRequest @JvmOverloads constructor(
    var name: String? = null,
    var description: String? = null,
    var sku: String? = null,
    var approvalStatus: ApprovalStatus? = null,
    var color: String? = null,
    var size: String? = null,
    var material: String? = null,
    var baseProductId: Long? = null
) : Serializable