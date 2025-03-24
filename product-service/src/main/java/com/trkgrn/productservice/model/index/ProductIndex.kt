package com.trkgrn.productservice.model.index

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import java.io.Serializable

@Document(indexName = "products")
class ProductIndex : Serializable {
    @Id
    var id: Long? = null

    @MultiField(
        mainField = Field(type = FieldType.Text, name = NAME),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword), InnerField(
            suffix = "autocomplete",
            type = FieldType.Search_As_You_Type
        )]
    )
    var name: String? = null

    @MultiField(
        mainField = Field(type = FieldType.Text, name = DESCRIPTION),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword), InnerField(
            suffix = "autocomplete",
            type = FieldType.Search_As_You_Type
        )]
    )
    var description: String? = null

    @Field(type = FieldType.Keyword, name = SKU)
    var sku: String? = null

    @Field(type = FieldType.Keyword, name = APPROVAL_STATUS)
    var approvalStatus: String? = null

    @MultiField(
        mainField = Field(type = FieldType.Text, name = COLOR),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword), InnerField(
            suffix = "autocomplete",
            type = FieldType.Search_As_You_Type
        )]
    )
    var color: String? = null

    @MultiField(
        mainField = Field(type = FieldType.Text, name = SIZE),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword), InnerField(
            suffix = "autocomplete",
            type = FieldType.Search_As_You_Type
        )]
    )
    var size: String? = null

    @MultiField(
        mainField = Field(type = FieldType.Text, name = MATERIAL),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword), InnerField(
            suffix = "autocomplete",
            type = FieldType.Search_As_You_Type
        )]
    )
    var material: String? = null

    @Field(type = FieldType.Nested, name = BASE_PRODUCT)
    var baseProduct: BaseProductIndex? = null

    @Field(type = FieldType.Nested, name = GALLERIES)
    var galleries: List<VariantProductGalleryIndex>? = null

    companion object {
        const val NAME: String = "name"
        const val DESCRIPTION: String = "description"
        const val SKU: String = "sku"
        const val APPROVAL_STATUS: String = "approvalStatus"
        const val COLOR: String = "color"
        const val SIZE: String = "size"
        const val MATERIAL: String = "material"
        const val BASE_PRODUCT: String = "baseProduct"
        const val GALLERIES: String = "galleries"
    }
}