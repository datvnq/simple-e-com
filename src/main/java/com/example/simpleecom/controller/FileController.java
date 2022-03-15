package com.example.simpleecom.controller;

import com.example.simpleecom.dto.FileUploadResponse;
import com.example.simpleecom.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public FileUploadResponse fileUpload(@RequestParam("file")MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String contentType = file.getContentType();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();

        FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
        return response;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.downloadFile(fileName);
        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.getFilename())
                .body(resource);
    }
}
