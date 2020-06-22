package com.paperfly.system.utils;

import com.paperfly.system.pojo.Info;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {


    public static String createZipAndReturnPath(List<Info> fileInfos, String cno) {
        String zipFilePath = fileInfos.get(0).getFilePath();
        zipFilePath = zipFilePath.substring(0, zipFilePath.lastIndexOf(cno));
        zipFilePath = zipFilePath + "/" + cno + "/" + cno + ".zip";

        //如果压缩文件存在了就删除
        File zipFile = new File(zipFilePath);
        zipFile.deleteOnExit();
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
            for (Info fileInfo : fileInfos) {
                fileInputStream = new FileInputStream(fileInfo.getFilePath());
                zipOutputStream.putNextEntry(new ZipEntry(fileInfo.getFileName()));
                int len = 0;
                byte[] bytes = new byte[1024 * 1024];
                while ((len = fileInputStream.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, len);
                    zipOutputStream.flush();
                }
                fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.flush();
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return zipFilePath;
    }
}