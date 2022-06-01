package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CindexController {


    @GetMapping(value = "COMG/home")
    public String ComgHome(HttpSession session, ModelMap model) {
        log.info("CjoinController : 회원가입 페이지 출력!");
        session.invalidate();

        return "/COMGindex/COMGhome";
    }




}
