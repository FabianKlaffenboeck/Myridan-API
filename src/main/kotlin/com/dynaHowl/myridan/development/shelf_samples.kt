package com.dynaHowl.myridan.development

import com.dynaHowl.myridan.model.Shelf

val sampleShelfs = listOf(
    Shelf(
        id = 1, name = "Main Storage", trays = listOf(sampleTrays[0], sampleTrays[1], sampleTrays[2])
    ), Shelf(
        id = 2, name = "Main Storage", trays = listOf(sampleTrays[3], sampleTrays[4], sampleTrays[5])
    )
)