package org.xedox.apkbuilder.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import org.xedox.apkbuilder.ApkBuilder;

public class ApkbuilderProperties {
    public static ApkBuilder.BuildConfig load(Properties props) {
        ApkBuilder.BuildConfig config = new ApkBuilder.BuildConfig();

        config.androidJarPath = props.getProperty("androidJarPath");
        config.buildPath = props.getProperty("buildPath");
        config.manifestPath = props.getProperty("manifestPath");
        config.resDir = props.getProperty("resDir");
        config.assetsDir = props.getProperty("assetsDir");
        config.nativeLibsDir = props.getProperty("nativeLibsDir");
        config.desugarJdkLibsPath = props.getProperty("desugarJdkLibsPath");
        config.proguardRulesPath = props.getProperty("proguardRulesPath");

        config.appPackage = props.getProperty("appPackage", "com.example.app");
        config.packageId = props.getProperty("packageId", "0x7f");

        config.versionName = props.getProperty("versionName", "1.0");
        config.versionCode = props.getProperty("versionCode", "1");
        config.minSdk = props.getProperty("minSdk", "21");
        config.targetSdk = props.getProperty("targetSdk", "33");
        config.javaVersion = props.getProperty("javaVersion", "17");

        config.debugMode = Boolean.parseBoolean(props.getProperty("debugMode", "true"));
        config.r8enabled = Boolean.parseBoolean(props.getProperty("r8enabled", "false"));
        config.apkAlignEnable = Boolean.parseBoolean(props.getProperty("apkAlignEnable", "true"));
        config.apkSignEnable = Boolean.parseBoolean(props.getProperty("apkSignEnable", "true"));
        config.aapt2OptimizeEnable =
                Boolean.parseBoolean(props.getProperty("aapt2OptimizeEnable", "true"));

        String sources = props.getProperty("javaSources");
        if (sources != null) {
            String[] sourceArray = sources.split(";");
            for (String source : sourceArray) {
                if (!source.trim().isEmpty()) {
                    config.javaSources.add(source.trim());
                }
            }
        }

        config.keyConfig.useKeystore =
                Boolean.parseBoolean(props.getProperty("keyConfig.useKeystore", "false"));

        config.keyConfig.keystore.path = props.getProperty("keyConfig.keystore.path");
        config.keyConfig.keystore.alias = props.getProperty("keyConfig.keystore.alias");
        config.keyConfig.keystore.storePassword =
                props.getProperty("keyConfig.keystore.storePassword");
        config.keyConfig.keystore.keyPassword = props.getProperty("keyConfig.keystore.keyPassword");

        config.keyConfig.keyWithCert.keyPath = props.getProperty("keyConfig.keyWithCert.keyPath");
        config.keyConfig.keyWithCert.certPath = props.getProperty("keyConfig.keyWithCert.certPath");
        return config;
    }

    public static Properties generateDefaultProperties() {
        Properties props = new Properties();

        props.setProperty("androidJarPath", "");
        props.setProperty("buildPath", "build");
        props.setProperty("manifestPath", "AndroidManifest.xml");
        props.setProperty("resDir", "res");
        props.setProperty("assetsDir", "assets");
        props.setProperty("nativeLibsDir", "libs");
        props.setProperty("desugarJdkLibsPath", "");
        props.setProperty("proguardRulesPath", "");

        props.setProperty("appPackage", "com.example.app");
        props.setProperty("packageId", "0x7f");

        props.setProperty("versionName", "1.0");
        props.setProperty("versionCode", "1");
        props.setProperty("minSdk", "21");
        props.setProperty("targetSdk", "33");
        props.setProperty("javaVersion", "17");

        props.setProperty("debugMode", "true");
        props.setProperty("r8enabled", "false");
        props.setProperty("apkAlignEnable", "true");
        props.setProperty("apkSignEnable", "true");
        props.setProperty("aapt2OptimizeEnable", "true");

        props.setProperty("javaSources", "src/main/java");

        props.setProperty("keyConfig.useKeystore", "false");
        props.setProperty("keyConfig.keystore.path", "");
        props.setProperty("keyConfig.keystore.alias", "");
        props.setProperty("keyConfig.keystore.storePassword", "");
        props.setProperty("keyConfig.keystore.keyPassword", "");
        props.setProperty("keyConfig.keyWithCert.keyPath", "");
        props.setProperty("keyConfig.keyWithCert.certPath", "");

        return props;
    }

    public static void saveDefaultConfig(String filePath) throws IOException {
        Properties props = generateDefaultProperties();
        try (OutputStream output = new FileOutputStream(filePath)) {
            props.store(output, "APK Builder Configuration");
        }
    }
}
