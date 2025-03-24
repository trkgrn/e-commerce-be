package com.trkgrn.common.dto.authservice.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.io.Serializable

data class UserRegisterRequest @JvmOverloads constructor(
    @field:NotBlank(message = "{NotBlank.username}")
    var username: String,

    @field:NotBlank(message = "{NotBlank.password}")
    var password: String,

    @field:NotBlank(message = "{NotBlank.firstName}")
    var firstName: String,

    @field:NotBlank(message = "{NotBlank.lastName}")
    var lastName: String,

    @field:NotBlank(message = "{NotBlank.email}")
    @field:Email(message = "{Email.username}")
    var email: String,

    @field:NotBlank(message = "{NotBlank.phone}")
    @field:Size(min = 10, max = 10, message = "{Size.phone}")
    var phone: String,

    var profilePicture: String? = null
) : Serializable

