package com.trkgrn.productservice.model.index

import java.io.Serializable

data class ProductGalleryIndex @JvmOverloads constructor(
    var id: Long? = null,
    var mediaContainer: MediaContainerIndex? = null,
    var slotNumber: Int? = null
) : Serializable