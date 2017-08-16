package pack.io.hiray.packer

import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class ReadPackConfigTask extends DefaultTask {

    @Input
    NamedDomainObjectContainer<Element> config

    @TaskAction
    public void work() {
        config.each { element ->
            println element.name
        }
    }

}