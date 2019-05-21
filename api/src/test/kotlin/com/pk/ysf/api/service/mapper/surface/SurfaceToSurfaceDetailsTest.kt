package com.pk.ysf.api.service.mapper.surface

import com.pk.ysf.api.data.SURFACE_ID
import com.pk.ysf.api.data.SURFACE_NAME
import com.pk.ysf.api.data.surfaceMock
import com.pk.ysf.api.model.dto.SurfaceDetails
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
class SurfaceToSurfaceDetailsTest {

    @InjectMocks
    lateinit var surfaceToSurfaceDetails: SurfaceToSurfaceDetails

    @Test
    fun correctMapSurface() {
        val surfaceDetails: SurfaceDetails = surfaceToSurfaceDetails.map(surfaceMock())

        assertEquals(SURFACE_ID, surfaceDetails.id)
        assertEquals(SURFACE_NAME, surfaceDetails.name)
    }

    @Test
    fun correctMapAll() {
        val surfaceDetailsList: Collection<SurfaceDetails> =
                surfaceToSurfaceDetails.mapAll(listOf(
                        surfaceMock(), surfaceMock(), surfaceMock()
                ))

        surfaceDetailsList.forEach {
            assertEquals(SURFACE_ID, it.id)
            assertEquals(SURFACE_NAME, it.name)
        }
    }

}