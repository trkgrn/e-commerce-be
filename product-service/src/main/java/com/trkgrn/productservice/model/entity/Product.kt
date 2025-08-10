package com.trkgrn.productservice.model.entity

import com.trkgrn.productservice.model.entity.abstracts.AbstractProduct
import com.trkgrn.productservice.model.entity.listener.ProductEntityListener
import jakarta.persistence.*

@Entity
@Table(schema = "public", name = "products")
@EntityListeners(ProductEntityListener::class)
class Product @JvmOverloads constructor(
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "product", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "product", strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @OneToMany(mappedBy = "baseProduct", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var variants: Set<VariantProduct>? = null
) : AbstractProduct()