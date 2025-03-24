package com.trkgrn.common.dto.productgalleryservice.response

import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto
import java.io.Serializable

data class VariantProductGalleryDto @JvmOverloads constructor(
    var id: Long? = null,
    var mediaContainer: MediaContainerDto? = null,
    var variantProductId: Long? = null,
    var slotNumber: Int? = null
) : Serializable