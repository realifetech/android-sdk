package com.realifetech.sdk.sell.fulfilmentpoint.mocks

import com.apollographql.apollo.api.toInput
import com.realifetech.GetFulfilmentPointCategoriesQuery
import com.realifetech.GetFulfilmentPointsQuery
import com.realifetech.fragment.FragmentCollectionNotes
import com.realifetech.fragment.FragmentForm
import com.realifetech.fragment.FragmentFulfilmentPoint
import com.realifetech.fragment.FragmentFulfilmentPointCategory
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.fulfilmentPoint.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.FulfilmentPointFilter
import com.realifetech.type.Language

object FFPMocks {

    const val PAGE = 1
    const val NEXT_PAGE = 2
    const val PAGE_SIZE = 10
    const val fulfilmentPointID = "1"


    val fieldForm = FragmentForm.Field(
        "",
        "",
        "",
        "",
        true,
        1,
        FragmentForm.AutoFill("", "", ""),
        listOf(
            FragmentForm.Translation1("", "", "", "", "", "", "", "", ""),
            null
        ),
        listOf(FragmentForm.SelectOption("", "", "", ""))
    )

    val seatForm= FragmentFulfilmentPoint.SeatForm(
        "",
        FragmentFulfilmentPoint.SeatForm.Fragments(
            FragmentForm(
                "",
                "2",
                "1",
                "",
                "",
                "",
                true,
                true,
                true,
                "",
                213,
                listOf(
                    FragmentForm.Translation(
                        "", "en", "form title",
                        "form description", "submit",
                        "skip", "complete",
                        "complete title", "complete description"
                    )
                ), listOf(fieldForm, null)
            )
        )
    )

    val form = seatForm.fragments.fragmentForm.asModel
    val ffpCategoryEdge = generateFFPCategoryEdge("1")
    val ffpCategoryEdges = listOf(ffpCategoryEdge, generateFFPCategoryEdge("2"))
    val nullableFFPCategoryEdges = listOf(ffpCategoryEdge, null)
    val nullableFFPCategories =
        nullableFFPCategoryEdges.map { it?.fragments?.fragmentFulfilmentPointCategory?.asModel }
    val fragmentFulfilmentPointCategory = ffpCategoryEdge.fragments.fragmentFulfilmentPointCategory
    val fulfilmentPointCategory = fragmentFulfilmentPointCategory.asModel

    val ffpCategories = listOf(
        fulfilmentPointCategory,
        generateFFPCategoryEdge("2").fragments.fragmentFulfilmentPointCategory.asModel
    )
    val fragmentFulfilmentPoint = generateFFP("1")
    val fulfilmentPoint: FulfilmentPoint? = fragmentFulfilmentPoint.asModel
    val ffpEdge = generateFFPEdge("1")
    val ffpEdge2 = generateFFPEdge("2")
    val ffpEdges = listOf(ffpEdge, ffpEdge2)
    val filters = FulfilmentPointFilter(listOf("testing").toInput()).toInput()
    val params = listOf(FilterParamWrapper("key", "value"))
    val nullableFFPEdges = listOf(ffpEdge, null)
    val fulfilmentPoints = ffpEdges.map { it.fragments.fragmentFulfilmentPoint.asModel }
    val nullableFFPoints = nullableFFPEdges.map { it?.fragments?.fragmentFulfilmentPoint?.asModel }
    private fun generateFFPEdge(id: String) = GetFulfilmentPointsQuery.Edge(
        "",
        GetFulfilmentPointsQuery.Edge.Fragments(
            FragmentFulfilmentPoint(
                "",
                id,
                "",
                "",
                null,
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                id.toInt(),
                "",
                "",
                null,
                null,
                null,
                null,
                null
            )
        )
    )

    val paginatedFFPCategories: PaginatedObject<FulfilmentPointCategory?>? =
        PaginatedObject(ffpCategories, PAGE + 1)
    val paginatedFFP: PaginatedObject<FulfilmentPoint?>? =
        PaginatedObject(fulfilmentPoints, NEXT_PAGE)
    val fulfilmentPointWithNullArguments = FragmentFulfilmentPoint(
        "",
        "3",
        "123",
        "ref",
        null,
        "",
        "",
        "",
        null,
        null,
        null,
        null,
        4,
        "",
        "",
        null,
        seatForm,
        null,
        null,
        null
    ).asModel
    val fulfilmentPointCategoryWithEmptyArguments = FragmentFulfilmentPointCategory(
        "", "1",
        null,
        null,
        null,
        5,
        null,
        null,
        null
    ).asModel

    private fun generateFFP(id: String) = FragmentFulfilmentPoint(
        "",
        id,
        "123",
        "ref",
        null,
        "",
        "",
        "",
        null,
        null,
        null,
        null,
        id.toInt(),
        "",
        "",
        listOf(
            FragmentFulfilmentPoint.Translation(
                "", Language.EN, "Meal Deal", "Batata and Chicken", "add chilli sauce",
                FragmentFulfilmentPoint.CollectionNotes(
                    "", FragmentFulfilmentPoint.CollectionNotes.Fragments(
                        FragmentCollectionNotes("", "1", "1")
                    )
                )
            )
        ),
        seatForm,
        listOf(
            FragmentFulfilmentPoint.Category(
                "", FragmentFulfilmentPoint.Category.Fragments(fragmentFulfilmentPointCategory)
            )
        ),
        null,
        null
    )


    private fun generateFFPCategoryEdge(id: String) =
        GetFulfilmentPointCategoriesQuery.Edge(
            "",
            GetFulfilmentPointCategoriesQuery.Edge.Fragments(
                FragmentFulfilmentPointCategory(
                    "", id,
                    "ref",
                    "stat",
                    "https://www.google.com",
                    id.toInt(),
                    "",
                    "",
                    listOf(
                        FragmentFulfilmentPointCategory.Translation(
                            "",
                            Language.EN,
                            "Batman vs Superman"
                        )
                    )
                )
            )
        )
}