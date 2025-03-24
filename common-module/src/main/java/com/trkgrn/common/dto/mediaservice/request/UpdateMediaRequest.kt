package com.trkgrn.common.dto.mediaservice.request

import java.io.Serializable

data class UpdateMediaRequest @JvmOverloads constructor(
    var code: String? = null,
    var name: String? = null,
    var description: String? = null,
    var altText: String? = null
) : Serializable