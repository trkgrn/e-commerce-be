package com.trkgrn.fileservice.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "files")
data class FileMetadata @JvmOverloads constructor (
    @Id
    var id: String? = null,
    var name: String? = null,
    var mimeType: String? = null,
    var cloudPath: String? = null,
    var fileSize: Long? = null,
    var url: String? = null,
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
    @Version
    var version: Long? = null
)