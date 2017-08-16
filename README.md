# AndroidPackPlugin
gradle plugin help packing various channel apk


# Why
inspired by meituan's blog [美团Android自动化之旅—生成渠道包](https://tech.meituan.com/mt-apk-packaging.html),solution three is quite easy and
perfect,but the case is written in python lang ,so i write some code with [zip4j](https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j).
finally i realise it is a good chance to write my own gradle plugin


# Usage
config the plugin in your main module buid.gradle file

```
pack {
    channels = ["ele", "meizu", "huawei","xiaomi","oneplus"]
    logEnable true
    clearChannelFile true
    taskName "packTask"
}
```
and rebuid,in the gradle task list ,will add a task named "packTask",if you omit it ,the defautl taskName is "apkPack"
<img width="280" height=“60” src="https://github.com/HirayClay/AndroidPackPlugin/raw/master/static/task-shoot.png"></img>

just click the task and seconds later,all the apks are generated

<img width="250" height=“160” src="https://github.com/HirayClay/AndroidPackPlugin/raw/master/static/variants.png"></img>
