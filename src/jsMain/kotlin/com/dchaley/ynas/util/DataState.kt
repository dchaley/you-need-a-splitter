package com.dchaley.ynas.util

/**
 * Represents data of a certain type that may or may not be loaded.
 *
 * Data typically begins as unloaded, then becomes loading, and is finally marked loaded.
 *
 * Useful for building UI components that react to asynchronous data loading.
 */
sealed class DataState<out T> {
  data object Unloaded : DataState<Nothing>()
  data object Loading : DataState<Nothing>()
  data class Loaded<T>(var data: T) : DataState<T>()

  // Guess we need to handle errors some day…
  // data class Error(val exception: Throwable) : DataState<Nothing>()
}
