package com.trkgrn.common.dto.mediaservice.response

import java.io.Serializable

data class MediaDto @JvmOverloads constructor(
    var id: Long? = null,
    var code: String? = null,
    var name: String? = null,
    var description: String? = null,
    var altText: String? = null,
    var format: MediaFormatDto? = null,
    var file: FileMetadataDto? = null
) : Serializable