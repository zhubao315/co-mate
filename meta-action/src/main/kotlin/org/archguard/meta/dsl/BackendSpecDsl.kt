package org.archguard.meta.dsl

import org.archguard.architecture.style.NamingStyle
import org.archguard.meta.AtomicAction


class NormalExampleRule : AtomicAction {
    fun pattern(regex: String) {

    }

    fun example(s: String) {

    }

}

interface MatcherResult {
    fun passed(): Boolean
    fun failureMessage(): String

    fun negatedFailureMessage(): String

    companion object {
        operator fun invoke(
            passed: Boolean,
            failureMessageFn: () -> String,
            negatedFailureMessageFn: () -> String,
        ) = object : MatcherResult {
            override fun passed(): Boolean = passed
            override fun failureMessage(): String = failureMessageFn()
            override fun negatedFailureMessage(): String = negatedFailureMessageFn()
        }
    }

}


class Naming : AtomicAction {
    val filename: String = ""

    private val conditions = mutableListOf<(String) -> Boolean>()
    fun endWiths(vararg suffixes: String) {
        conditions.add { file ->
            suffixes.any { file.endsWith(it) }
        }
    }
}

class LayeredRule {
    private var pattern: String? = null
    private var namingRules: Naming? = null

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        this.pattern = pattern
        this.namingRules = Naming().apply(block)
    }

    fun naming(function: Naming.() -> Unit): Naming {
        val rule = Naming()
        rule.function()
        return rule
    }

    fun dependsOn(s: String) {

    }
}

class DependencyRule {
    val rules = mutableListOf<Pair<String, String>>()
    infix fun String.dependedOn(to: String) {
        rules.add(this to to)
    }
}

class LayeredDeclaration : AtomicAction {
    val dependencyRules = HashMap<String, List<String>>()

    fun layer(name: String, function: LayeredRule.() -> Unit): LayeredRule {
        val rule = LayeredRule()
        rule.function()
        return rule
    }

    fun dependency(function: DependencyRule.() -> Unit): DependencyRule {
        val rule = DependencyRule()
        rule.function()
        return rule
    }

}

class NamingDeclaration {
    val `函数名`: String = ""

    fun startsWith(vararg symbols: String): (Unit) -> Unit {
        return { }
    }

    fun contains(vararg symbols: String): (Unit) -> Unit {
        return { }
    }

    fun style(style: String) {
        if (!NamingStyle.contains(style)) {
            throw IllegalArgumentException("Unknown naming style: $style. Supported styles: ${NamingStyle.valuesString()}")
        }
    }
}

class BackendSpecDsl {
    fun project_name(function: NormalExampleRule.() -> Unit): NormalExampleRule {
        val rule = NormalExampleRule()
        rule.function()
        return rule
    }

    fun layered(function: LayeredDeclaration.() -> Unit): LayeredDeclaration {
        val rule = LayeredDeclaration()
        rule.function()
        return rule
    }


    fun naming(function: NamingDeclaration.() -> Unit): NamingDeclaration {
        val rule = NamingDeclaration()
        rule.function()
        return rule
    }
//    fun class_name_style(style: String) {
//
//    }
//
//    fun method_name_style(style: String) {
//
//    }
//
//    fun variable_name_style(style: String) {
//
//    }

    fun exception(style: String) {

    }

    fun security(style: String) {

    }
}

fun backend(init: BackendSpecDsl.() -> Unit): BackendSpecDsl {
    val html = BackendSpecDsl()
    html.init()
    return html
}
