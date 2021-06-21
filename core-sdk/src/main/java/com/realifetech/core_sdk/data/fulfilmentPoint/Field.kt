package com.realifetech.core_sdk.data.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Field(
    val type: String?,
    val key: String?,
    val validationRegex: String?,
    val required: Boolean?,
    val sortId: Int?,
    val autoFill: AutoFill?,
    val translations: List<FieldTranslation?>?,
    val selectOptions: List<SelectOption?>?,
) : Parcelable
