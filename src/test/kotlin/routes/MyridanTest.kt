package routes

import com.dynaHowl.myridan.plugins.JwtConfig
import com.dynaHowl.myridan.plugins.configureDatabases
import com.dynaHowl.myridan.plugins.configureHTTP
import com.dynaHowl.myridan.plugins.configureSecurity
import com.dynaHowl.myridan.plugins.configureSerialization
import com.dynaHowl.myridan.plugins.configureRouting
import io.ktor.server.testing.*

const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJqd3QtYXVkaWVuY2UiLCJpc3MiOiJodHRwczovL215cmlkYW4uZXdvbHZlbGFicy5jb20vIiwidXNlcm5hbWUiOiJ0ZXN0VXNlcmV4YW1wbGUuY29tIn0.CHzgnmEfy4mqlH64KPajA7c5leU34ZniBlzJT3s8wVA"

fun ApplicationTestBuilder.setupTestApp() {
    application {
        configureDatabases(
            "jdbc:sqlite:TestDB", dbUser = "root", dbPW = "", updateSchema = false, initDB = true, populateDB = true
        )

        val jwtConfig = JwtConfig(
            domain = "https://myridan.ewolvelabs.com/",
            audience = "jwt-audience",
            realm = "ktor sample app",
            secret = "super-secret-key"
        )

        configureHTTP()
        configureSerialization()
        configureSecurity(jwtConfig)

        configureRouting(jwtConfig)
    }
}
