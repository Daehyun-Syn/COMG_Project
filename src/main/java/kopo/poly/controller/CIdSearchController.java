package kopo.poly.controller;

import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICIdSearchService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CIdSearchController {

    @Resource(name = "CIdSearchService")
    private ICIdSearchService cIdSearchService;


    @GetMapping(value = "COMG/IdSearch")
    public String IdSearch(){
    log.info("CIdSearchController : 아이디 찾기 페이지 출력!");
        return "/COMGlogin/COMGidSearch";
    }

    @PostMapping(value = "COMG/IDsearchLogic")
    public String IDsearchLogic(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
        log.info("CIdSearchController : IDsearchLogic 시작!");
        int res = 0;
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        CUserDTO uDTO = null;
        CUserDTO nDTO = null;
        try {
            String user_name = CmmUtil.nvl(request.getParameter("name"));
            String student_id = CmmUtil.nvl(request.getParameter("student_id"));
            log.info("웹에서 받아온 user_name : " + user_name);
            log.info("웹에서 받아온 student_id : " + student_id);

            uDTO = new CUserDTO();
            nDTO = new CUserDTO();
            uDTO.setUser_name(user_name);
            uDTO.setStudent_id(student_id);
            nDTO = cIdSearchService.IDSearch(uDTO);
            if(nDTO == null) {
                nDTO = new CUserDTO();
                log.info("입력받은 정보와 일치하는 아이디가 없어 nDTO를 널처리함");
            }
            String user_id = CmmUtil.nvl(nDTO.getUser_id());
            if (nDTO.getUser_id() != null) {
                alert_title = "아이디 찾기 성공!";
                alert_contents = "회원님의 아이디는 "+ user_id +" 입니다.";
                alert_state = "success";
                alert_aftersending = "COMG/login";
            }else if (nDTO.getUser_id() == null) {
                alert_title= "아이디 찾기 실패!";
                alert_contents= "입력하신 정보와 일치하는 아이디가 없습니다.";
                alert_state= "error";
                alert_aftersending = "COMG/IdSearch";
            }
        }catch (Exception e) {
            log.info("아이디 찾기 로직 실패" + e.toString());
            e.printStackTrace();
        }finally {
            log.info(this.getClass().getName() + ".IDsearchLogic end!");
            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

        }

        log.info("CIdSearchController : IDsearchLogic 끝!");
        return "/alert/sweetalert";
    }
}
