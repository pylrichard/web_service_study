package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.common.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 参数名file与ControllerTest.whenUploadSuccess里new MockMultipartFile的第一个参数名要相同
     */
    @PostMapping("/upload")
    public FileInfo update(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());

        String path = "xxx";
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        File localFile = new File(path, System.currentTimeMillis() + "." + extension);
        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //从request中获取下载文件路径
        String filePath = request.getParameter("xxxx");
        //try ()里定义的InputStream和OutputStream会被自动释放，不需要在finally里手动释放，JDK 1.7后出现的此语法
        try (InputStream inputStream = new FileInputStream(filePath);
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            //attachment;filename=指定默认下载文件名
            response.addHeader("Content-Disposition", "attachment;filename=download.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }
}
