package com.dateforyou.best.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: Int,
    val name: String,
    val image: String
): Parcelable