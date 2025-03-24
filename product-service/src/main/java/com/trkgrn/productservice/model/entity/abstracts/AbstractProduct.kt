package com.trkgrn.productservice.model.entity.abstracts

import com.trkgrn.common.model.enums.ApprovalStatus
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractProduct : Base() {
    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "description", length = 1000)
    var description: String? = null

    @Column(name = "sku", unique = true, nullable = false)
    var sku: String? = null

    @Enumerated(EnumType.STRING)
    var approvalStatus: ApprovalStatus? = null
}