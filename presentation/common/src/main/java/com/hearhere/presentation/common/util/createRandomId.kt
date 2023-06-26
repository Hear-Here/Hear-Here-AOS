package com.hearhere.presentation.common.util

import java.util.*

fun createRandomId(): Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
