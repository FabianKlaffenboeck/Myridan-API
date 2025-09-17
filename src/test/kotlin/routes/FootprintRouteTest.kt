package routes

import com.dynaHowl.myridan.development.sampleFootprints
import com.dynaHowl.myridan.model.Footprint
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class FootprintRouteTest {

    @Test
    fun testGetFootprints() = testApplication {
        setupTestApp()
        client.get("api/footprints") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Footprint> = Json.decodeFromString(body())
            assertEquals(sampleFootprints.size, receivedData.size)
        }
    }

    @Test
    fun testPostFootprints() = testApplication {
        setupTestApp()
        client.post("api/footprints") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleFootprints[0].copy(id = null)))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("api/footprints") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Footprint> = Json.decodeFromString(body())
            assertEquals(sampleFootprints.size + 1, receivedData.size)
        }
    }

    @Test
    fun testDeleteFootprintsId() = testApplication {
        setupTestApp()
        client.delete("api/footprints/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/footprints") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Footprint> = Json.decodeFromString(body())
            assertEquals(sampleFootprints.size - 1, receivedData.size)
        }
    }

    @Test
    fun testGetFootprintsId() = testApplication {
        setupTestApp()
        client.get("api/footprints/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: Footprint = Json.decodeFromString(body())
            assertEquals(sampleFootprints[0], receivedData)
        }
    }
}