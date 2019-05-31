package com.pk.ysf.api.data

import com.pk.ysf.api.model.entity.Surface

fun surfaceMock(): Surface =
        Surface.build {
            id = SURFACE_ID
            name = SURFACE_NAME
        }