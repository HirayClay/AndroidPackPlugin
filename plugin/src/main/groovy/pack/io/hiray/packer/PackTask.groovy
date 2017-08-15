package pack.io.hiray.packer

import com.android.build.gradle.api.BaseVariant
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

    @TaskAction
    public void pack() {
        File apkFile = variant.outputs[0].outputFile

        ZipFile zFile = new ZipFile(apkFile.absolutePath);
        List<String> channels = ext.channels
        File channelFile = new File(apkFile.getParent() + File.separator + "channel_official")
        if (!channelFile.exists())
            channelFile.createNewFile()
        for (String ch : channels) {
            String copiedApkPath = copyChannelApk(ch, apkFile)
            writeChannelToApk(ch, new File(copiedApkPath))
        }
    }


    def writeChannelToApk(String channelName, File apkFile) {
        ZipFile zFile = new ZipFile(apkFile);
        File channelFile = new File(apkFile.getParent() + File.separator + "channel_${channelName}")
        if (!channelFile.exists())
            channelFile.createNewFile()
        log("zipFile:" + zFile.isValidZipFile())

        if (zFile.isValidZipFile())
            log("zipFileSizeBeforeWriteChannel:${zFile.file.size()}")
        try {
            zFile.setFileNameCharset("UTF-8");

            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setRootFolderInZip("META-INF");
            zFile.addFile(channelFile, zipParameters);
            log("zipFileSizeAfterWriteChannel:${zFile.file.size()}")
            log("task done")
        } catch (ZipException e) {
            e.printStackTrace();
            print("Pack Failed :$e.getMessage()")
        }
    }


    def copyChannelApk(String channelName, File srcApk) {
        File destApk = new File(srcApk.getParent() + File.separator + "apk-${channelName}.apk")
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

    def

    public void log(String s) {
        logger.error(marker, s)
    }
}