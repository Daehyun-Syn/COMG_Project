package kopo.poly.controller;

import kopo.poly.dto.CMailDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICMailService;
import kopo.poly.service.ICPwSearchService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class CPwSearchController {

    @Resource(name = "CPwSearchService")
    private ICPwSearchService pwSearchService;

    @Resource(name = "CMailService")
    private ICMailService cMailService;


    @GetMapping(value = "COMG/PwSearch")
    public String PwSearch(){
        log.info("CPwSearchController : 비밀번호 찾기 페이지 출력");
        return "/COMGlogin/COMGpwSearch";
    }

    @ResponseBody
    @PostMapping(value = "COMG/emailCheckLogic")
    public int emailCheckLogic(HttpServletRequest request, HttpSession session) throws ParseException {

        log.info("CPwSearchController : emailCheckLogic 시작 ! ");
        int res = 0;
        int result = 0;

        try {
            String toMail = request.getParameter("id");
            log.info("웹에서 받아온 보낼 메일 주소 : " + toMail);

            // 회원중 일치하는 아이디가 있는지 조회함
            CUserDTO oDTO = null;
            oDTO = new CUserDTO();
            oDTO.setUser_id(toMail);
            result = pwSearchService.IDSearch(oDTO);

            if(result == 1){
                log.info("emailCheckLogic : 일치하는 회원 존재하여 임시 코드 발송 실행");

                String contents = RandomStringUtils.getRamdomPassword(4);
                log.info("전송될 임시 코드  : " + contents);

                //세션에 아이디 검증용 코드를 저장 (추후에 비교하여 사용하기 위함)
                session.setAttribute("contents", contents);

                CMailDTO pDTO = new CMailDTO();

                pDTO.setToMail(toMail);
                pDTO.setTitle("[COMG] 본인 인증용 코드입니다.");
                pDTO.setContents(contents+"\n입력후 임시 비밀번호를 발급받으세요.");
                log.info("SMTP로 보낼 본인인증 코드 전송로직 실행");

                res = cMailService.doSendmail(pDTO);
                if(res == 1) {
                    log.info("emailCheckLogic : 임시 코드 발송 성공 !");

                }else {
                    log.info("emailCheckLogic : 임시 코드 발송 실패 !");

                }
            }else {
                log.info("emailCheckLogic : 일치하는 회원 없음");
                res = 2;
            }

            
        } catch (Exception e) {
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();

        }
        
        log.info("CPwSearchController : emailCheckLogic 끝 ! ");
        return res;
    }

    @ResponseBody
    @PostMapping(value = "COMG/codeCheckLogic")
    public int codeCheckLogic(HttpServletRequest request, HttpSession session) throws ParseException {
        log.info("CPwSearchController : codeCheckLogic 시작 ! ");
        int res = 0;

        try{
            String code = request.getParameter("Certification_Number");
            log.info("웹에서 받아온 입력받은 코드 : "+ code);
            String Certification_Number = CmmUtil.nvl((String)session.getAttribute("contents"));
            log.info("세션에서 저장된 발송된 코드 : "+ Certification_Number);

            if(Objects.equals(Certification_Number, code)) {
                res = 1;
                log.info("입력한 코드와 보낸 코드가 일치함");
            }else {
                res = 0;
                log.info("입력한 코드와 보낸 코드가 불일치함");
            }

        }catch (Exception e){
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();
        }
        log.info("CPwSearchController : codeCheckLogic 끝 ! ");
        return res;
    }


    @PostMapping(value = "COMG/updatePassWord")
    public String updatePassWord(HttpServletRequest request, HttpSession session, ModelMap model) throws ParseException {

        log.info("CPwSearchController : updatePassWord 시작 ! ");
        int res = 0;
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        try {
            String code = request.getParameter("Certification_Number");
            log.info("웹에서 받아온 입력받은 코드 : "+ code);
            String Certification_Number = CmmUtil.nvl((String)session.getAttribute("contents"));
            log.info("세션에서 저장된 발송된 코드 : "+ Certification_Number);

            if(code.equals(Certification_Number)){
                String id = request.getParameter("id");
                log.info("웹에서 받아온 보낼 메일 주소 : " + id);

                String temporary = RandomStringUtils.getRamdomPassword(8);
                String Password = EncryptUtil.encHashSHA256(temporary);
                log.info("변경될 임시 비밀번호  : " + temporary);
                CUserDTO oDTO = new CUserDTO();
                oDTO.setUser_id(id);
                oDTO.setUser_password(Password);

                res = pwSearchService.PWupdate(oDTO);
                if (res==1) {
                    alert_title = "임시 비밀번호 발급 성공!";
                    alert_contents = "회원님의 임시 비밀번호는 "+temporary+" 입니다.";
                    alert_state = "success";
                    alert_aftersending = "COMG/login";
                    log.info("CPwSearchController : 임시 비밀번호로 비밀번호 변경 성공 ! ");
                }else if (res==0) {
                    alert_title= "임시 비밀번호 발급 실패!";
                    alert_contents= "오류로 인해 임시 비밀번호 발급이 실패하였습니다.";
                    alert_state= "error";
                    alert_aftersending = "COMG/PwSearch";
                    log.info("CPwSearchController : 임시 비밀번호로 비밀번호 변경 실패(로직오류)! ");
                }else {
                    alert_title= "임시 비밀번호 발급 실패!";
                    alert_contents= "관리자에게 문의하세요.";
                    alert_state= "warning";
                    alert_aftersending = "COMG/PwSearch";
                    log.info("CPwSearchController : 임시 비밀번호로 비밀번호 변경 실패(코드 전부 확인하기) ");
                }
            }else {
                log.info("CPwSearchController : 코드와 입력받은 값 불일치 !");
                alert_title= "임시 비밀번호 발급 실패!";
                alert_contents= "입력된 코드가 임시 코드와 불일치 합니다!";
                alert_state= "warning";
                alert_aftersending = "COMG/PwSearch";
                
            }


        } catch (Exception e) {
            log.info("비밀번호 수정 로직 실패(비밀번호 변경)" + e.toString());
            e.printStackTrace();

        }finally {
            log.info(this.getClass().getName() + ".insertUserInfo end!");
            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

        }

        log.info("CPwSearchController : emailCheckLogic 끝 ! ");
        return "/alert/sweetalert";
    }
}
