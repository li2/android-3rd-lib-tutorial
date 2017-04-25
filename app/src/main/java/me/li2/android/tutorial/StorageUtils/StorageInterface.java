package me.li2.android.tutorial.StorageUtils;

import java.io.File;

/**
 * Created by weiyi on 25/04/2017.
 * https://github.com/li2
 */

public interface StorageInterface {
    /**
     * Creating file with given name and with content in string format. <br>
     *
     * @param directoryName The directory name
     * @param fileName The file name
     */
    File createFile(String directoryName, String fileName);

    File createFile(String fileName);

    /**
     * Is file exists
     *
     * @param directoryName The directory name
     * @param fileName The file name
     * @return
     */
    boolean isFileExist(String directoryName, String fileName);

    boolean isFileExist(String fileName);

    /**
     * Get {@link File} object by name of directory or file
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    File getFile(String directoryName, String fileName);

    File getFile(String name);

    /**
     * Read string from file
     *
     * @param directoryName The directory name
     * @param fileName The file name
     * @return
     */
    String readTextFile(String directoryName, String fileName);
    String readTextFile(String fileName);

    void writeFile(String directoryName, String fileName, String content);
    void writeFile(String fileName, String content);

    void copyRawToFile(int rawId, String directoryName, String fileName);
    void copyRawToFile(int rawId, String fileName);
}
