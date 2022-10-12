package kopo.poly.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICS3UploadService {

    // 파일 업로드 로직
    String fileUpload(MultipartFile mf, String dirName)throws Exception;

    // 업로드 파일 삭제 로직
    int fileDelete(String fileName) throws Exception;

}
