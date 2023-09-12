package com.weather.search.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.weather.search.R

@Composable
fun DetailScreen(item: Int) {
    Row(
        modifier = Modifier
            .background(colorResource(id = R.color.color_light_gray))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detail Screen of item $item",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize(),
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    DetailScreen(1)
}
