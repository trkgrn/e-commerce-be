package com.trkgrn.common.dto.userservice.response

import java.io.Serializable

data class UserRoleDto @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null
) : Serializable