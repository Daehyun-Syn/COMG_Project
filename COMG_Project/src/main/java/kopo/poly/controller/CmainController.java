package kopo.poly.controller;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICGroupService;
import kopo.poly.service.ICMypageService;
import kopo.poly.util.CmmUtil;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class CmainController {
    @Resource(name = "CGroupService")
    private ICGroupService cGroupService;

    @Resource(name = "CMypageService")
    private ICMypageService cMypageService;

    @GetMapping(value = "COMG/main")
    public String ComgMain(HttpSession session, ModelMap model) {
        log.info("CmainController : 그룹 목록 출력 시작 !");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        List<CGroupDTO> rList = null;
        String user_profile = "";
        CFileDTO pDTO = null;

        // 비 로그인시 접속 안되게 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CmainController : 로그인을 하지않고 메인페이지 접속함!");

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
                log.info("입력받은 정보와 일치하는 아이디가 없어 nDTO 널처리를 진행함");
            }
            // 개인적인 테스트시 널 발생하여 한번더 로그인을 했는지 걸러줌
            if (uDTO.getUser_id() == null) {
                uDTO = new CUserDTO();
                log.info("CmainController : 로그인을 하지않고 메인페이지 접속함!");

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
                model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));
                model.addAttribute("student_id", CmmUtil.nvl(uDTO.getStudent_id()));
                log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
            }
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
                model.addAttribute("student_id", CmmUtil.nvl(fDTO.getStudent_id()));
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
                    CFileDTO eDTO = null;
                    eDTO = new CFileDTO();
                    CFileDTO lDTO = null;
                    lDTO = new CFileDTO();
                    eDTO.setFk_file_seq(fk_file_seq);
                    lDTO = cGroupService.getFilePath(eDTO);

                    if (lDTO == null) {
                        lDTO = new CFileDTO();
                    }else {
                        log.info("CgroupController : 파일패스 가져오기 성공");
                        rList.get(i).setGroup_profile(lDTO.getServer_file_path());
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
            session.setAttribute("user_profile", user_profile);
        }
        log.info("CgroupController : 그룹 목록 출력 끝 !");
        return "/COMGmain/COMGmain";
    }
}



