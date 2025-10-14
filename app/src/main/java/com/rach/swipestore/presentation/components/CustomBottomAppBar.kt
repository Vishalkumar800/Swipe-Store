package com.rach.swipestore.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rach.swipestore.R
import com.rach.swipestore.presentation.navigation.Screens
import com.rach.swipestore.presentation.theme.redColor
import com.rach.swipestore.presentation.theme.unselectedColor

@Composable
fun AdvanceBottomBar(
    modifier: Modifier =  Modifier,
    route: String?,
    items: List<BottomAppBarDataClass>,
    fabSize: Dp = 64.dp,
    barHeight: Dp = 70.dp,
    navController: NavController
) {


    Box(
        modifier = modifier.fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .align(Alignment.TopCenter),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, item ->
                    if (index == 2) Spacer(modifier = Modifier.size(fabSize))
                    SingleBottomAppBarItem(
                        bottomBarItem = item,
                        selected = route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }

        LargeFloatingActionButton(
            onClick = {
                navController.navigate(Screens.AddProductScreen.route)
            },
            modifier = Modifier
                .size(fabSize)
                .padding(bottom = 20.dp)
                .border(
                    width = 2.dp,
                    shape = CircleShape,
                    color = Color.White
                )
                .align(Alignment.Center),
            containerColor = Color(0xFFFF2020),
            shape = CircleShape,
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add Floating Action Button",
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }

    }

}


@Composable
private fun SingleBottomAppBarItem(
    bottomBarItem: BottomAppBarDataClass,
    onClick: () -> Unit,
    selected: Boolean = false
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f
    )

    Column(
        modifier = Modifier
            .graphicsLayer(
                scaleX = scale, scaleY = scale
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = bottomBarItem.image,
            contentDescription = bottomBarItem.label,
            modifier = Modifier.size(24.dp),
            tint = if (selected) redColor else unselectedColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = bottomBarItem.label,
            fontSize = 12.sp,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) redColor else unselectedColor
        )
    }

}


data class BottomAppBarDataClass(
    val image: Painter,
    val label: String,
    val route: String
)