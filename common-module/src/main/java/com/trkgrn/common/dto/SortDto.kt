package com.trkgrn.common.dto

import java.io.Serializable

data class SortDto @JvmOverloads constructor(
    var code: String? = null,
    var name: String? = null,
    var selected: Boolean? = null
) : Serializable