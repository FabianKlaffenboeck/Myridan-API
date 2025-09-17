package routes

import com.dynaHowl.myridan.development.samplePartTypes
import com.dynaHowl.myridan.model.PartType
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PartTypeRouteTest {

    @Test
    fun testGetParttypes() = testApplication {
        setupTestApp()
        client.get("api/partTypes") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<PartType> = Json.decodeFromString(body())
            assertEquals(samplePartTypes.size, receivedData.size)
        }
    }

    @Test
    fun testPostParttypes() = testApplication {
        setupTestApp()
        client.post("api/partTypes") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(samplePartTypes[0].copy(id = null)))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/partTypes") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<PartType> = Json.decodeFromString(body())
            assertEquals(samplePartTypes.size + 1, receivedData.size)
        }
    }

    @Test
    fun testDeleteParttypesId() = testApplication {
        setupTestApp()
        client.delete("api/partTypes/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }

        client.get("api/partTypes") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: List<PartType> = Json.decodeFromString(body())
            assertEquals(samplePartTypes.size - 1, receivedData.size)
        }
    }

    @Test
    fun testGetParttypesId() = testApplication {
        setupTestApp()
        client.get("api/partTypes/1") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val receivedData: PartType = Json.decodeFromString(body())
            assertEquals(samplePartTypes[0], receivedData)
        }
    }
}