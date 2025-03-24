package com.trkgrn.common.dto.authservice.request

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class AuthRequest @JvmOverloads constructor(
    @field:NotBlank(message = "{NotBlank.username}")
    var username: String,

    @field:NotBlank(message = "{NotBlank.password}")
    var password: String
) : Serializable