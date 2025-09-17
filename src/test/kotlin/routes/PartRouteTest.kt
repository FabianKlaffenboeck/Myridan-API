package routes

import com.dynaHowl.myridan.development.sampleParts
import com.dynaHowl.myridan.model.Part
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PartRouteTest {

    @Test
    fun testGetParts() = testApplication {
        setupTestApp()
        client.get("api/parts") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Part> = Json.decodeFromString(body())
            assertEquals(sampleParts.size, receivedData.size)
        }
    }

    @Test
    fun testPostParts() = testApplication {
        setupTestApp()
        client.post("api/parts") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleParts[0].copy(id = null)))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/parts") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Part> = Json.decodeFromString(body())
            assertEquals(sampleParts.size + 1, receivedData.size)
        }
    }

    @Test
    fun testDeletePartsId() = testApplication {
        setupTestApp()
        client.delete("api/parts/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/parts") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Part> = Json.decodeFromString(body())
            assertEquals(sampleParts.size - 1, receivedData.size)
        }
    }

    @Test
    fun testGetPartsId() = testApplication {
        setupTestApp()
        client.get("api/parts/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: Part = Json.decodeFromString(body())
            assertEquals(sampleParts[0], receivedData)
        }
    }
}