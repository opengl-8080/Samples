package sample

import org.gradle.api.Project
import org.gradle.api.Plugin

class FooPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("foo") {
            doFirst { println("FOO!!") }
        }
    }
}