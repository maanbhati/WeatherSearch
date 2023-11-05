package com.weather.search.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.weather.network.api.Api
import com.weather.search.R
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.search.utils.getDate

@Preview
@Composable
fun SuccessScreenPreview() {
    val successContent = WeatherDomainViewModel(
        123, 849242L, "test", 324.00, 34.00, 34.00, "raining", "", listOf()
    )
    SuccessScreen(navigateToDetailScreen = {}, successContent)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SuccessScreen(
    navigateToDetailScreen: (weatherDetail: String) -> Unit,
    successContentState: WeatherDomainViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = successContentState.name,
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.spacing_x_small)),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = stringResource(
                R.string.day_temperature, successContentState.temp.toString()
            ), modifier = Modifier.wrapContentHeight(), style = MaterialTheme.typography.body2
        )
        GlideImage(
            model = Api.FORECAST_ICON.plus(successContentState.icon),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.icon_size))
                .width(dimensionResource(id = R.dimen.icon_size)),
            contentScale = ContentScale.Fit,
            contentDescription = ""
        )
        Text(
            text = successContentState.description,
            modifier = Modifier.wrapContentHeight(),
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = getDate(successContentState.dt),
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.spacing_x_small)),
            style = MaterialTheme.typography.body1
        )
        LazyColumn(
            modifier = Modifier.wrapContentSize(),
            contentPadding = PaddingValues(
                dimensionResource(id = R.dimen.spacing_medium)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))
        ) {
            itemsIndexed(items = successContentState.daily) { index, daily ->
                WeatherListItem(dailyDomain = daily) {
                    /* Details Screen is under development
                    navigateToDetailScreen(successContentState.description)
                     */
                }
            }
        }
    }
}