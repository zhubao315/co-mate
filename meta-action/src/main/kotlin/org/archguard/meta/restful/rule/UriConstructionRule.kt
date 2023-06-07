package org.archguard.meta.restful.rule

import org.archguard.meta.restful.ApiRule
import org.archguard.meta.restful.RestApi

class UriConstructionRule : ApiRule("uri-construction") {
    private var ruleRegex: Regex? = null
    private var sample = ""

    fun rule(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: RestApi): Boolean {
        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.uri)
            return matchResult != null
        }

        return true
    }
}
