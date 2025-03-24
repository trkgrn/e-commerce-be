package com.trkgrn.common.dto.productgalleryservice.response

import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto
import java.io.Serializable

data class ProductGalleryDto @JvmOverloads constructor(
    var id: Long? = null,
    var mediaContainer: MediaContainerDto? = null,
    var productId: Long? = null,
    var slotNumber: Int? = null
) : Serializable