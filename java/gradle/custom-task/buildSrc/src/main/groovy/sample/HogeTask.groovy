package sample

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class HogeTask extends DefaultTask {

    @TaskAction
    def hoge() {
        println("Hello Hoge.")
    }
}