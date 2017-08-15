package pack.io.hiray.packer

import com.android.build.gradle.api.BaseVariant
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


        project.extensions.create("pack", PackExt)


//        variant vt
//        dependsOn vt.assemble
        project.afterEvaluate {
            project.android.applicationVariants.each { BaseVariant vt ->
                def task = project.tasks.findByName("apk-${vt.name}")
                if (task == null)
                    project.tasks.create("apk-${vt.name}", PackTask.class, {
                        tk->
                            tk.setVariant(vt)
                            tk.setExt(project.pack)
                            tk.dependsOn vt.assemble

                    })
            }
        }

    }
}