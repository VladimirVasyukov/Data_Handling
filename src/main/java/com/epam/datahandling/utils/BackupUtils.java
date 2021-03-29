package com.epam.datahandling.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public final class BackupUtils {
    private static final Logger LOG = Logger.getLogger(BackupUtils.class);

    private BackupUtils() {
    }

    /**
     * Backup file. Create backup file if it is not exists. Override existing backup.
     *
     * @param src  Path to the source file
     * @param dest Path to backup file
     * @throws IOException If source file is not exist
     */
    public static void backup(String src, String dest) throws IOException {
        File destinationFile = new File(dest);
        if (checkDestFileExistence(destinationFile)) {
            try (
                FileInputStream fileInputStream = new FileInputStream(src);
                FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)
            ) {
                int i;
                while ((i = fileInputStream.read()) != -1) {
                    fileOutputStream.write(i);
                }
            }
        }
    }

    private static boolean checkDestFileExistence(File destinationFile) {
        boolean isDestFileExists = destinationFile.exists();

        File destinationDirectory = new File(destinationFile.getParent());
        boolean isDestDirectoryExists = destinationDirectory.exists();
        if (!isDestDirectoryExists) {
            isDestDirectoryExists = destinationDirectory.mkdirs();
        }

        try {
            if (!isDestFileExists) {
                isDestFileExists = destinationFile.createNewFile();
            }
        } catch (IOException ioException) {
            LOG.debug("Unexpected IOException in the checkDestFileExistence method!", ioException);
        }
        return isDestFileExists && isDestDirectoryExists;
    }

}
