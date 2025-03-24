package com.trkgrn.common.dto.authservice.response

import java.io.Serializable

data class TokenDto @JvmOverloads constructor(
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var accessTokenExpiry: Long? = null,
    var refreshTokenExpiry: Long? = null
) : Serializable
