package org.archguard.spec.lang.domain.declaration

class ContextDeclaration(name: String) {
    fun aggregate(name: String, function: AggregateDeclaration.() -> Unit): AggregateDeclaration {
        val aggregateDeclaration = AggregateDeclaration(name)
        aggregateDeclaration.function()
        return aggregateDeclaration
    }

    infix fun dependedOn(targetContextDeclaration: ContextDeclaration) {
        
    }
}