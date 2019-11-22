package com.ToxicBakery.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.unaryPlus
import androidx.ui.core.Clip
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(ScreenModel())
        }
    }
}

@Preview
@Composable
fun LightThemeNewsStory() {
    Screen()
}

@Preview
@Composable
fun DarkThemeNewsStory() {
    Screen()
}

@Model
class ScreenModel {

    private var themeIndex = 0

    val theme: MaterialColors
        get() = themeColors[themeIndex % themeColors.size]

    fun nextTheme() {
        themeIndex++
        if (themeIndex == themeColors.size) themeIndex = 0
    }

    companion object {
        private val themeColors = listOf(
            lightThemeColors,
            darkThemeColors
        )
    }
}

@Composable
fun Screen(screenModel: ScreenModel = ScreenModel()) {
    MaterialTheme(
        colors = screenModel.theme,
        typography = themeTypography
    ) {
        Surface(color = +themeColor { background }) {
            FlexColumn {
                inflexible {
                    TopAppBar(
                        title = { Text(text = "Hello Compose") }
                    )
                }
                flexible(flex = 1f) {
                    VerticalScroller {
                        NewsStory(screenModel)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsStory(
    screenModel: ScreenModel
) {
    val image = +imageResource(R.drawable.header)
    Column(
        crossAxisSize = LayoutSize.Expand,
        modifier = Spacing(16.dp)
    ) {
        Container(expanded = true, height = 180.dp) {
            Clip(shape = RoundedCornerShape(8.dp)) {
                DrawImage(image)
            }
        }

        HeightSpacer(16.dp)

        Text("Header",
            style = +themeTextStyle { h6 })
        Text("Davenport, California",
            style = +themeTextStyle { body2 })
        Text("November 2019",
            style = +themeTextStyle { body2 })

        HeightSpacer(16.dp)

        Button("Change Theme",
            onClick = { screenModel.nextTheme() })
    }
}
