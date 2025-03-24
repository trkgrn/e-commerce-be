package com.trkgrn.fileservice.constants

import com.google.cloud.storage.Storage

enum class FileType(
    val path: String,
    val acl: Storage.PredefinedAcl,
    val mimeTypes: List<String>
) {
    USER_PROFILE_IMAGE(
        "public/users/profile/images",
        Storage.PredefinedAcl.PUBLIC_READ,
        listOf("jpg", "png", "jpeg", "webp")
    ),

    PRODUCT_IMAGE(
        "public/products/images",
        Storage.PredefinedAcl.PUBLIC_READ,
        listOf("jpg", "png", "jpeg", "webp")
    ),

    PRODUCT_VIDEO(
        "public/products/videos",
        Storage.PredefinedAcl.PUBLIC_READ,
        listOf("mp4")
    )

}
