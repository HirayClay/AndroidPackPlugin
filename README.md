# AndroidPackPlugin
gradle plugin help packing various channel apk


# Why
inspired by meituan's blog [美团Android自动化之旅—生成渠道包](https://tech.meituan.com/mt-apk-packaging.html),solution three is quite easy and
perfect,but the case is written in python lang ,so i write some code with [zip4j](https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j).
finally i realise it is a good chance to write my own gradle plugin


# Usage
add the classpath in your project build.gradle file like below
```
    buildscript {
        repositories {
           jcenter()
        }
        dependencies {
            classpath 'pack.io.hiray.packer:core:1.0.0'
        }
    }
```
config the plugin in your main module buid.gradle file

```
apply plugin:'android.packer'

pack {
    channels = ["ele", "meizu", "huawei","xiaomi","oneplus"]
    logEnable true
    clearChannelFile true
    taskName "packTask"
}
```
read out the channel like this:
```
    public String readChannel(Context context) {
            ApplicationInfo appinfo = context.getApplicationInfo();
            String sourceDir = appinfo.sourceDir;
            String ret = "";
            String channel = "no channel";
            ZipFile zipfile = null;
            try {
                zipfile = new ZipFile(sourceDir);
                Enumeration<?> entries = zipfile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = ((ZipEntry) entries.nextElement());
                    String entryName = entry.getName();
                    if (entryName.contains("channel")) {
                        ret = entryName;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (zipfile != null) {
                    try {
                        zipfile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    
            String[] split = ret.split("_");
            if (split != null && split.length >= 2) {
                channel = ret.substring(split[0].length() + 1);
    
            }
    
            return channel;
        }
```
and rebuild,in the gradle task list ,will add a task named "packTask",if you omit it ,the defautl taskName is "apkPack"
<img width="280" height=“60” src="https://github.com/HirayClay/AndroidPackPlugin/raw/master/static/task-shoot.png"></img>

just click the task and seconds later,all the apks are generated

<img width="250" height=“160” src="https://github.com/HirayClay/AndroidPackPlugin/raw/master/static/variants.png"></img>
