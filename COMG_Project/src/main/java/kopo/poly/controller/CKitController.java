package kopo.poly.controller;

import kopo.poly.dto.CUserDTO;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CKitController {


    @GetMapping(value = "COMG/COVID_KIT")
    public String COVID_KIT(HttpSession session, ModelMap model, HttpServletRequest request) {
        log.info("CKitController : COVID_KIT 페이지 시작! ");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        String group_seq = "";
        String user_name = "";
        // 비로그인 접속 방지
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
        }else{
            user_name = CmmUtil.nvl((String) session.getAttribute("user_name"));
            model.addAttribute("user_name",user_name);
        }
        if(request.getParameter("GroupSEQ") == null){
            alert_title = "비정상적인 접근입니다!";
            alert_contents = "정상적인 경로로 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/main";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }else {
           group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
           model.addAttribute("group_seq", group_seq);
        }

        log.info("CKitController : COVID_KIT 페이지 끝! ");
        return "/COVID/KitSend";
    }
}
