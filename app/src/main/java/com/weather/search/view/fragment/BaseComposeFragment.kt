package com.weather.search.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

abstract class BaseComposeFragment : Fragment() {
    @Composable
    abstract fun ComposeContent()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {
        // Dispose of the Composition when the view's LifecycleOwner is destroyed
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent { ComposeContent() }
    }
}