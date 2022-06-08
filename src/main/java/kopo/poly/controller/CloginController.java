package kopo.poly.controller;

import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICLoginService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sun.java2d.cmm.kcms.CMM;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CloginController {


    @Resource(name = "CloginService")
    private ICLoginService cloginService;

    @GetMapping(value = "COMG/login")
    public String ComgLogin(HttpSession session, ModelMap model){
        log.info("CloginController : ComgLogin 시작(로그인페이지 진입) !");
        session.invalidate();


        log.info("CloginController : ComgLogin 끝!");
        return "/COMGlogin/COMGlogin";
    }

    @PostMapping(value = "COMG/loginLogic")
    public String loginLogic(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) throws Exception {

        log.info("CloginController : loginLogic 시작 !");
        
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        int res = 0;
        CUserDTO uDTO = null;
        CUserDTO mDTO = null;

        try{
            String id = CmmUtil.nvl(request.getParameter("id"));
            String pwd = CmmUtil.nvl(request.getParameter("pwd"));
            log.info("파라미터로 받아온 user_id : " + id);
            log.info("파라미터로 받아온 user_pwd : " + pwd);
            mDTO = new CUserDTO();
            uDTO = new CUserDTO();

            uDTO.setUser_id(id);
            uDTO.setUser_pwd(EncryptUtil.encHashSHA256(pwd));
            log.info("암호화된 비밀번호 : " +EncryptUtil.encHashSHA256(pwd));

            res = cloginService.Logincheck(uDTO);


            if (res == 1) {
                // 사용자의 정보를 받아와 DTO에 담아둔다(추후 세션에 넣어 사용할수 있기 때문에)
                mDTO.setUser_id(id);
                mDTO.setUser_pwd(EncryptUtil.encHashSHA256(pwd));
                CUserDTO oDTO = cloginService.SelectUserInfo(mDTO);
                alert_title = "로그인 성공!";
                alert_contents = oDTO.getUser_name()+"님 로그인에 성공하였습니다.";
                alert_state = "success";
                alert_aftersending = "COMG/main";
                String user_name = CmmUtil.nvl(oDTO.getUser_name());
                session.setAttribute("user_name", user_name);
                session.setAttribute("oDTO", oDTO);
                log.info("CloginController : 로그인 성공 !");

            }else if (res == 0) {
                alert_title= "로그인 실패!";
                alert_contents= "아이디와 비밀번호를 확인해주세요.";
                alert_state= "error";
                alert_aftersending = "COMG/login";
                log.info("CloginController : 로그인 실패 !");

            }else {
                alert_title= "로그인 실패!";
                alert_contents= "오류로 인해 로그인에 실패하였습니다.";
                alert_state= "warning";
                alert_aftersending = "COMG/login";
                log.info("CloginController : 로그인 실패(시스템 에러) !");
            }

        }catch (Exception e) {
            log.info("로그인 로직 작동 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            uDTO = null;
            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);
        }
        log.info("CloginController : loginLogic 끝 !");
        return "/alert/sweetalert";
    }



}
