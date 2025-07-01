package org.xedox.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.xedox.apkbuilder.ApkBuilder;
import org.xedox.demo.AssetsUtils;

public class MainActivity extends AppCompatActivity {

    private static final String APP_DIR = "app";
    private static final String SRC_DIR = "src";
    private static final String OUT_DIR = "out";
    private static final String ANDROID_JAR = "android.jar";
    private static final String DESUGAR_JDK_LIBS = "desugar_jdk_libs.jar";
    private static final String MANIFEST_FILE = "AndroidManifest.xml";
    private static final String RES_DIR = "res";
    private static final String SIGNED_APK = "signed_base.apk";

    private OutputView output;
    private File baseDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.output);
        new Thread(this::startCompilationProcess).start();
    }

    private void startCompilationProcess() {
        try {
            baseDir = getExternalFilesDir(null);
            try {
                AssetsUtils.copyAssetsFolder(this, "app", new File(baseDir, APP_DIR));
                AssetsUtils.copyAssetToFile(this, ANDROID_JAR, new File(baseDir, ANDROID_JAR));
                AssetsUtils.copyAssetToFile(
                        this, DESUGAR_JDK_LIBS, new File(baseDir, DESUGAR_JDK_LIBS));
            } catch (Throwable e) {
                e.printStackTrace();
            }
            File appDir = new File(baseDir, APP_DIR);
            File srcDir = new File(appDir, SRC_DIR);
            File outDir = new File(baseDir, OUT_DIR);
            File androidJar = new File(baseDir, ANDROID_JAR);
            File desugarJdkLibs = new File(baseDir, DESUGAR_JDK_LIBS);
            File manifestFile = new File(appDir, MANIFEST_FILE);
            File resDir = new File(appDir, RES_DIR);
            outDir.mkdirs();
            ApkBuilder.BuildConfig config = new ApkBuilder.BuildConfig();
            config.androidJarPath = androidJar.getAbsolutePath();
            config.buildPath = outDir.getAbsolutePath();
            config.javaSources.add(srcDir.getAbsolutePath());
            config.desugarJdkLibsPath = desugarJdkLibs.getAbsolutePath();
            config.manifestPath = manifestFile.getAbsolutePath();
            config.resDir = resDir.getAbsolutePath();

            ApkBuilder builder = new ApkBuilder(this, output.getPrintStream(), config);
            builder.build();

        } catch (Exception e) {
            e.printStackTrace(output.getPrintStream());
        }
    }

    private void appendOutput(String msg) {
        runOnUiThread(() -> output.append(msg + "\n"));
    }
}
