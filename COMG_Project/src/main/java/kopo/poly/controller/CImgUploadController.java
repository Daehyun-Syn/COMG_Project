package kopo.poly.controller;

import kopo.poly.dto.*;
import kopo.poly.service.*;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Controller
public class CImgUploadController {

    @Resource(name = "CS3UploadService")
    public ICS3UploadService cS3UploadService;

    @Resource(name = "CFileUploadService")
    public ICFileUploadService iFileUploadService;

    @Resource(name = "CGroupService")
    private ICGroupService cGroupService;

    @Resource(name = "CjoinService")
    private ICJoinService cjoinService;

    @Resource(name = "CBoardService")
    private ICBoardService cBoardService;

    @Resource(name = "CAssignmentService")
    private ICAssignmentService cAssignmentService;


    @ResponseBody
    @PostMapping(value = "img/imgUpload")
    public int imgUpload(HttpServletRequest request, HttpSession session,
                         @RequestParam(value = "uploadImgUser") MultipartFile mf) throws Exception {
        log.info("CImgUploadController : imgUpload 시작!");

        int res = 0;
        int res2 = 0;
        int res3 = 0;
        CFileDTO fDTO = null;
        CFileDTO oDTO = null;
        CFileDTO gDTO = null;
        CUserDTO tDTO = null;

        String seq = "";

        //파일 원본파일명 가져오기
        String originalFileName = mf.getOriginalFilename();
        //파일 뒤 확장자만 가져오기
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();


        // 저장되는 파일이름 (영어, 숫자로 파일명 변경)
        String saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
        //  S3서버에 저장될 파일의 이름
        String saveFilePath = cS3UploadService.fileUpload(mf, saveFileName);
        //서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리
        String fullFileInfo = saveFilePath + "/" + saveFileName;
        // 파일이름과 패스가 중복될시 구분하기 위한 코드
        String fileOriginCode = RandomStringUtils.getRamdomPassword(8);
        log.info("파일 구분 코드  : " + fileOriginCode);
        log.info("originalFileName(원본파일 이름) : " + originalFileName);
        log.info("ext(원본파일 확장자) : " + ext);
        log.info("saveFileName(서버에 저장된 파일의 이름) : " + saveFileName);
        log.info("saveFileName(서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리) : " + saveFilePath);
        log.info("fullFileInfo(서버에 저장된 파일의 이름+확장자) : " + fullFileInfo);
        String from_table = request.getParameter(CmmUtil.nvl("from_table"));
        log.info("어떤 테이블에서 파일 업로드를 호출했는지 확인  : " + from_table);
        String file_code = RandomStringUtils.getRamdomPassword(8);
        log.info("seq 조회위해 임시로 발급한 8자리 파일구분코드  : " + file_code);
        // FILE_INFO 테이블에 들어갈 데이터
        fDTO = new CFileDTO();
        fDTO.setFile_from_table(from_table); // 파일 유입 경로(어느테이블에서 왔는지)
        fDTO.setFile_code(file_code);
        res = iFileUploadService.InsertFileInfo(fDTO);

        if (res == 1) {
            log.info("FILE_INFO 테이블에 정상적으로 데이터가 저장됨");
            oDTO = new CFileDTO();
            oDTO = iFileUploadService.FileSeqSearch(fDTO);
            // FILE_MORE_INFO 테이블에 들어갈 데이터
            gDTO = new CFileDTO();
            // FILE_SEQ값 조회 로직
            if (oDTO.getFile_seq() == null) {
                log.info("file_code로 file_seq값 가져오기 실패!");
            } else {
                seq = CmmUtil.nvl(oDTO.getFile_seq());
                gDTO.setFile_seq(seq); //FILE_INFO(파일 정보)테이블에 해당하는 SEQ번호
            }
            gDTO.setServer_file_name(saveFileName); // S3서버에 저장된 파일이름
            gDTO.setServer_file_path(saveFilePath); // 서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리
            gDTO.setOrigin_file_name(originalFileName); //원본파일이름
            gDTO.setOrigin_file_extension(ext); // 원본파일 확장자

            log.info("FILE_MORE_INFO 테이블에 데이터 저장 시작");
            res2 = iFileUploadService.InsertFileMoreInfo(gDTO);
            if (res2 == 1) {
                log.info("FILE_MORE_INFO 테이블에 정상적으로 데이터가 저장됨");
                if (Objects.equals(from_table, "GROUP_INFO")) {
                    log.info("JSP에서 받아온 from_table 값이 GROUP_INFO 라면 GROUP_INFO테이블에 값 넣기 실행");
                    CGroupDTO rDTO = null;
                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");


                    String group_name = CmmUtil.nvl(request.getParameter("group_name"));
                    String group_reg_id = CmmUtil.nvl(tDTO.getUser_id());
                    String group_password = CmmUtil.nvl(request.getParameter("group_pwd"));
                    String group_introduce = CmmUtil.nvl(request.getParameter("group_introduce"));
                    rDTO = new CGroupDTO();
                    rDTO.setGroup_name(group_name);
                    rDTO.setGroup_regid(group_reg_id);
                    rDTO.setGroup_password(group_password);
                    rDTO.setGroup_introduce(group_introduce);
                    rDTO.setFile_seq(seq);
                    res3 = cGroupService.InsertGroupInfo(rDTO);
                    if (res3 == 1) {
                        log.info("그룹 생성 성공! : 그룹 생성이 성공하였습니다.");
                        // 그룹 생성 성공하여 DB에서 GROUP_SEQ 받아옴
                        CGroupDTO xDTO = null;
                        xDTO = new CGroupDTO();
                        xDTO = cGroupService.getGroupSeq(rDTO);
                        if (xDTO == null) {
                            res3 = 3;
                            log.info("그룹 seq 가져오기 실패!");

                        } else {
                            log.info("그룹 seq 가져오기 성공!");
                            String fk_user_seq = CmmUtil.nvl(tDTO.getUser_seq());
                            String fk_group_seq = CmmUtil.nvl(xDTO.getGroup_seq());
                            String user_id = CmmUtil.nvl(tDTO.getUser_id());
                            ;
                            String user_auth = "1";
                            rDTO = null;
                            rDTO = new CGroupDTO();
                            rDTO.setFk_user_seq(fk_user_seq);
                            rDTO.setFk_group_seq(fk_group_seq);
                            rDTO.setUser_id(user_id);
                            rDTO.setUser_auth(user_auth);
                            int InsertAdmin = cGroupService.InsertGroupUserAdminInfo(rDTO);

                            if (InsertAdmin == 1) {
                                log.info("CgroupController : 그룹 어드민 등록 성공!");
                                res3 = 1;
                            } else {
                                log.info("CgroupController : 그룹 어드민 등록 실패!");
                                res3 = 3;
                            }
                        }
                    } else if (res3 == 2) {
                        log.info("그룹 생성 실패! : 그룹 이름이 이미 존재합니다.");
                    } else {
                        log.info("그룹 생성 실패! : 오류로 인해 그룹 생성이 실패하였습니다.");
                    }
                } else if (Objects.equals(from_table, "USER_INFO")) {
                    log.info("JSP에서 받아온 from_table 값이 USER_INFO 라면 USER_INFO테이블에 업데이트문 실행");
                    CUserDTO rDTO = null;
                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");

                    String user_id = CmmUtil.nvl(tDTO.getUser_id());
                    rDTO = new CUserDTO();
                    rDTO.setUser_id(user_id);
                    rDTO.setFile_seq(seq);
                    res3 = cjoinService.UpdateUserInfo(rDTO);
                    if (res3 == 1) {
                        log.info("프로필 변경 성공! : 프로필 변경이 성공하였습니다.");
                    } else {
                        log.info("프로필 변경! : 오류로 인해 프로필 변경이 실패하였습니다.");
                    }
                } else if (Objects.equals(from_table, "BOARD_INFO")) {
                    log.info("JSP에서 받아온 from_table 값이 BOARD_INFO 라면 BOARD_INFO테이블에 인서트문 실행");
                    CBoardDTO rDTO = null;
                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");

                    String user_id = CmmUtil.nvl(tDTO.getUser_id());
                    String contents = CmmUtil.nvl(request.getParameter("COMG_Board_Contents"));
                    String user_seq = CmmUtil.nvl(tDTO.getUser_seq());
                    String fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
                    String fk_group_user_seq = CmmUtil.nvl(request.getParameter("FK_GROUP_USER_SEQ"));

                    log.info("fk_group_seq : " + fk_group_seq);
                    log.info("fk_group_user_seq : " + fk_group_user_seq);
                    log.info("contents : " + CmmUtil.nvl(request.getParameter("COMG_Board_Contents")));

                    rDTO = new CBoardDTO();
                    rDTO.setBoard_contents(contents);
                    rDTO.setBoard_writer(user_id);
                    rDTO.setFk_user_seq(user_seq);
                    rDTO.setFk_group_seq(fk_group_seq);
                    rDTO.setFk_group_user_seq(fk_group_user_seq);
                    rDTO.setFile_seq(seq);

                    res3 = cBoardService.InsertBoardInfo(rDTO);
                    if (res3 == 1) {
                        log.info("게시글 작성 완료 !");
                    } else {
                        log.info("게시글 작성 실패 ! : 로직 에러 .");
                    }

                } else if (Objects.equals(from_table, "BOARD_UPDATE")) {
                    log.info("JSP에서 받아온 from_table 값이 BOARD_UPDATE 라면 BOARD_INFO테이블에 업데이트문 실행");
                    CBoardDTO rDTO = null;
                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");

                    String user_id = CmmUtil.nvl(tDTO.getUser_id());
                    String contents = request.getParameter("COMG_Board_Contents");
                    String board_seq = CmmUtil.nvl(request.getParameter("board_seq"));

                    rDTO = new CBoardDTO();
                    rDTO.setBoard_contents(contents);
                    rDTO.setBoard_reg_user(user_id);
                    rDTO.setBoard_seq(board_seq);
                    rDTO.setFile_seq(seq);

                    res3 = cBoardService.UpdateBoardInfo(rDTO);
                    if (res3 == 1) {
                        log.info("게시글 수정 완료 !");
                    } else {
                        log.info("게시글 수정 실패 ! : 로직 에러 .");
                    }
                } else if (Objects.equals(from_table, "UPDATE_GROUP")) {
                    log.info("JSP에서 받아온 from_table 값이 UPDATE_GROUP 라면 GROUP_INFO테이블에 업데이트문 실행");

                    CBoardDTO rDTO = null;
                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");

                    log.info("세션에 담긴 DTO에서 꺼내온 값 :" + tDTO.getUser_name());
                    CGroupDTO bDTO = null;
                    bDTO = new CGroupDTO();

                    String user_id = CmmUtil.nvl(tDTO.getUser_id());
                    String group_introduce = request.getParameter("group_introduce");
                    String group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
                    log.info("CImgUploadController : user_id = " + user_id);
                    bDTO.setGroup_regid(user_id);
                    bDTO.setGroup_introduce(group_introduce);
                    bDTO.setGroup_seq(group_seq);
                    bDTO.setFile_seq(seq);

                    res3 = cGroupService.UpdateGroupInfo(bDTO);
                    if (res3 == 1) {
                        log.info("CImgUploadController : 그룹정보 수정 완료 !");
                    } else {
                        log.info("CImgUploadController : 그룹정보 수정 실패 !");
                    }

                    log.info("CgroupController : EditGroupLogic 끝 !");
                } else if (Objects.equals(from_table, "CREATE_ASSIGNMENT")) {
                    log.info("JSP에서 받아온 from_table 값이 UPDATE_GROUP 라면 CREATE_ASSIGNMENT테이블에 인서트문 실행");

                    CAssignmentDTO rDTO = null;
                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");

                    String user_id = CmmUtil.nvl(tDTO.getUser_id());
                    String assignment_room_name = CmmUtil.nvl(request.getParameter("assignment_name"));
                    String fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
                    String assignment_room_contents = CmmUtil.nvl(request.getParameter("COMG_assignment_Contents"));
                    String assignment_deadline = CmmUtil.nvl(request.getParameter("assignment_deadline"));

                    int[] profiles = {62, 63, 64, 65, 66, 67, 68, 69};
                    Random rand = new Random();
                    int int_file_seq = profiles[rand.nextInt(8)];
                    String assignment_profile_seq = int_file_seq + "";

                    log.info("contents : " + assignment_room_contents);
                    rDTO = new CAssignmentDTO();
                    rDTO.setAssignment_room_name(assignment_room_name);
                    rDTO.setAssignment_room_creater(user_id);
                    rDTO.setAssignment_room_contents(assignment_room_contents);
                    rDTO.setAssignment_deadline(assignment_deadline);
                    rDTO.setFk_group_seq(fk_group_seq);
                    rDTO.setAssignment_profile_seq(assignment_profile_seq);
                    rDTO.setFile_seq(seq);


                    res3 = cAssignmentService.InsertAssignmentInfo(rDTO);
                    if (res3 == 1) {
                        log.info("과제 생성 완료 !");
                    } else {
                        log.info("과제 생성 실패 ! : 로직 에러 .");
                    }
                }
            } else {
                log.info("FILE_MORE_INFO 테이블에 데이터 저장 실패 !");
            }
        } else {
            log.info("FILE_INFO 테이블에 데이터 저장 실패 !");
        }


        log.info("CImgUploadController : imgUpload 끝!");
        return res3;
    }

