package capps.foodapp.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import capps.foodapp.presentation.ui.theme.Neutral300

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    tint: Color = Color.Unspecified,
    size: Dp = 20.dp,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .size(40.dp)
            .clickable { onClick() }
            .border(1.dp, color = Neutral300, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(id = resId),
            tint = tint,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(size)
        )
    }
}