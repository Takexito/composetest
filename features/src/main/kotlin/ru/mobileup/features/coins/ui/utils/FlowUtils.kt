package ru.mobileup.features.coins.ui.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.aartikov.replica.common.CombinedLoadingError
import me.aartikov.replica.single.Loadable
import ru.mobileup.core.error_handling.ErrorHandler
import timber.log.Timber

internal fun <T> StateFlow<T>.toComposeState(coroutineScope: CoroutineScope): State<T> {
    val state: MutableState<T> = mutableStateOf(this.value)
    coroutineScope.launch {
        this@toComposeState.collect {
            state.value = it
        }
    }
    return state
}

internal fun <T : Any> Flow<T>.toComposeState(
    coroutineScope: CoroutineScope,
    errorHandler: ErrorHandler? = null
): State<Loadable<T>> {
    val state: MutableState<Loadable<T>> = mutableStateOf(Loadable(true))
    coroutineScope.launch {
        this@toComposeState.collect {
            try {
                this@toComposeState.collect {
                    state.value = Loadable(false, it)
                }
            } catch (e: Exception) {
                state.value = Loadable(false, error = CombinedLoadingError(listOf(e)))
                if (errorHandler == null) Timber.e(e)
                errorHandler?.handleError(e)
            }
        }
    }
    return state
}