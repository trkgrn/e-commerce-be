package com.trkgrn.common.dto.mediaservice.request

import java.io.Serializable

data class CreateMediaRequest @JvmOverloads constructor(
    var code: String? = null,
    var name: String? = null,
    var description: String? = null,
    var altText: String? = null,
    var mediaFormatId: Long? = null
) : Serializable