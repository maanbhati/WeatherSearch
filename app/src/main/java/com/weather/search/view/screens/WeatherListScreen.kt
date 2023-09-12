package com.weather.search.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.weather.network.api.Api
import com.weather.search.R
import com.weather.search.data.remote.DailyDomainViewModel
import com.weather.search.utils.getDate

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherListItem(dailyDomain: DailyDomainViewModel, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_small)),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .background(colorResource(id = R.color.color_light_gray))
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_small))
        ) {
            Text(
                text = getDate(dailyDomain.dt),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterStart),
                style = MaterialTheme.typography.body1
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_x_small))
            ) {
                GlideImage(
                    model = Api.FORECAST_ICON.plus(dailyDomain.weather.first().icon),
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.spacing_x_large))
                        .width(dimensionResource(id = R.dimen.spacing_x_large)),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                Text(
                    text = dailyDomain.weather.first().description,
                    modifier = Modifier.wrapContentHeight(),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = stringResource(
                        R.string.min_max_temperature,
                        dailyDomain.temp.max.toString(),
                        dailyDomain.temp.min.toString()
                    ),
                    modifier = Modifier.wrapContentHeight(),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}