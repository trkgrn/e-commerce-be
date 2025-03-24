package com.trkgrn.authservice.model

import java.io.Serializable

data class Token(
    var id: String? = null,
    var jwt: String? = null
) : Serializable