package pack.io.hiray.packer

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

import com.android.build.gradle.AppPlugin

public class PackPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        //zip4j
        //compile group: 'net.lingala.zip4j', name: 'zip4j', version: '1.3.2'

        def hasApp = project.plugins.withType(AppPlugin)
//        def hasLib = project.plugins.withType(LibraryPlugin)

        if (!hasApp)
            throw new IllegalStateException("'com.android.application' plugin is required")


        def packExt = project.extensions.create("pack", PackExt)

        NamedDomainObjectContainer<Element> packConfig = project.container(Element)
        project.extensions.add('packConfig', packConfig)

//        variant vt
//        dependsOn vt.assemble
        project.afterEvaluate {
            project.tasks.create("readConfig", ReadPackConfigTask.class, {
                task ->
                    task.config = project.extensions.findByName("packConfig")
            })


            project.android.applicationVariants.each { BaseVariant vt ->
                def task = project.tasks.findByName(packExt.taskName)
                if (task == null)
                    project.tasks.create(packExt.taskName, PackTask.class, {
                        tk ->
                            tk.setVariant(vt)
                            tk.setExt(project.pack)
                            tk.dependsOn vt.assemble

                    })
            }
        }

    }
}