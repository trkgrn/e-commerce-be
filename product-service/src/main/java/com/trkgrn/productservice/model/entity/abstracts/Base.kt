package com.trkgrn.productservice.model.entity.abstracts

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class Base {

    @Column(name = "create_time", nullable = false, updatable = false)
    var createTime: LocalDateTime? = null
        private set

    @Column(name = "update_time")
    var updateTime: LocalDateTime? = null
        private set

    @Column(name = "is_active")
    var isActive: Boolean? = null

    @PrePersist
    fun prePersist() {
        createTime = LocalDateTime.now()
        updateTime = LocalDateTime.now()
        isActive = true
    }

    @PreUpdate
    fun preUpdate() {
        updateTime = LocalDateTime.now()
    }

    override fun toString(): String {
        return "Base(createTime=$createTime, updateTime=$updateTime, isActive=$isActive)"
    }
}
