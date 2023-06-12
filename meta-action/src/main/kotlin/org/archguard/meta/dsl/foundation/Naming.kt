package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction

class Naming : AtomicAction<String> {
    val filename: String = ""

    fun endWiths(vararg suffixes: String) : Boolean {
        return suffixes.any { filename.endsWith(it) }
    }

    fun startsWith(vararg symbols: String): Boolean {
        return symbols.any { filename.startsWith(it) }
    }

    fun contains(vararg symbols: String): Boolean {
        return symbols.any { filename.contains(it) }
    }
}