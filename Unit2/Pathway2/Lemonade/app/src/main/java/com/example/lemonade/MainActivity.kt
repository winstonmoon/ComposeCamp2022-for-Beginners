package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp() {

    // Current step the app is displaying (remember allows the state to be retained
    // across recompositions).
    var currentStep by remember { mutableStateOf(1) }

    // Number of times the lemon needs to be squeezed to turn into a glass of lemonade
    var squeezeCount by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (currentStep) {
            1 -> {
                // Display lemon tree image and ask user to pick a lemon from the tree
                LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_select,
                    drawableResourceId = R.drawable.lemon_tree,
                    contentDescriptionResourceId = R.string.lemon_tree_content_description,
                    onImageClick = {
                        // Update to next step
                        currentStep = 2
                        // Each time a lemon is picked from the tree, get a new random number
                        // between 2 and 4 (inclusive) for the number of times the lemon needs
                        // to be squeezed to turn into lemonade
                        squeezeCount = (2..4).random()
                    }
                )
            }
            2 -> {
                // Display lemon image and ask user to squeeze the lemon
                LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_squeeze,
                    drawableResourceId = R.drawable.lemon_squeeze,
                    contentDescriptionResourceId = R.string.lemon_content_description,
                    onImageClick = {
                        // Decrease the squeeze count by 1 for each click the user performs
                        squeezeCount--
                        // When we're done squeezing the lemon, move to the next step
                        if (squeezeCount == 0) {
                            currentStep = 3
                        }
                    }
                )
            }
            3 -> {
                // Display glass of lemonade image and ask user to drink the lemonade
                LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_drink,
                    drawableResourceId = R.drawable.lemon_drink,
                    contentDescriptionResourceId = R.string.glass_of_lemonade_content_description,
                    onImageClick = {
                        // Update to next step
                        currentStep = 4
                    }
                )
            }
            4 -> {
                // Display empty glass image and ask user to start again
                LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_restart,
                    drawableResourceId = R.drawable.lemon_restart,
                    contentDescriptionResourceId = R.string.empty_glass_content_description,
                    onImageClick = {
                        // Back to starting step
                        currentStep = 1
                    }
                )
            }
        }
    }
}

/**
 * Composable that displays a text label above an image that is clickable.
 *
 * @param textLabelResourceId is the resource ID for the text string to display
 * @param drawableResourceId is the resource ID for the image drawable to display below the text
 * @param contentDescriptionResourceId is the resource ID for the string to use as the content
 * description for the image
 * @param onImageClick will be called when the user clicks the image
 * @param modifier modifiers to set to this composable
 */
@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(drawableResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier
                .wrapContentSize()
                .clickable(
                    onClick = onImageClick
                )
                .border(
                    BorderStroke(2.dp, Color(105, 205, 216)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun LemonPreview() {
    LemonadeTheme() {
        LemonApp()
    }
}