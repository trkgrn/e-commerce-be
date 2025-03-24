package com.trkgrn.productservice.model.index

import java.io.Serializable

data class MediaContainerIndex @JvmOverloads constructor(
    var medias: List<MediaIndex>? = null
) :Serializable