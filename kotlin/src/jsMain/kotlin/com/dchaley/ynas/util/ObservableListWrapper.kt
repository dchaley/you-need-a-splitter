package com.dchaley.ynas.util

import io.kvision.state.ObservableListWrapper

// define an extension function on ObservableListWrapper that clears and adds all atomically
fun <T> ObservableListWrapper<T>.replaceAll(newItems: List<T>) : Boolean {
  if (this.isEmpty() && newItems.isEmpty()) {
    return true
  }
  this.mutableList.clear()
  return this.addAll(newItems)
}
