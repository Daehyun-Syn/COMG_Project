package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CLogoutController {

    @GetMapping (value = "COMG/logout")
    public String logout(HttpSession session, ModelMap model) {
        log.info("로그아웃 시작");
        session.invalidate();

        String alert_title = "로그아웃 성공!";
        String alert_contents = "로그아웃 되었습니다.";
        String alert_state = "success";
        String alert_aftersending = "COMG/home";

        // 로그아웃 결과 전송
        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);


        return "alert/sweetalert";
    }
}
