package com.trkgrn.productservice.model.index

import java.io.Serializable

data class MediaIndex @JvmOverloads constructor(
    var url: String? = null,
    var altText: String? = null,
    var mimeType: String? = null,
    var format: String? = null
) : Serializable