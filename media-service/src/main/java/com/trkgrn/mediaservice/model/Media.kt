package com.trkgrn.mediaservice.model

import com.trkgrn.mediaservice.model.abstracts.Base
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "medias")
class Media @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media")
    @SequenceGenerator(name = "media", sequenceName = "media_id_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "code", unique = true)
    var code: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "alttext")
    var altText: String? = null,

    @Column(name = "file_id")
    var fileId: String,

    @ManyToOne
    @JoinColumn(name = "media_format_id", referencedColumnName = "id")
    var format: MediaFormat
) : Base()
