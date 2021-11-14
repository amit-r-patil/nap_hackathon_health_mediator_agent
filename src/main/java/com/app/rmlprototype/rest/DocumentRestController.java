package com.app.rmlprototype.rest;

import com.app.rmlprototype.entity.MLPrediction;
import com.app.rmlprototype.entity.UploadFileResponse;
import com.app.rmlprototype.service.DocumentStorageService;
import com.app.rmlprototype.service.MLPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentRestController {

    @Autowired
    private DocumentStorageService documneStorageService;

    @Autowired
    private MLPredictionService mlPredictionService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("userId") Integer UserId,
                                         @RequestParam("docType") String docType) {
        String fileName = documneStorageService.storeFile(file, UserId, docType);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("userId") Integer userId,
                                                 @RequestParam("docType") String docType,
                                                 HttpServletRequest request) {
        String fileName = documneStorageService.getDocumentName(userId, docType);
        Resource resource = null;
        if(fileName !=null && !fileName.isEmpty()) {
            try {
                resource = documneStorageService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllDocuments")
    public List<MLPrediction> getALlDocuments(){
        return mlPredictionService.findALl();
    }

    @GetMapping("/getDocumentById/{documentId}")
    public MLPrediction getDocumentById(@PathVariable int theId){
        return mlPredictionService.getDocumentById(theId);
    }

    @GetMapping("/getBySpeciality/{speciality}")
    public List<MLPrediction> getBySpeciality(@PathVariable String speciality){
        return mlPredictionService.findDocumentBySpeciality(speciality);
    }

}