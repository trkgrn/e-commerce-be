package com.trkgrn.common.dto.productgalleryservice.request

import java.io.Serializable

data class CreateProductGalleryRequest @JvmOverloads constructor(
    var mediaContainerId: Long? = null,
    var productId: Long? = null,
    var slotNumber: Int? = null
) : Serializable