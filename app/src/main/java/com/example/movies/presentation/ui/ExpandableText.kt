package com.example.movies.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.movies.presentation.ui.theme.MoviesTheme

const val COLLAPSED_MAX_LINE = 3

@Composable
fun ExpandableText(
    text: String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    showMoreText: String,
    showLessText: String,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    collapsedMaxLines: Int = COLLAPSED_MAX_LINE,
    expandedMaxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {
//    var isExpanded by remember { mutableStateOf(false) }
    var isOverflow by rememberSaveable { mutableStateOf(true) }
//    var lastCharIndex by remember { mutableIntStateOf(text.length - 1) }

    Column(
        modifier = modifier
            .animateContentSize(),
    ) {
        Text(
            text = text,
            maxLines = if (isExpanded) expandedMaxLines else collapsedMaxLines,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded) {
//                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLines - 1)
                    isOverflow = textLayoutResult.hasVisualOverflow
                }
            },
            overflow = overflow,
            lineHeight = lineHeight,
            color = color,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            softWrap = softWrap,
            style = style
        )
        if (isOverflow) {
            Text(
                modifier = Modifier
                    .clickable {
                        onExpandedChange(!isExpanded)
                    },
                text = if (isExpanded) {
                    showLessText
                } else {
                    showMoreText
                },
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showSystemUi = false)
@Composable
private fun ExpandableTextPreview() {
    MoviesTheme {
        ExpandableText(
            text = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
                    "привнести в размеренную жизнь аристократа дух приключений.",
            showMoreText = "Показать еще",
            showLessText = "Скрыть",
            onExpandedChange = {},
            isExpanded = false
        )
    }
}

@Preview(showSystemUi = false)
@Composable
private fun ExpandableTextNoOverflowPreview() {
    MoviesTheme {
        ExpandableText(
            text = "Пострадав в результате несчастного случая.",
            showMoreText = "Показать еще",
            showLessText = "Скрыть",
            onExpandedChange = {},
            isExpanded = false
        )
    }
}

@Composable
fun ExpandableTextNoButton(
    text: String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    showMoreText: String,
    showLessText: String,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    collapsedMaxLines: Int = COLLAPSED_MAX_LINE,
    expandedMaxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {
    var isOverflow by rememberSaveable { mutableStateOf(true) }
    var lastCharIndex by rememberSaveable { mutableIntStateOf(0) }
    Text(
        modifier = Modifier
            .clickable {
                onExpandedChange(!isExpanded)
            }
            .then(modifier),
        text = buildAnnotatedString {
            if (isExpanded) {
                append(text)
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(showLessText)
                }
            } else {
                val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreText.length)
                    .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                append(adjustText)
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append(" $showMoreText") }
            }
        },
        maxLines = if (isExpanded) expandedMaxLines else collapsedMaxLines,
        onTextLayout = { textLayoutResult ->
            if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLines - 1)
            }
        }
    )
}

@Preview
@Composable
private fun ExpandedTextNoButtonPreview() {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    MoviesTheme {
        ExpandableTextNoButton(
            text = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
                    "привнести в размеренную жизнь аристократа дух приключений.",
            showMoreText = "Показать еще",
            showLessText = "Скрыть",
            onExpandedChange = { isExpanded = it},
            isExpanded = isExpanded
        )
    }
}