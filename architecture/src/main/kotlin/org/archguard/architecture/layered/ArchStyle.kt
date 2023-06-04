package org.archguard.architecture.layered

enum class ArchStyle(val value: String) {
    MVC("mvc"),
    MVP("mvp"),
    MVVM("mvvm"),
    VIPER("viper"),
    CLEAN("clean"),
    DDD("ddd"),
    ONION("onion"),
    HEXAGONAL("hexagonal"),
    MONOLITH("monolith")
    ;

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return values().any { it.value == lowercase }
        }

        fun valuesString(): String {
            return values().joinToString(", ") { it.value }
        }
    }
}
