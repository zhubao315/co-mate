package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec

class Scene(val description: String) {
    val steps: MutableList<NamedStep> = mutableListOf()

    fun Given(description: String) {
        steps.add(NamedStep(description))
    }

    fun And(description: String) {
        steps.add(NamedStep(description))
    }

    fun When(description: String) {
        steps.add(NamedStep(description))
    }

    fun Then(description: String) {
        steps.add(NamedStep(description))
    }

    inner class NamedStep(val description: String)
}

class StoryDeclaration(val name: String) {
    val scenes: MutableList<Scene> = mutableListOf()

    fun scene(scenario: String, function: Scene.() -> Unit): Scene {
        val scene = Scene(scenario)
        scene.function()
        scenes.add(scene)
        return scene
    }
}

class CaseFlowSpec(name: String, defaultRole: String) : Spec<String> {
    lateinit var start: Activity
    lateinit var end: Activity
    private val activities = mutableListOf<Activity>()

    fun activity(name: String, init: Activity.() -> Unit) {
        val activity = Activity(name)
        activity.init()
        activities.add(activity)
    }

    fun story(storyName: String, function: StoryDeclaration.() -> Unit): StoryDeclaration {
        val storyDeclaration = StoryDeclaration(storyName)
        storyDeclaration.function()
        return storyDeclaration
    }

    fun build() {
        // Workflow construction logic goes here
    }

    open inner class NamedActivity(open val name: String) {
        override fun toString(): String {
            return "NamedActivity(name='$name')"
        }
    }

    inner class Activity(override val name: String) : NamedActivity(name) {
        var task: Task = Task("")
        var next: NamedActivity? = null

        fun task(name: String, init: Task.() -> Unit) {
            val task = Task(name)
            task.init()
            this.task = task
        }

        override fun toString(): String {
            return "Activity(name='$name', activity=$task, next=$next)"
        }
    }

    inner class Task(val name: String) {
        var role: String = ""
        var story: List<String> = listOf()

        override fun toString(): String {
            return "Task(name='$name', role='$role', story='$story')"
        }
    }

    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun toString(): String {
        return "CaseFlowSpec(steps=$activities)"
    }

    companion object {
        fun defaultSpec(): CaseFlowSpec =
            // 使用如下的 DSL 编写一个 OKR 协作与管理系统 的需求全景。要求： 1. 你返回的内容格式如下：```kotlin
            caseflow("MovieTicketBooking", defaultRole = "User") {
                // activity's should consider all user activities
                activity("AccountManage") {
                    // task part should include all user tasks under the activity
                    task("UserRegistration") {
                        // you should list key steps in the story
                        story = listOf("Register with email", "Register with phone")
                    }
                    task("UserLogin") {
                        story += "Login to the website"
                    }
                }
                activity("MovieSelection") {}
                // ...
                activity("PaymentCancel") {
                    task("ConfirmCancel") {
                        role = "Admin" // if some task is role-specific, you can specify it here
                        //...
                    }
                }

                story("Register with email") {
                    scene("Registered email already exists") {
                        Given("Given an existing registered email \"user@example.com\"")
                        When("When I fill in the following information on the registration form")
                        And("I click the register button")
                        Then(" I should see a message indicating that the password and confirm password do not match")
                    }
                }
            }
    }
}

/**
 * CaseFlow DSL would be used to describe the workflow of a business case, like user journey, business process, etc.
 */
fun caseflow(name: String, defaultRole: String = "User", init: CaseFlowSpec.() -> Unit): CaseFlowSpec {
    val workflow = CaseFlowSpec(name, defaultRole)
    workflow.init()
    workflow.build()
    return workflow
}