package com.kill.file.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 文件信息
 * @author kill
 */
@Data
@AllArgsConstructor
public class FileData {

    private String fileName;

    private int size;

    private byte[] data;

}
