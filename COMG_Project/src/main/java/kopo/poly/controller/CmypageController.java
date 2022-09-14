package kopo.poly.controller;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICGroupService;
import kopo.poly.service.ICMypageService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class CmypageController {

    @Resource(name = "CMypageService")
    private ICMypageService cMypageService;
    @Resource(name = "CGroupService")
    private ICGroupService cGroupService;

    @GetMapping(value = "COMG/Mypage")
    public String Mypage(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
        log.info("CmypageController : 그룹 목록 출력 시작 !");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        List<CGroupDTO> rList = null;
        CFileDTO pDTO = null;
        String user_profile = "";

        // 비 로그인시 접속 안되게 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CmypageController : 로그인을 하지않고 마이페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }
        try {
            CUserDTO uDTO = null;
            uDTO = new CUserDTO();
            uDTO = (CUserDTO) session.getAttribute("suDTO");
            if (uDTO == null) {
                uDTO = new CUserDTO();
                log.info("세션에서 받아온 nDTO가 null이므로 null처리를 진행함");
            }
            // 개인적인 테스트시 널 발생하여 한번더 로그인을 했는지 걸러줌
            if (uDTO.getUser_id() == null) {
                uDTO = new CUserDTO();
                log.info("CmypageController : 로그인을 하지않고 메인페이지 접속함!");

                alert_title = "로그인이 필요합니다!";
                alert_contents = "로그인 후 접속하여 주세요.";
                alert_state = "warning";
                alert_aftersending = "COMG/login";

                model.addAttribute("alert_title", alert_title);
                model.addAttribute("alert_contents", alert_contents);
                model.addAttribute("alert_state", alert_state);
                model.addAttribute("alert_aftersending", alert_aftersending);

                return "/alert/sweetalert";

            } else if (uDTO.getUser_id() != null) {
                log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
                String user_seq = CmmUtil.nvl(uDTO.getUser_seq());
                // SEQ값을 기준으로 사용자 정보를 가져옴
                CUserDTO iDTO = null;
                iDTO = new CUserDTO();
                CUserDTO fDTO = null;
                fDTO = new CUserDTO();
                iDTO.setUser_seq(user_seq);
                fDTO = cMypageService.getUserInfo(iDTO);
                if(fDTO.getUser_name() == null){
                    log.info("CmypageController : 쿼리 오류로 값을 가져오지 못함 !");
                }else{
                    model.addAttribute("user_name", CmmUtil.nvl(fDTO.getUser_name()));
                    model.addAttribute("student_id", CmmUtil.nvl(fDTO.getStudent_id()));
                    model.addAttribute("user_id", CmmUtil.nvl(fDTO.getUser_id()));
                    String file_seq = CmmUtil.nvl(fDTO.getFile_seq());
                    pDTO = new CFileDTO();
                    pDTO.setFk_file_seq(file_seq);
                    CFileDTO zDTO = null;
                    zDTO = new CFileDTO();
                    zDTO = cMypageService.getFile(pDTO);
                    if(zDTO == null) {
                        log.info("zDTO(유저프로필) 이 null이라 메모리에 강제로 올려줌");
                        zDTO = new CFileDTO();
                    }else {
                        log.info("zDTO(유저프로필) 가져오기 성공!");
                        user_profile = CmmUtil.nvl(zDTO.getServer_file_path());
                    }
                }
            }
            String fk_user_seq = CmmUtil.nvl(uDTO.getUser_seq());
            CGroupDTO gDTO = null;
            gDTO = new CGroupDTO();
            gDTO.setFk_user_seq(fk_user_seq);
            rList = cGroupService.getGroupList(gDTO);
            if(rList == null) {
                //rList가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
                log.info("CgroupController : 그룹이 존재하지않아 강제로 메모리에 rList를 올림!");
                rList = new ArrayList<>();
            }
            else {
                log.info("CgroupController : 그룹리스트 가져오기 성공");
                // 그룹 프로필 가져오기
                for (int i = 0; i < rList.size(); i++){
                    String fk_file_seq = rList.get(i).getFile_seq();
                    CFileDTO fDTO = null;
                    fDTO = new CFileDTO();
                    CFileDTO iDTO = null;
                    iDTO = new CFileDTO();
                    fDTO.setFk_file_seq(fk_file_seq);
                    iDTO = cGroupService.getFilePath(fDTO);

                    if (iDTO == null) {
                        iDTO = new CFileDTO();
                    }else {
                        log.info("CgroupController : 파일패스 가져오기 성공");
                        rList.get(i).setGroup_profile(iDTO.getServer_file_path());
                    }
                }


            }
        } catch (Exception e) {
            log.info("그룹 리스트 가져오기 로직 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            //그룹 리스트 목록 전달하기
            model.addAttribute("rList", rList);
            model.addAttribute("user_profile", user_profile);
        }
        log.info("CgroupController : 그룹 목록 출력 끝 !");

        return "/COMGmypage/mypage";
    }

    @GetMapping(value = "COMG/Pwchange")
    public String Pwchange(ModelMap model, HttpSession session) {

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        // 비 로그인시 접속 안되게 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CmypageController : 로그인을 하지않고 마이페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }

        return "/COMGmypage/PwChange";
    }

    @GetMapping(value = "COMG/ChangeProfile")
    public String ChangeProfile(HttpServletRequest request, HttpSession session, ModelMap model) {

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        // 비 로그인시 접속 안되게 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CmypageController : 로그인을 하지않고 마이페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }

        return "/COMGmypage/ChangeProfile";
    }

    @ResponseBody
    @PostMapping(value = "COMG/DeleteUser")
    public int DeleteUser(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("CmypageController : DeleteUser 시작 ! ");
        // 성공 1, 실패 0
        int res = 0;
        try{
            CUserDTO uDTO = null;
            uDTO = new CUserDTO();
            uDTO = (CUserDTO) session.getAttribute("suDTO");
            if (uDTO == null) {
                uDTO = new CUserDTO();
                log.info("세션에서 받아온 nDTO가 null이므로 null처리를 진행함");
            }
            log.info("세션에서 가져온 아이디 : "+ uDTO.getUser_id());
            log.info("세션에서 가져온 비밀번호  : "+ uDTO.getUser_password());
            CUserDTO nDTO = null;
            nDTO = new CUserDTO();
            nDTO.setUser_id(CmmUtil.nvl(uDTO.getUser_id()));
            nDTO.setUser_password(CmmUtil.nvl(uDTO.getUser_password()));

            res = cMypageService.deleteUser(nDTO);

            if(res == 1) {

                log.info("CmypageController : 회원 탈퇴 로직 성공");
            }else {

                log.info("CmypageController : 회원 탈퇴 로직 실패");
            }

        }catch (Exception e){
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();
        }

        log.info("CmypageController : DeleteUser 끝 ! ");
        return res;
    }

    @ResponseBody
    @PostMapping(value ="COMG/changeUserInfo")
    public int changeUserInfo(HttpServletRequest request) throws Exception {

        log.info("CmypageController : changeUserInfo 시작 ! ");
        // 성공 1, 실패 0
        int res = 0;
        CUserDTO uDTO = null;
        String user_id = CmmUtil.nvl(request.getParameter("user_id"));
        String user_name = CmmUtil.nvl(request.getParameter("user_name"));
        String student_id = CmmUtil.nvl(request.getParameter("student_id"));
        log.info("JSP에서 받아온 user_id : " + user_id);
        log.info("JSP에서 받아온 user_name : " + user_name);
        log.info("JSP에서 받아온 student_id : " + student_id);
        try{

            uDTO = new CUserDTO();
            uDTO.setUser_id(user_id);
            uDTO.setUser_name(user_name);
            uDTO.setStudent_id(student_id);

            res = cMypageService.updateUser(uDTO);

            if(res == 1) {

                log.info("CmypageController : 회원 정보 수정 로직 성공");
            }else {

                log.info("CmypageController : 회원 정보 수정 실패");
            }

        }catch (Exception e){
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();
        }

        log.info("CmypageController : changeUserInfo 끝 ! ");
        return res;
    }

    @PostMapping(value = "COMG/passwordChange")
    public String passwordChange(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
        log.info("CmypageController : passwordChange 시작 !");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        int res = 0;
        CUserDTO uDTO = null;


        try{
            String origin_password = CmmUtil.nvl(request.getParameter("origin_password"));
            String password1 = CmmUtil.nvl(request.getParameter("password1"));
            String password2 = CmmUtil.nvl(request.getParameter("password2"));
            log.info("파라미터로 받아온 origin_password : " + origin_password);
            log.info("파라미터로 받아온 password1 : " + password1);
            log.info("파라미터로 받아온 password2 : " + password2);
            uDTO = new CUserDTO();
            if(Objects.equals(password1, password2)){
                CUserDTO mDTO = null;
                mDTO = new CUserDTO();
                mDTO = (CUserDTO) session.getAttribute("suDTO");

                if (uDTO == null) {
                    uDTO = new CUserDTO();
                    log.info("세션에서 받아온 nDTO가 null이므로 null처리를 진행함");
                }

                log.info("세션에서 가져온 아이디 : "+ mDTO.getUser_id());
                uDTO.setUser_id(mDTO.getUser_id());
                uDTO.setUser_password(EncryptUtil.encHashSHA256(password2));
                log.info("암호화된 비밀번호 : " +EncryptUtil.encHashSHA256(password2));
                uDTO.setPassword_check(EncryptUtil.encHashSHA256(origin_password));

                res = cMypageService.passwordChange(uDTO);

                if (res == 1) {
                    alert_title = "비밀번호 변경 성공!";
                    alert_contents = "정상적으로 비밀번호가 변경되었습니다.";
                    alert_state = "success";
                    alert_aftersending = "COMG/login";
                    log.info("CmypageController : 비밀번호 변경 성공 !");

                }else if (res == 2) {
                    alert_title= "비밀번호 변경 실패!";
                    alert_contents= "현재 비밀번호와 변경될 비밀번호가 일치합니다. 다른비밀번호를 사용해 주세요";
                    alert_state= "error";
                    alert_aftersending = "COMG/Pwchange";
                    log.info("CmypageController : 비밀번호 변경 실패 !");

                }else if (res == 3){
                    alert_title= "비밀번호 변경 실패!";
                    alert_contents= "입력받은 현재 비밀번호가 사용중인 비밀번호와 일치하지 않습니다.";;
                    alert_state= "error";
                    alert_aftersending = "COMG/Pwchange";
                    log.info("CmypageController : 비밀번호 변경 실패 !");
                }else{
                    alert_title= "비밀번호 변경 실패!";
                    alert_contents= "비밀번호 변경이 실패하였습니다. 다시 시도해 주세요";
                    alert_state= "error";
                    alert_aftersending = "COMG/Pwchange";
                    log.info("CmypageController : 비밀번호 변경 실패 !");
                }
                
            }else {
                alert_title= "비밀번호 변경 실패!";
                alert_contents= "변경할 비밀번호가 서로 일치하지 않습니다. 다시 시도해주세요.";
                alert_state= "error";
                alert_aftersending = "COMG/Pwchange";
                log.info("CmypageController : 비밀번호 변경 실패 !");
            }

        }catch (Exception e) {
            log.info("비밀번호 변경 작동 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            uDTO = null;
            //비밀번호 변경 결과 메시지 전달하기
            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);
        }
        log.info("CmypageController : passwordChange 끝 !");

        return "/alert/sweetalert";
    }
}
