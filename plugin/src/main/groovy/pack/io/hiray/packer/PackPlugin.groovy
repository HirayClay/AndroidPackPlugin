package pack.io.hiray.packer

import org.gradle.api.Plugin
import org.gradle.api.Project

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin

public class PackPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        //zip4j
        //compile group: 'net.lingala.zip4j', name: 'zip4j', version: '1.3.2'

        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)

//        if (!hasApp || !hasLib)
//            throw new IllegalStateException("'android' or 'android-library' plugin is needed")

        project.extensions.create("pack", PackExt)

        def log = project.logger
        final def variants
        variants = project.android.applicationVariants


        project.task("INFO").doLast({
            def channels = project.pack.channels
            println(" is not disabled. and channel:$channels")
        })
//        variants.all { variant ->
//            if (!variant.buildType.isDebuggable()) {
//                log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
//            } else if (!project.pack.enable) {
//
//            }

//            logger.debug("packer enable: $enable  channels:$channels")
//        throw new IllegalStateException("the channel size:$channels  enable: $enable")
//        project.logger.info(Arrays.toString(channels))

//        project.afterEvaluate {
//            project.android.variants.each {
//
//
//            }
//        }

    }
}