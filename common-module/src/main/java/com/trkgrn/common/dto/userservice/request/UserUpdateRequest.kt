package com.trkgrn.common.dto.userservice.request

import java.io.Serializable

data class UserUpdateRequest @JvmOverloads constructor(
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var profilePicture: String? = null
) : Serializable