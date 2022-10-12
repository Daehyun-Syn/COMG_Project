package kopo.poly.controller;

import kopo.poly.dto.CUserDTO;
import kopo.poly.service.impl.KakaoLoginService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Controller
public class KakaoLoginController {

    KakaoLoginService kakaoLoginService;
    public KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }


    @GetMapping(value = "/kakaologinPage")
    public  String userlogin(@RequestParam("code") String code, HttpSession session, HttpServletRequest request, ModelMap model) {

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        String access_Token = kakaoLoginService.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoLoginService.getUserInfo(access_Token);
        System.out.println("access_Token : " + access_Token);
        System.out.println("토큰으로 가져온 유저정보 : " + userInfo);

        CUserDTO checkKakao = null;
        CUserDTO uDTO = null;
        checkKakao = new CUserDTO();
        uDTO = new CUserDTO();


        String group_seq = CmmUtil.nvl(request.getParameter("group_seq"));

        // 클라이언트의 이메일이 존재할 때 세션에 해달 이메일과 토큰 등록
        if (userInfo.get("nickname") != null){
            session.setAttribute("userId", userInfo.get("nickname"));
            session.setAttribute("access_Token", access_Token);

            uDTO.setKakaoId((String) userInfo.get("nickname"));
            checkKakao = kakaoLoginService.getUserKakao(uDTO);
            if(checkKakao == null) {
                checkKakao = new CUserDTO();
            }
            if(checkKakao.getUser_id() == null) {
                alert_title = "연동된 계정이 없습니다";
                alert_contents = "기존계정으로 로그인 후, 마이페이지에서 계정연동을 진행해 주세요.";
                alert_state = "error";
                alert_aftersending = "COMG/login";
                log.info("KakaoLoginController : 로그인 실패 !");
            }else {
                String user_name = CmmUtil.nvl(checkKakao.getUser_name());
                session.setAttribute("user_name", user_name);
                log.info("oDTO : " + checkKakao.getUser_id());
                session.setAttribute("suDTO", checkKakao);

                alert_title = "로그인 성공!";
                alert_contents = userInfo.get("nickname")+"님 로그인에 성공하였습니다.";
                alert_state = "success";

                if (Objects.equals(group_seq, "")){
                    alert_aftersending = "COMG/main";
                } else {
                    alert_aftersending = "COMG/Joingroup?GroupSEQ="+group_seq;
                    log.info(" [else] JoinGroup 에서 받아온 GroupSEQ : "+group_seq);
                }
            }
            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

        }


        return "/alert/sweetalert";
    }

    @GetMapping(value = "/kakaoJoinPage")
    public  String userJoin(@RequestParam("code") String code, HttpSession session, HttpServletRequest request, ModelMap model) {

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        String access_Token = kakaoLoginService.getAccessToken2(code);

        HashMap<String, Object> userInfo = kakaoLoginService.getUserInfo(access_Token);
        System.out.println("access_Token : " + access_Token);
        System.out.println("토큰으로 가져온 유저정보 : " + userInfo);
        CUserDTO tDTO = null;
        tDTO = new CUserDTO();
        tDTO = (CUserDTO) session.getAttribute("suDTO");
        String userId = CmmUtil.nvl(tDTO.getUser_id());
        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        int success = 0;

        // 클라이언트의 이메일이 존재할 때 세션에 해달 이메일과 토큰 등록
        if (userInfo.get("nickname") != null){
            session.setAttribute("userId", userInfo.get("nickname"));
            session.setAttribute("access_Token", access_Token);

            uDTO.setKakaoId((String) userInfo.get("nickname"));
            uDTO.setUser_id(userId);
            success = kakaoLoginService.insertUserKakao(uDTO);


            if(success > 0){
                alert_title= "계정 연동 성공!";
                alert_contents= "로그인후 이용해주세요.";
                alert_state= "success";
                alert_aftersending = "COMG/login";
                log.info("KakaoLoginController : 게정연동 성공 !");
            }else {
                alert_title= "계정 연동 실패!";
                alert_contents= "고객센터로 문의해주세요";
                alert_state= "error";
                alert_aftersending = "COMG/login";
                log.info("KakaoLoginController : 게정연동 실패 !");
            }
            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

        }


        return "/alert/sweetalert";
    }
}
