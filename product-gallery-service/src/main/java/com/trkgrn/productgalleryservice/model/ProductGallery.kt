package com.trkgrn.productgalleryservice.model

import com.trkgrn.productgalleryservice.model.abstracts.Base
import jakarta.persistence.*

@Entity
@Table(
    schema = "public",
    name = "product_gallery",
    uniqueConstraints = [UniqueConstraint(
        name = "unique_media_per_base_product",
        columnNames = ["media_container_id", "product_id"]
    ), UniqueConstraint(
        name = "unique_media_per_variant_product",
        columnNames = ["media_container_id", "variant_product_id"]
    )]
)
class ProductGallery @JvmOverloads constructor(
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "product_gallery", sequenceName = "product_gallery_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "product_gallery", strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "media_container_id", nullable = false)
    var mediaContainerId: Long? = null,

    @Column(name = "product_id")
    var productId: Long? = null,

    @Column(name = "variant_product_id")
    var variantProductId: Long? = null,

    @Column(name = "slot_number", nullable = false)
    var slotNumber: Int? = null
) : Base()