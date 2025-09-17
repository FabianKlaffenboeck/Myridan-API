package routes

import com.dynaHowl.myridan.development.sampleTrays
import com.dynaHowl.myridan.model.Tray
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TrayRouteTest {

    @Test
    fun testGetTrays() = testApplication {
        setupTestApp()
        client.get("api/trays") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Tray> = Json.decodeFromString(body())
            assertEquals(sampleTrays.size, receivedData.size)
        }
    }

    @Test
    fun testPostTrays() = testApplication {
        setupTestApp()
        client.post("api/trays") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleTrays[0].copy(id = null)))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/trays") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Tray> = Json.decodeFromString(body())
            assertEquals(sampleTrays.size + 1, receivedData.size)
        }
    }

    @Test
    fun testDeleteTraysId() = testApplication {
        setupTestApp()
        client.delete("api/trays/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/trays") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Tray> = Json.decodeFromString(body())
            assertEquals(sampleTrays.size - 1, receivedData.size)
        }
    }

    @Test
    fun testGetTraysId() = testApplication {
        setupTestApp()
        client.get("api/trays/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: Tray = Json.decodeFromString(body())
            assertEquals(sampleTrays[0], receivedData)
        }
    }
}