package com.trkgrn.common.dto.productservice.response

import java.io.Serializable

data class MediaDto (
    var url: String? = null,
    var altText: String? = null,
    var mimeType: String? = null,
    var format: String? = null
) : Serializable