package com.web.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    //tạo file lưu trữ dựa vào id truyền vào
    String getStoredFileName(MultipartFile file, String id);

    //lưu
    void store(MultipartFile file, String storedFilename);

    //Nạp nội dung của file
    Resource loadAsResource(String filename);

    Path load(String filename);

    //xóa file
    void delete(String storedFilename) throws IOException;

    //khởi tạo thư mục
    void init();
}
