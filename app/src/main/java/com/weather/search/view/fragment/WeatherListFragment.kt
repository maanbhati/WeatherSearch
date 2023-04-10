package com.weather.search.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.weather.network.api.Api.Companion.FORECAST_ICON
import com.weather.search.R
import com.weather.search.data.remote.DailyDomainViewModel
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.search.ui.theme.WeatherSearchTheme
import com.weather.search.utils.getDate
import com.weather.search.view.screens.ErrorScreen
import com.weather.search.view.screens.LoadingScreen
import com.weather.search.viewmodel.WeatherListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    private lateinit var viewModelWeather: WeatherListViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WeatherSearchTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        viewModelWeather = viewModel()
                        Scaffold(
                            topBar = { SearchAppBar(viewModel = viewModelWeather) }
                        ) {
                            val isLoadingContentState by viewModelWeather.isLoadingContentState.collectAsStateWithLifecycle()
                            val isErrorContentState by viewModelWeather.isErrorContentState.collectAsStateWithLifecycle()
                            val successContentState by viewModelWeather.successContentState.collectAsStateWithLifecycle()

                            if (isLoadingContentState) {
                                LoadingScreen()
                            }
                            successContentState?.let { SuccessScreen(it) }
                            if (isErrorContentState) {
                                ErrorScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SearchAppBar(
        viewModel: WeatherListViewModel,
    ) {
        var query: String by rememberSaveable { mutableStateOf("") }
        var showClearIcon by rememberSaveable { mutableStateOf(false) }
        showClearIcon = query.isNotEmpty()

        TextField(
            value = query,
            onValueChange = { onQueryChanged ->
                query = onQueryChanged
                if (onQueryChanged.isNotEmpty()) {
                    viewModel.fetchWeatherData(onQueryChanged)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (showClearIcon) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = "Clear Icon"
                        )
                    }
                }
            },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            placeholder = { Text(text = stringResource(R.string.search_hint)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background, shape = RectangleShape)
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_small))
        )
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun SuccessScreen(successContentState: WeatherDomainViewModel) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = successContentState.name,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(dimensionResource(id = R.dimen.spacing_x_small)),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = requireContext().getString(
                    R.string.day_temperature,
                    successContentState.temp.toString()
                ),
                modifier = Modifier.wrapContentHeight(),
                style = MaterialTheme.typography.body2
            )
            GlideImage(
                model = FORECAST_ICON.plus(successContentState.icon),
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
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.spacing_medium),
                    vertical = dimensionResource(id = R.dimen.spacing_small)
                ),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))
            ) {
                items(items = successContentState.daily) { daily ->
                    WeatherListItem(dailyDomain = daily)
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun WeatherListItem(dailyDomain: DailyDomainViewModel) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_small)),
            modifier = Modifier.fillMaxWidth()
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
                        model = FORECAST_ICON.plus(dailyDomain.weather.first().icon),
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
                        text = requireContext().getString(
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
}