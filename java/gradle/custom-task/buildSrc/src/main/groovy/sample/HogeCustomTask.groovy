package sample

import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class HogeCustomTask extends DefaultTask {
    final String message

    @Inject
    HogeCustomTask(String message) {
        this.message = message
    }

    @TaskAction
    def hoge() {
        println("Hello World. message=${this.message}")
    }
}