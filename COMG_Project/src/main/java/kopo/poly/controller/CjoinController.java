package kopo.poly.controller;

import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICJoinService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Slf4j
@Controller
public class CjoinController {


    @Resource(name = "CjoinService")
    private ICJoinService cjoinService;

    @GetMapping(value="COMG/join")
    public String ComgJoin(HttpSession session, ModelMap model) {
        log.info("CjoinController : 회원가입 페이지 출력!");
        session.invalidate();

        return "/COMGjoin/COMGjoin";
    }

    @PostMapping(value = "COMG/joinLogic")
    public String join(HttpServletRequest request, ModelMap model) throws Exception {
        
        log.info("CjoinController : join(회원가입 로직) 시작 !");
        
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        CUserDTO uDTO = null;

        try {
        String user_id = CmmUtil.nvl(request.getParameter("id"));
        String user_pwd = CmmUtil.nvl(request.getParameter("password2"));
        String user_name = CmmUtil.nvl(request.getParameter("name"));
        String student_id = CmmUtil.nvl(request.getParameter("student_id"));

        log.info("user_id(사용자 아이디) : " + user_id);
        log.info("user_pwd(사용자 비밀번호)  : " + user_pwd );
        log.info("user_name(사용자 이름) : " + user_name);
        log.info("student_id(학번) : " + student_id);
        // 프로필 사진 랜덤 배치를 위해 저장된 프로필 사진중 임의의 값을 뽑아온다
        int[] profiles = {2,3,4,5,6,7,8,9,10};
        Random rand = new Random();
        int int_file_seq = profiles[rand.nextInt(9)];
        String file_seq = int_file_seq+"";

        uDTO = new CUserDTO();

        uDTO.setUser_id(user_id);
        uDTO.setUser_password(EncryptUtil.encHashSHA256(user_pwd));
        uDTO.setUser_name(user_name);
        uDTO.setStudent_id(student_id);
        uDTO.setFile_seq(file_seq);
        int res = cjoinService.InsertUserInfo(uDTO);

        if (res==1) {
            alert_title = "회원가입 성공!";
            alert_contents = "회원가입에 성공하였습니다.";
            alert_state = "success";
            alert_aftersending = "COMG/login";
        }else if (res==2) {
            alert_title= "회원가입 실패!";
            alert_contents= "이미 가입된 아이디 입니다.";
            alert_state= "error";
            alert_aftersending = "COMG/join";
        }else {
            alert_title= "회원가입 실패!";
            alert_contents= "오류로 인해 회원가입이 실패하였습니다.";
            alert_state= "warning";
            alert_aftersending = "COMG/join";
        }

        }catch(Exception e) {
        //저장이 실패되면 사용자에게 보여줄 메시지
        log.info("회원가입 로직 실패 :" + e.toString());
        log.info(e.toString());
        e.printStackTrace();

        }finally {
        log.info(this.getClass().getName() + ".insertUserInfo end!");
        //회원가입 여부 결과 메시지 전달하기
        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);

        }

        log.info("CjoinController : join(회원가입 로직) 끝 !");
        return "/alert/sweetalert";
    }


    // 아작스 사용한 아이디 중복확인 로직
    @ResponseBody
    @PostMapping(value = "COMG/idcheck")
    public int ID_Check(HttpServletRequest request) throws ParseException {

        int res = 0;

        log.info("CjoinController : ID_Check(아작스 아이디 중복검사) 시작 !");
        
        try {
            //클라이언트가 보낸 ID값
            String ID = CmmUtil.nvl(request.getParameter("id"));
            log.info("ajax로 받아온 id값 : " + ID);

            CUserDTO uDTO = null;
            uDTO = new CUserDTO();

            uDTO.setUser_id(ID);
            res = cjoinService.IdCheckForAjax(uDTO);
            log.info("중복 검사로 받아온 res값 : " + res);

            if(res == 1) {//결과 값이 있으면 아이디 존재
                log.info("중복 검사 결과 : 아이디 중복 ");
            } else {		//없으면 아이디 존재 X
                log.info("중복 검사 결과 : 아이디 중복되지 않음 ");
            }
        } catch (Exception e) {
            log.info("ajax 조회가 실패하였습니다" + e.toString());
            e.printStackTrace();
        }

        log.info("CjoinController : ID_Check(아작스 아이디 중복검사) 끝 !");
        return res;
    }
}
