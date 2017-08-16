package pack.io.hiray.packer


class PackExt {
    def channels = []

    def logEnable = false

    def clearChannelFile = false

    def taskName = "apkPack"

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
}