package com.trkgrn.common.dto.mediaservice.response

import java.io.Serializable

data class MediaFormatDto @JvmOverloads constructor(
    var id: Long? = null,
    var code: String? = null,
    var name: String? = null
) : Serializable