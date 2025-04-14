package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static void copyAssetsFolderToInternalStorage(Context context, String folderName) {
        AssetManager assetManager = context.getAssets();

        try {
            // List all files/folders in the current folder
            String[] files = assetManager.list(folderName);

            if (files == null || files.length == 0) {
                // If no files, it's a file (not a folder), so copy it
                copyAssetFileToInternalStorage(context, folderName);
            } else {
                // If it's a folder, iterate over its contents
                for (String fileName : files) {
                    String fullPath = folderName.isEmpty() ? fileName : folderName + "/" + fileName;
                    copyAssetsFolderToInternalStorage(context, fullPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyAssetFileToInternalStorage(Context context, String assetFileName) {
        AssetManager assetManager = context.getAssets();

        try (InputStream in = assetManager.open(assetFileName);
             FileOutputStream out = context.openFileOutput(assetFileName.replace("/", "_"), Context.MODE_PRIVATE)) {
            // Replace "/" with "_" to avoid directory structure issues in internal storage
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
