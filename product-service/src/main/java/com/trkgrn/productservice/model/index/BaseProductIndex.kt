package com.trkgrn.productservice.model.index

import java.io.Serializable

data class BaseProductIndex @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var sku: String? = null,
    var approvalStatus: String? = null,
    var galleries: List<ProductGalleryIndex>? = null
) : Serializable