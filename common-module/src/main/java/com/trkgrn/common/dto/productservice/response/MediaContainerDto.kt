package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

data class MediaContainerDto @JvmOverloads constructor(
    var medias: MutableList<MediaDto>? = null
) : Serializable