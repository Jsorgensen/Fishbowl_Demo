package com.example.fishbowl_demo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fishbowl_demo.R

val roboRegular = FontFamily(Font(R.font.roboto_regular))
val roboLight = FontFamily(Font(R.font.roboto_light))

val RoboRegular14 = Typography(
    bodyLarge = TextStyle(
        fontFamily = roboRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.25.sp
    ),
)

val RoboLight17 = Typography(
    bodyLarge = TextStyle(
        fontFamily = roboLight,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.25.sp
    ),
)

val RoboRegular30 = Typography(
    bodyLarge = TextStyle(
        fontFamily = roboRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 39.sp,
    ),
)