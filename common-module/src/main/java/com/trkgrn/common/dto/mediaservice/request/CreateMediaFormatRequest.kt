package com.trkgrn.common.dto.mediaservice.request

import java.io.Serializable

data class CreateMediaFormatRequest @JvmOverloads constructor(
    var code: String? = null,
    var name: String? = null
) : Serializable