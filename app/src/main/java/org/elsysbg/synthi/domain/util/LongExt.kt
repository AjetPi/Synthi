package org.elsysbg.synthi.domain.util

inline val Long.formatted
    get() = String.format("%02d:%02d", this / 1000 / 60, this / 1000 % 60)