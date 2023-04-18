package com.esmt.projet.victodo.feature_list.presentation.util

import androidx.compose.ui.graphics.Color

sealed class CircleColors(val color: Color) {
    object firstColor : CircleColors(Color(0xFFCB2F2F))
    object secondColor : CircleColors(Color(0xFF006EE9))
    object thirdColor : CircleColors(Color(0xFF35AB4F))
    object fourthColor : CircleColors(Color(0xFF7B61FF))
    object fifthColor : CircleColors(Color(0xFFF24E1E))
    object sixthColor : CircleColors(Color(0xFFFF7262))
    object seventhColor : CircleColors(Color(0xFF362075))
    object eighthColor : CircleColors(Color(0xFF434343))
    object ninthColor : CircleColors(Color(0xFF97C8FF))
    object tenthColor : CircleColors(Color(0xFFEBDBCF))
    object eleventhColor : CircleColors(Color(0xFFFFE7AA))
    object twelfthColor : CircleColors(Color(0xFFB66200))

    companion object {
        fun getArrayOfColors(): Array<CircleColors> {
            return arrayOf(
                firstColor,
                secondColor,
                thirdColor,
                fourthColor,
                fifthColor,
                sixthColor,
                seventhColor,
                eighthColor,
                ninthColor,
                tenthColor,
                eleventhColor,
                twelfthColor,
            )
        }

        fun size(): Int {
            return getArrayOfColors().size
        }
    }
}
