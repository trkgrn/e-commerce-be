package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

data class ProductGalleryDto @JvmOverloads constructor(
    var id: Long? = null,
    var mediaContainer: MediaContainerDto? = null,
    var slotNumber: Int? = null
) : Serializable