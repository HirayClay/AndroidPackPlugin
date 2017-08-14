package pack.io.hiray.packer


class PackExt {
    def channels = []

    def enable = false

    def setChannels(List<String> ch) {
        channels = ch
    }

    def getChannels() {
        return channels
    }

    def setEnable(boolean b) {
        enable = b
    }

    def getEnable() {
        enable
    }
}