package com.trkgrn.common.dto.productservice.request

import com.trkgrn.common.model.enums.ApprovalStatus
import java.io.Serializable

data class CreateProductRequest @JvmOverloads constructor(
    var name: String? = null,
    var description: String? = null,
    var sku: String? = null,
    var approvalStatus: ApprovalStatus? = null
) : Serializable