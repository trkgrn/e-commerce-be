package com.trkgrn.common.dto.authservice.response

import java.io.Serializable

data class UserDto @JvmOverloads constructor(
    var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var profilePicture: String? = null,
    var role: UserRoleDto? = null
) : Serializable
