package com.trkgrn.userservice.model

import com.trkgrn.userservice.model.abstracts.Base
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "user")
class User @JvmOverloads constructor(
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "username", unique = true)
    var username: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "email", unique = true)
    var email: String? = null,

    @Column(name = "phone", unique = true, length = 10)
    var phone: String? = null,

    @Column(name = "profile_picture")
    var profilePicture: String? = null,

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    var role: UserRole? = null
) : Base()