package com.kill.file.utils;

import com.alibaba.excel.EasyExcel;
import com.kill.file.listener.NoModelDataListener;
import com.kill.file.model.FileData;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 压缩文件工具类
 * @author kill
 * @date 2021-11-2
 */
public class ZipFileUtils {

    /**
     * 从zip压缩文件流中读取压缩的第一个文件的内容，转成字节
     * @param inputStream 压缩文件流
     * @return 文件信息
     * @throws IOException 异常
     */
    public static FileData readZip(InputStream inputStream) throws IOException {
        BufferedOutputStream outStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null) {
                return null;
            }
            String fileName = zipEntry.getName();
            byteArrayOutputStream = new ByteArrayOutputStream();
            outStream = new BufferedOutputStream(byteArrayOutputStream);
            byte[] buffer = new byte[4096];
            int len;
            int total = 0;
            while ((len = zipInputStream.read(buffer)) != -1) {
                outStream.write(buffer,0, len);
                total += len;
            }
            outStream.flush();
            byte[] dataArrTmp = byteArrayOutputStream.toByteArray();
            return new FileData(fileName, total, dataArrTmp);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    //
                }
            }
            if (null != outStream){
                try {
                    outStream.close();
                } catch (Exception e) {
                    //
                }
            }
            if (null != byteArrayOutputStream){
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e) {
                    //
                }
            }
        }
    }

    /**
     * 读取压缩文件中的excel数据
     * @param inputStream 输入流
     * @return 值的集合
     * @throws IOException 异常
     */
    public static List<Map<String, Object>> readZipData(InputStream inputStream) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null) {
                return null;
            }
            NoModelDataListener readListener = new NoModelDataListener();
            EasyExcel.read(zipInputStream, readListener).sheet().doRead();
            return readListener.getFinalData();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    //
                }
            }
        }
    }

}
