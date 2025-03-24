package com.trkgrn.userservice.model

import com.trkgrn.userservice.model.abstracts.Base
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "user_role")
class UserRole @JvmOverloads constructor(
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_role", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_role", sequenceName = "user_role_id_seq", allocationSize = 1)
    var id: Long? = null,

    @Column(name = "name", nullable = false, unique = true)
    var name: String? = null
) : Base()