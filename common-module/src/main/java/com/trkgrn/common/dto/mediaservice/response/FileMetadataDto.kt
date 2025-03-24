package com.trkgrn.common.dto.mediaservice.response

import java.io.Serializable
import java.time.LocalDateTime

data class FileMetadataDto @JvmOverloads constructor(
    var id: String? = null,
    var name: String? = null,
    var mimeType: String? = null,
    var cloudPath: String? = null,
    var fileSize: Long? = null,
    var url: String? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
) : Serializable