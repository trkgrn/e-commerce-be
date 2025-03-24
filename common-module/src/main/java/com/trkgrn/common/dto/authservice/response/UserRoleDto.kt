package com.trkgrn.common.dto.authservice.response

import java.io.Serializable

data class UserRoleDto @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null
) : Serializable

