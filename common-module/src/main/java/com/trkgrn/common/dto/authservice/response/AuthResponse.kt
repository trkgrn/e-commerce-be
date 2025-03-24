package com.trkgrn.common.dto.authservice.response

import java.io.Serializable

data class AuthResponse @JvmOverloads constructor(
    var token: TokenDto? = null,
    var role: String? = null
) : Serializable

