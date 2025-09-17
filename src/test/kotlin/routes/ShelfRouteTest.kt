package routes

import com.dynaHowl.myridan.development.sampleShelfs
import com.dynaHowl.myridan.model.Shelf
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ShelfRouteTest {

    @Test
    fun testGetShelfs() = testApplication {
        setupTestApp()
        client.get("api/shelfs") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Shelf> = Json.decodeFromString(body())
            assertEquals(sampleShelfs.size, receivedData.size)
        }
    }

    @Test
    fun testPostShelfs() = testApplication {
        setupTestApp()
        client.post("api/shelfs") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleShelfs[0].copy(id = null)))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/shelfs") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Shelf> = Json.decodeFromString(body())
            assertEquals(sampleShelfs.size + 1, receivedData.size)
        }
    }

    @Test
    fun testDeleteShelfsId() = testApplication {
        setupTestApp()
        client.delete("api/shelfs/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/shelfs") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<Shelf> = Json.decodeFromString(body())
            assertEquals(sampleShelfs.size - 1, receivedData.size)
        }
    }

    @Test
    fun testGetShelfsId() = testApplication {
        setupTestApp()
        client.get("api/shelfs/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: Shelf = Json.decodeFromString(body())
            assertEquals(sampleShelfs[0], receivedData)
        }
    }
}