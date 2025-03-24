package com.trkgrn.common.dto.productgalleryservice.request

import java.io.Serializable

data class CreateVariantProductGalleryRequest @JvmOverloads constructor(
    var mediaContainerId: Long? = null,
    var variantProductId: Long? = null,
    var slotNumber: Int? = null
) : Serializable