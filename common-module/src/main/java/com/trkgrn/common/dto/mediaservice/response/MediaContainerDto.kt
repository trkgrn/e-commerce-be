package com.trkgrn.common.dto.mediaservice.response

import java.io.Serializable

data class MediaContainerDto @JvmOverloads constructor(
    var id: Long? = null,
    var code: String? = null,
    var name: String? = null,
    var description: String? = null,
    var medias: MutableList<MediaDto>? = null
) : Serializable