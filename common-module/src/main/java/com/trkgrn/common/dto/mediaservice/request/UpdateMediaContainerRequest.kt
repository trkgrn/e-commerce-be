package com.trkgrn.common.dto.mediaservice.request

import java.io.Serializable

data class UpdateMediaContainerRequest @JvmOverloads constructor(
    var code: String? = null,
    var name: String? = null,
    var description: String? = null
) : Serializable