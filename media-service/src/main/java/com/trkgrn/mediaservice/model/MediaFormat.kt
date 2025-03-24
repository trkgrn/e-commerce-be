package com.trkgrn.mediaservice.model

import com.trkgrn.mediaservice.model.abstracts.Base
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "media_formats")
class MediaFormat @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_format")
    @SequenceGenerator(name = "media_format", sequenceName = "media_format_id_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "code", unique = true)
    var code: String,

    @Column(name = "name")
    var name: String
): Base()
