package com.dynaHowl.myridan.model

enum class ElectricalUnit(val id: Int, val symbol: String, val description: String) {
    // Voltage
    VOLT(0, "V", "Voltage"),

    // Current
    AMPERE(1, "A", "Electric Current"),

    // Resistance
    OHM(2, "Ω", "Electrical Resistance"),

    // Conductance
    SIEMENS(3, "S", "Electrical Conductance"),

    // Capacitance
    FARAD(4, "F", "Capacitance"),

    // Inductance
    HENRY(5, "H", "Inductance"),

    // Power
    WATT(6, "W", "Power"),

    // Energy
    JOULE(7, "J", "Energy"),

    // Electric Charge
    COULOMB(8, "C", "Electric Charge"),

    // Frequency
    HERTZ(9, "Hz", "Frequency");

    override fun toString(): String {
        return "$name ($symbol) - $description"
    }
}
