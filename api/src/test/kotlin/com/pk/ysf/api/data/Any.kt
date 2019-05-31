package com.pk.ysf.api.data

import org.mockito.Mockito

fun <T> any(type: Class<T>): T = Mockito.any(type)