package com.realifetech.sdk.sell.product.mocks

import com.apollographql.apollo.api.toInput
import com.realifetech.GetProductsQuery
import com.realifetech.fragment.FragmentProduct
import com.realifetech.fragment.ModifierItem
import com.realifetech.fragment.ProductVariant
import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.product.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.fragmentFulfilmentPoint
import com.realifetech.type.Language
import com.realifetech.type.ProductFilter
import com.realifetech.type.ProductModifierItemStatus

object ProductMocks {
    val productId = "id"
    val filters: ProductFilter =
        ProductFilter(listOf("1", "2").toInput(), listOf("2", "3").toInput())
    val params = listOf(FilterParamWrapper("key", "value"))
    val PAGE_SIZE = 10
    val PAGE = 1
    val NEXT_PAGE = 2

    val edge = generateProductEdge("1")
    val fragmentProduct = edge.fragments.fragmentProduct
    val product = fragmentProduct.asModel
    val edges = listOf(edge)
    val productsResult = edges.map { it.fragments.fragmentProduct.asModel }
    val products: GetProductsQuery.GetProducts? =
        GetProductsQuery.GetProducts("", edges, NEXT_PAGE)
    val paginatedObject: PaginatedObject<Product?>? =
        PaginatedObject(productsResult, NEXT_PAGE)

    private fun generateProductEdge(id: String) =
        GetProductsQuery.Edge(
            "",
            GetProductsQuery.Edge.Fragments(
                FragmentProduct(
                    "",
                    id,
                    "success",
                    "test",
                    "test",
                    "",
                    "",
                    listOf(FragmentProduct.Image("", "", "url", 1)),
                    listOf(
                        FragmentProduct.ModifierList(
                            "", id,
                            "test",
                            "test",
                            "test",
                            true,
                            mandatorySelect = true,
                            3,
                            listOf(
                                FragmentProduct.Item(
                                    "",
                                    FragmentProduct.Item.Fragments(
                                        ModifierItem(
                                            "", id,
                                            ProductModifierItemStatus.ACTIVE,
                                            "test",
                                            12,
                                            1,
                                            listOf(
                                                ModifierItem.Translation(
                                                    "",
                                                    Language.EN,
                                                    "test"
                                                )
                                            )
                                        )
                                    )
                                )
                            ),
                            listOf(FragmentProduct.Translation("", Language.EN, "title"))
                        )
                    ),
                    listOf(
                        FragmentProduct.Variant(
                            "", FragmentProduct.Variant.Fragments(
                                ProductVariant(
                                    "",
                                    id,
                                    "test",
                                    123,
                                    "12",
                                    "14",
                                    listOf(
                                        ProductVariant.Translation(
                                            "",
                                            Language.EN,
                                            "varient title"
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    listOf(
                        FragmentProduct.Category(
                            "",
                            id,
                            "dsa",
                            "sada",
                            "fdas",
                            1,
                            "",
                            "",
                            listOf(FragmentProduct.Translation1("", Language.EN, "category"))
                        )
                    ),
                    listOf(
                        FragmentProduct.FulfilmentPoint(
                            "",
                            FragmentProduct.FulfilmentPoint.Fragments(fragmentFulfilmentPoint)
                        )
                    ),
                    listOf(FragmentProduct.Translation2("", "", "en", "", ""))
                )
            )
        )
}