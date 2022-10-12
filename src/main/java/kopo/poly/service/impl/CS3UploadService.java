package kopo.poly.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kopo.poly.service.ICS3UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;


@Slf4j
@Service("CS3UploadService")
public class CS3UploadService implements ICS3UploadService {
    @Value("${cloud.aws.s3.bucket}")
    public String bucketName;

    private final AmazonS3 amazonS3;

    public CS3UploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    /*
    * 스프링에서 제공하는 multipartFile을 자바의 파일객체 file로 변환
    * */
    @Override
    public String fileUpload(MultipartFile multipartFile, String dirName) throws Exception {
        log.info("CS3UploadService : fileUpload 시작 !");
        log.info(this.getClass().getName()+"multpartFile 자바 file 객체로 변환 시작!");

        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        log.info(this.getClass().getName()+"multpartFile 자바 file 객체로 변환 종료!");
        log.info("CS3UploadService : fileUpload 끝 !");
        return upload(uploadFile, dirName);
    }


    // 파일 삭제하기
    @Override
    public int fileDelete(String fileName) throws Exception {
        int res = 0;

        amazonS3.deleteObject(bucketName, fileName);

        res = 1;
        return res;
    }

    public String upload(File uploadFile, String filePath) {
        log.info("CS3UploadService : upload 시작 !");
        log.info(this.getClass().getName()+"파일 업로드 전 로직 시작!!");

        String fileName = filePath + "/" + uploadFile.getName();   // S3에 저장된 파일 이름

        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        removeNewFile(uploadFile); //로컬에 저장된 이미지 파일 지우기

        log.info(this.getClass().getName()+"파일 업로드 전 로직 종료!!");
        log.info("CS3UploadService : upload 끝 !");
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        log.info("CS3UploadService : putS3 시작 !");
        log.info(this.getClass().getName()+"파일 업로드 시작!");

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        log.info(this.getClass().getName()+"파일 업로드 종료!");
        log.info("CS3UploadService : putS3 끝 !");
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제 성공!");
            return;
        }
        log.info("파일 삭제 실패");
    }

    //convert null check
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return Optional.of(convFile);
    }
}
