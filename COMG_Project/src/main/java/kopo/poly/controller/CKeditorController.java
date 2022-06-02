package kopo.poly.controller;


import kopo.poly.service.ICS3UploadService;
import kopo.poly.util.DateUtil;
import kopo.poly.util.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

@Slf4j
@Controller
public class CKeditorController {

    @Resource(name = "CS3UploadService")
    public ICS3UploadService cS3UploadService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadimg(HttpServletRequest request, HttpServletResponse response, MultipartFile upload, HttpSession session) throws Exception {
        log.info("CKeditorController : upload 시작! ");

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");


        //파일 원본파일명 가져오기
        String originalFileName = upload.getOriginalFilename();
        //파일 뒤 확장자만 가져오기
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        //파일을 바이트 배열로 변환
        byte[] bytes = upload.getBytes();

        // s3
        // 저장되는 파일이름 (영어, 숫자로 파일명 변경)
        String saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
        //  S3서버에 저장될 파일의 이름 #추후에 jsp에서 이 값을 가지고 이미지 처리
        String saveFilePath = cS3UploadService.fileUpload(upload, saveFileName);
        //서버에 저장된 파일의 경로
        String fullFileInfo = saveFilePath + "/" + saveFileName;

        log.info("originalFileName(원본파일 이름) : " + originalFileName);
        log.info("ext(원본파일 확장자) : " + ext);
        log.info("saveFileName(서버에 저장된 파일의 이름) : " + saveFileName);
        log.info("saveFileName(서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리) : " + saveFilePath);
        log.info("fullFileInfo(서버에 저장된 파일의 이름+확장자) : " + fullFileInfo);


        OutputStream out = new FileOutputStream("s3File");


        //서버에 write
        out.write(bytes);

        //성공여부 가져오기
        String callback = request.getParameter("CKEditorFuncNum");

        //클라이언트에 이벤트 추가 (자바스크립트 실행)
        PrintWriter printWriter = response.getWriter(); //자바스크립트 쓰기위한 도구

        String fileUrl = saveFilePath;

        if (!callback.equals("1")) { // callback이 1일 경우만 성공한 것
            printWriter.println("<script>alert('이미지 업로드에 실패했습니다.');" + "</script>");

        } else {
            log.info("upload img 들어온다! " + fileUrl);

            printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl + "','이미지가 업로드되었습니다.')" + "</script>");

        }

        printWriter.flush();
    }
}
