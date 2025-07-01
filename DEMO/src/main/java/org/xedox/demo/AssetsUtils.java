package org.xedox.demo;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class AssetsUtils {
    public static File copyAssetsFolder(Context context, String assetPath, File targetDir) throws IOException {
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            throw new IOException("Failed to create directory: " + targetDir);
        }

        String[] files = context.getAssets().list(assetPath);
        if (files == null) {
            throw new IOException("Asset path not found: " + assetPath);
        }

        for (String file : files) {
            String assetFilePath = assetPath + File.separator + file;
            File targetFile = new File(targetDir, file);

            String[] subFiles = context.getAssets().list(assetFilePath);
            if (subFiles != null && subFiles.length > 0) {
                copyAssetsFolder(context, assetFilePath, targetFile);
            } else {
                copyAssetToFile(context, assetFilePath, targetFile);
            }
        }

        return targetDir;
    }

    public static void copyAssetToFile(Context context, String assetName, File targetFile) throws IOException {
        try (InputStream in = context.getAssets().open(assetName);
                OutputStream out = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
    }
}
