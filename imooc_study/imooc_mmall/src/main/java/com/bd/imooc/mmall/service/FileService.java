package com.bd.imooc.mmall.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file, String path);
}
