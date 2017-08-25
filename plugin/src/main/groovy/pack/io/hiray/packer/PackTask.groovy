package pack.io.hiray.packer

import com.android.build.gradle.api.BaseVariant
import groovy.text.SimpleTemplateEngine
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.ZipParameters
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.slf4j.Marker
import org.slf4j.MarkerFactory

class PackTask extends DefaultTask {

    Marker marker = MarkerFactory.getMarker("PackTask")
    @Input
    BaseVariant variant

    @Input
    PackExt ext

    List<File> tempChannelFiles

    SimpleTemplateEngine formatEngine

    @TaskAction
    public void pack() {
        long startTimeStamp = System.currentTimeMillis()
        File apkFile = variant.outputs[0].outputFile

        List<String> channels = ext.channels
        tempChannelFiles = new ArrayList<>(channels.size())
        for (String ch : channels) {
            String copiedApkPath = copyChannelApk(ch, apkFile)
            File cf = createChannelFile(apkFile, ch)
            if (ext.clearChannelFile)
                tempChannelFiles.add(cf)
            writeChannelToApk(cf, new File(copiedApkPath))
        }

        clearChannelFile(ext.clearChannelFile)
        long dur = System.currentTimeMillis() - startTimeStamp
        if (ext.logEnable)
            log("the pack task consume time: ${dur / 1000} seconds in ${channels.size()} channel")
    }

    def clearChannelFile(boolean b) {
        if (b & tempChannelFiles != null)
            tempChannelFiles.each { f -> f.delete() }
    }


    def createChannelFile(File root, String channel) {
        File channelFile = new File(root.getParent() + File.separator + "channel_${channel}")
        if (!channelFile.exists())
            channelFile.createNewFile()
        channelFile
    }


    def writeChannelToApk(File channelFile, File apkFile) {
        ZipFile zFile = new ZipFile(apkFile);

        try {
            zFile.setFileNameCharset("UTF-8");
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setRootFolderInZip("META-INF");
            zFile.addFile(channelFile, zipParameters);
            if (ext.logEnable)
                log("zipFileSizeAfterWriteChannel:${zFile.file.size()}")
        } catch (ZipException e) {
            e.printStackTrace();
            print("Pack Failed :$e.getMessage()")
        }
    }


    static def copyChannelApk(String channelName, File srcApk) {
        if (!formatEngine)
            formatEngine = new SimpleTemplateEngine()
        String channelFormat = ext.format
        def binding = [
                "prefix"     : ext.prefix,
                "versionName": variant.mergedFlavor.versionCode,
                "channelName": channelName,
                "buildType"  : variant.buildType.name
        ]
        String finalName = formatEngine.createTemplate(channelFormat).make(binding)
        File destApk = new File(srcApk.getParent() + File.separator + "${finalName}.apk")
        srcApk.withInputStream { is ->
            def buf = new byte[1024 * 1024]
            int len = -1
            destApk.withOutputStream { os ->
                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len)
                    os.flush()
                }
            }
        }

        destApk
    }


    public void log(String s) {
        logger.error(marker, s)
    }
}