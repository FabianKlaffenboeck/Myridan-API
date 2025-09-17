package routes

import com.dynaHowl.myridan.development.sampleManufacturers
import com.dynaHowl.myridan.model.Manufacturer
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ManufacturerRouteTest {

    @Test
    fun testGetManufacturers() = testApplication {
        setupTestApp()
        client.get("api/manufacturers") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Manufacturer> = Json.decodeFromString(body())
            assertEquals(sampleManufacturers.size, receivedData.size)
        }
    }

    @Test
    fun testPostManufacturers() = testApplication {
        setupTestApp()
        client.post("api/manufacturers") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleManufacturers[0].copy(id = null)))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/manufacturers") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Manufacturer> = Json.decodeFromString(body())
            assertEquals(sampleManufacturers.size + 1, receivedData.size)
        }
    }

    @Test
    fun testDeleteManufacturersId() = testApplication {
        setupTestApp()
        client.delete("api/manufacturers/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/manufacturers") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Manufacturer> = Json.decodeFromString(body())
            assertEquals(sampleManufacturers.size - 1, receivedData.size)
        }
    }

    @Test
    fun testGetManufacturersId() = testApplication {
        setupTestApp()
        client.get("api/manufacturers/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: Manufacturer = Json.decodeFromString(body())
            assertEquals(sampleManufacturers[0], receivedData)
        }
    }
}