    //img download
    @GetMapping(value = "/downloadfile")
    public ResponseEntity<byte[]> download(HttpServletRequest request, ModelMap model) throws Exception {

        String filename = CmmUtil.nvl(request.getParameter("filename"));


        return cS3UploadService.getObject(filename);
    }

    //file upload
    @PostMapping(value = "file/fileUpload")
    public String fileUpload(HttpServletRequest request, HttpSession session, ModelMap model, @RequestParam(value = "uploadImgUser") MultipartFile mf) throws Exception {
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        int res = 0;
        int res2 = 0;
        int res3 = 0;
        CFileDTO fDTO = null;
        CFileDTO oDTO = null;
        CFileDTO gDTO = null;
        CUserDTO tDTO = null;
        String seq = "";

        String assignment_user_name = "";
        String fk_group_seq = "";
        String fk_assignment_room_seq = "";
        String file_seq = "";


        //파일 원본파일명 가져오기
        String originalFileName = mf.getOriginalFilename();
        //파일 뒤 확장자만 가져오기
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();


        // 저장되는 파일이름 (영어, 숫자로 파일명 변경)
        String saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
        //  S3서버에 저장될 파일의 이름
        String saveFilePath = cS3UploadService.fileUpload(mf, saveFileName);
        //서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리
        String fullFileInfo = saveFilePath + "/" + saveFileName;
        // 파일이름과 패스가 중복될시 구분하기 위한 코드
        String fileOriginCode = RandomStringUtils.getRamdomPassword(8);
        log.info("파일 구분 코드  : " + fileOriginCode);
        log.info("originalFileName(원본파일 이름) : " + originalFileName);
        log.info("ext(원본파일 확장자) : " + ext);
        log.info("saveFileName(서버에 저장된 파일의 이름) : " + saveFileName);
        log.info("saveFileName(서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리) : " + saveFilePath);
        log.info("fullFileInfo(서버에 저장된 파일의 이름+확장자) : " + fullFileInfo);
        String from_table = request.getParameter(CmmUtil.nvl("from_table"));
        log.info("어떤 테이블에서 파일 업로드를 호출했는지 확인  : " + from_table);
        String file_code = RandomStringUtils.getRamdomPassword(8);
        log.info("seq 조회위해 임시로 발급한 8자리 파일구분코드  : " + file_code);
        // FILE_INFO 테이블에 들어갈 데이터
        fDTO = null;
        fDTO = new CFileDTO();
        fDTO.setFile_from_table(from_table); // 파일 유입 경로(어느테이블에서 왔는지)
        fDTO.setFile_code(file_code);
        res = iFileUploadService.InsertFileInfo(fDTO);

        if (res == 1) {
            log.info("FILE_INFO 테이블에 정상적으로 데이터가 저장됨");
            oDTO = new CFileDTO();
            oDTO = iFileUploadService.FileSeqSearch(fDTO);
            // FILE_MORE_INFO 테이블에 들어갈 데이터
            gDTO = new CFileDTO();
            // FILE_SEQ값 조회 로직
            if (oDTO.getFile_seq() == null) {
                log.info("file_code로 file_seq값 가져오기 실패!");
            } else {
                seq = CmmUtil.nvl(oDTO.getFile_seq());
                gDTO.setFile_seq(seq); //FILE_INFO(파일 정보)테이블에 해당하는 SEQ번호
            }
            gDTO.setServer_file_name(saveFileName); // S3서버에 저장된 파일이름
            gDTO.setServer_file_path(saveFilePath); // 서버에 저장된 파일의 경로 #추후에 jsp에서 이 값을 가지고 이미지 처리
            gDTO.setOrigin_file_name(originalFileName); //원본파일이름
            gDTO.setOrigin_file_extension(ext); // 원본파일 확장자

            log.info("FILE_MORE_INFO 테이블에 데이터 저장 시작");
            res2 = iFileUploadService.InsertFileMoreInfo(gDTO);
            if (res2 == 1) {
                log.info("FILE_MORE_INFO 테이블에 정상적으로 데이터가 저장됨");
                if (Objects.equals(from_table, "assignment_sending")) {
                    log.info("JSP에서 받아온 from_table 값이 assignment_sending 라면 ASSIGNMENT_SEND_INFO테이블에 인서트문 실행");

                    tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");


                    String user_seq = CmmUtil.nvl(tDTO.getUser_seq());
                    assignment_user_name = CmmUtil.nvl(tDTO.getUser_name());
                    fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
                    fk_assignment_room_seq = CmmUtil.nvl(request.getParameter("assignment_room_seq"));
                    file_seq = seq;

                    CGroupDTO cDTO = new CGroupDTO();
                    CGroupDTO nDTO = new CGroupDTO();

                    nDTO.setFk_user_seq(tDTO.getUser_seq());
                    cDTO = cGroupService.selectGroupUserSeq(nDTO);
                    log.info("가져온 user_seq 결과는 ? " + cDTO.getFk_user_seq());

                    CAssignmentDTO aDTO = new CAssignmentDTO();
                    aDTO.setAssignment_user_name(assignment_user_name);
                    aDTO.setFk_group_user_seq(cDTO.getGroup_user_seq());
                    aDTO.setFk_user_seq(user_seq);
                    aDTO.setFk_group_seq(fk_group_seq);
                    aDTO.setFk_assignment_room_seq(fk_assignment_room_seq);
                    aDTO.setFile_seq(file_seq);

                    res3 = cAssignmentService.insertAssignmentSend(aDTO);

                    if (res3 == 1) {
                        alert_title = "과제제출 완료!";
                        alert_contents = "과제가 정상적으로 제출되었습니다.";
                        alert_state = "success";
                        alert_aftersending = "COMG/Group?GroupSEQ=" + fk_group_seq;

                        model.addAttribute("alert_title", alert_title);
                        model.addAttribute("alert_contents", alert_contents);
                        model.addAttribute("alert_state", alert_state);
                        model.addAttribute("alert_aftersending", alert_aftersending);

                        return "/alert/sweetalert";
                    } else {
                        alert_title = "과제제출 실패!";
                        alert_contents = "과제 제출이 실패하였습니다 다시 시도해주세요.";
                        alert_state = "warning";
                        alert_aftersending = "COMG/AssignmentSubmitInfo?AssignmentSEQ=" + fk_assignment_room_seq + "&GroupSEQ=" + fk_group_seq;
                        model.addAttribute("alert_title", alert_title);
                        model.addAttribute("alert_contents", alert_contents);
                        model.addAttribute("alert_state", alert_state);
                        model.addAttribute("alert_aftersending", alert_aftersending);

                    }

                }
            } else {
                log.info("FILE_MORE_INFO 테이블에 데이터 저장 실패 !");
                alert_title = "데이터 저장 실패!";
                alert_contents = "error(FILE_MORE_INFO Insert Fail)";
                alert_state = "warning";
                alert_aftersending = "COMG/AssignmentSubmitInfo?AssignmentSEQ=" + fk_assignment_room_seq + "&GroupSEQ=" + fk_group_seq;

                model.addAttribute("alert_title", alert_title);
                model.addAttribute("alert_contents", alert_contents);
                model.addAttribute("alert_state", alert_state);
                model.addAttribute("alert_aftersending", alert_aftersending);

                return "/alert/sweetalert";
            }
        } else {
            log.info("FILE_INFO 테이블에 데이터 저장 실패 !");

            alert_title = "데이터 저장 실패!";
            alert_contents = "error(FILE_INFO Insert Fail)";
            alert_state = "warning";
            alert_aftersending = "COMG/AssignmentSubmitInfo?AssignmentSEQ=" + fk_assignment_room_seq + "&GroupSEQ=" + fk_group_seq;

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }


        log.info("CImgUploadController : fileUpload 끝!");
        return "/alert/sweetalert";
    }
}

