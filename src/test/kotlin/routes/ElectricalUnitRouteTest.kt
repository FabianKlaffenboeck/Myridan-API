package routes

import com.dynaHowl.myridan.model.ElectricalUnit
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ElectricalUnitRouteTest {

    @Test
    fun testGetElectricalunits() = testApplication {
        setupTestApp()
        client.get("api/electricalUnits") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val electricalUnits: List<ElectricalUnit> = Json.decodeFromString(body())
            assertEquals(ElectricalUnit.entries.toList(), electricalUnits)
        }
    }
}