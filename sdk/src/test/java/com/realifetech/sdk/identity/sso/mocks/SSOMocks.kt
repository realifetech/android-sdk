package com.realifetech.sdk.identity.sso.mocks

import com.realifetech.GetUserAliasesQuery
import com.realifetech.fragment.FragmentUserAlias

object SSOMocks {
    val expectedUserAlias =
        FragmentUserAlias("", FragmentUserAlias.UserAliasType("", "AXS"), "2345")

    val result = GetUserAliasesQuery.Data(
        GetUserAliasesQuery.Me(
            "", GetUserAliasesQuery.User(
                "", listOf(
                    GetUserAliasesQuery.UserAlias("",
                        GetUserAliasesQuery.UserAlias.Fragments(expectedUserAlias))
                )
            )
        )
    )
}