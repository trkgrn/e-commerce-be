package com.trkgrn.mediaservice.model

import com.trkgrn.mediaservice.model.abstracts.Base
import com.trkgrn.mediaservice.model.listener.MediaContainerEntityListener
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "media_containers")
@EntityListeners(MediaContainerEntityListener::class)
class MediaContainer @JvmOverloads constructor (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_container")
    @SequenceGenerator(name = "media_container", sequenceName = "media_container_id_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "code", unique = true)
    var code: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "media_container_to_media",
        joinColumns = [JoinColumn(name = "media_container_id")],
        inverseJoinColumns = [JoinColumn(name = "media_id")]
    )
    var medias: MutableList<Media> = mutableListOf()

) : Base()
