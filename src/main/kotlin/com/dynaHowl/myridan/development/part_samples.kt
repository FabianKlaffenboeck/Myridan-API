package com.dynaHowl.myridan.development

import com.dynaHowl.myridan.model.ElectricalUnit
import com.dynaHowl.myridan.model.Part

val sampleParts = listOf(
    Part(
        id = 1,
        name = "Mcp 2515",
        quantity = 3,
        electricalUnit = ElectricalUnit.VOLT,
        value = null,
        footprint = null,
        partType = samplePartTypes[0],
        manufacturer = sampleManufacturers[1],
        tray = sampleTrays[0],
    ), Part(
        id = 2,
        name = "Mcp 2552",
        quantity = 3,
        electricalUnit = ElectricalUnit.AMPERE,
        value = 564.00,
        footprint = sampleFootprints[0],
        partType = samplePartTypes[0],
        manufacturer = sampleManufacturers[1],
        tray = sampleTrays[1],
    ), Part(
        id = 3,
        name = "NCP1117",
        quantity = 3,
        electricalUnit = null,
        value = 100.00,
        footprint = sampleFootprints[0],
        partType = samplePartTypes[0],
        manufacturer = sampleManufacturers[2],
        tray = sampleTrays[2],
    )
)