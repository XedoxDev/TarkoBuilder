package org.xedox.apkbuilder.task;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.xedox.apkbuilder.ApkBuilder;
import org.xedox.apkbuilder.util.BinaryUtils;
import org.xedox.apkbuilder.util.TaskManager;
import org.xedox.javac.OptionsBuilder;

public class OptimizeTask implements TaskManager.Task {

    private ApkBuilder builder;

    public OptimizeTask(ApkBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void run() throws Exception {
        File baseApk = new File(builder.config.buildPath, "base.apk");
        File outputApk = new File(builder.config.buildPath, "base_optimized.apk");
        // aapt2 optimize -o output.apk --enable-sparse-encoding input.apk
        OptionsBuilder opt = new OptionsBuilder();
        opt.arg(builder.aapt2Binary.getAbsolutePath(), "optimize");
        opt.arg("-o", outputApk.getAbsolutePath());
        opt.arg("--enable-sparse-encoding");
        opt.arg(baseApk.getAbsolutePath());
        BinaryUtils.execute(opt.build());
        Files.move(outputApk.toPath(), baseApk.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
