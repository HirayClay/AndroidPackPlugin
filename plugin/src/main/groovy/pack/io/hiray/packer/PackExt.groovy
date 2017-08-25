package pack.io.hiray.packer


class PackExt {
    def channels = []

    def logEnable = false

    def clearChannelFile = false

    def taskName = "apkPack"

    def format=""

    def prefix = "apk-"
    def setChannels(List<String> ch) {
        channels = ch
    }

    def getChannels() {
        return channels
    }

    def setLogEnable(boolean b) {
        logEnable = b
    }

    def getLogEnable() {
        logEnable
    }

    def getTaskName() {
        return taskName
    }

    void setTaskName(taskName) {
        this.taskName = taskName
    }

    def getFormat() {
        return format
    }

    void setFormat(String format) {
        this.format = format
    }

    def getPrefix() {
        return prefix
    }

    void setPrefix(prefix) {
        this.prefix = prefix
    }
}