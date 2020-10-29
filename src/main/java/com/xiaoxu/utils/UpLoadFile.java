package com.xiaoxu.utils;

import com.xiaoxu.error.BusinessException;
import com.xiaoxu.error.EmBusinessError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created on 2019/11/27 9:49
 *
 * @author Xiaoxu
 */
public class UpLoadFile {
    public static String upLoadFile(MultipartFile file, String localPath, String fileName) throws BusinessException {
        String newFileName = getUniqueFileName(fileName);
        String path = localPath + "/" + newFileName;
        File destination = new File(path);
        if (!destination.getParentFile().exists()) {
            destination.getParentFile().mkdir();
        }
        //保存文件
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new BusinessException(EmBusinessError.UPLOAD_FILE_FAILED);
        }
        return newFileName;
    }

    private static String getUniqueFileName(String fileName){
       return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
    }
}
