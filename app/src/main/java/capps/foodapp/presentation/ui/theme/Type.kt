package capps.foodapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography.bodyMediumMedium: TextStyle
    get() = bodyMedium.copy(fontWeight = FontWeight.W500)

val Typography.bodyMediumBold: TextStyle
    get() = bodyMedium.copy(fontWeight = FontWeight.W700)

val Typography.bodySmallMedium: TextStyle
    get() = bodySmall.copy(fontWeight = FontWeight.W500)

val Typography.bodySmallSemiBold: TextStyle
    get() = bodySmall.copy(fontWeight = FontWeight.W700) //Figma used W700, but called it SemiBold


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Satoshi,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.5).sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Satoshi,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.5).sp
    ),

    bodySmall = TextStyle(
        fontFamily = Satoshi,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.5).sp
    ),

    titleMedium = TextStyle(
        fontFamily = Satoshi,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 21.6.sp,
        letterSpacing = (-0.48).sp
    ),
)