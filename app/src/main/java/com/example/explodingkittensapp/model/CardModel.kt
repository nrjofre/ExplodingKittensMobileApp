package com.example.explodingkittensapp.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
data class CardModel(
    var id: String,
    var type: String
): Parcelable {
    @IgnoredOnParcel
    var isClickable: Boolean = true
}