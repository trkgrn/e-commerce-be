package com.trkgrn.productservice.model.entity

import com.trkgrn.productservice.model.entity.abstracts.AbstractProduct
import com.trkgrn.productservice.model.entity.listener.VariantProductEntityListener
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "variant_products")
@EntityListeners(VariantProductEntityListener::class)
class VariantProduct @JvmOverloads constructor(
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "variant_product", sequenceName = "variant_product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "variant_product", strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "color")
    var color: String? = null,

    @Column(name = "size")
    var size: String? = null,

    @Column(name = "material")
    var material: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Product::class)
    @JoinColumn(name = "base_product_id", referencedColumnName = "id")
    var baseProduct: Product? = null
) : AbstractProduct()