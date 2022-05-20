package kopo.poly.controller;

import kopo.poly.dto.CBoardDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICBoardService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CBoardController {

    @Resource(name = "CBoardService")
    private ICBoardService cBoardService;

    @GetMapping(value = "COMG/Board")
    public String BoardWrite (HttpServletRequest request, HttpSession session, ModelMap model) {
        log.info("CBoardController : BoardWrite 페이지 시작 !");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        CGroupDTO gDTO = null;
        CGroupDTO rDTO = null;
        String group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
        log.info("GroupIndex 에서 받아온 GroupSEQ : "+group_seq);

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
        }

        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));

        log.info("CBoardController : BoardWrite 페이지 끝 !");
        return "/COMGBoard/BoardWrite";
    }

    @ResponseBody
    @GetMapping(value = "COMG/BoardWriteLogic")
    public int BoardWriteLogic (HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
        log.info("CBoardController : BoardWriteLogic 시작 !");
        int res = 0;

        CBoardDTO rDTO = null;
        CUserDTO tDTO = null;
        tDTO = new CUserDTO();
        tDTO = (CUserDTO) session.getAttribute("suDTO");

        String user_id = CmmUtil.nvl(tDTO.getUser_id());
        String contents = CmmUtil.nvl(request.getParameter("board"));
        String user_seq = CmmUtil.nvl(tDTO.getUser_seq());
        String fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
        String fk_group_user_seq = CmmUtil.nvl(request.getParameter("fk_group_user_seq"));
        String seq = "0";
        log.info("contents : " + contents);
        rDTO = new CBoardDTO();
        rDTO.setBoard_contents(contents);
        rDTO.setBoard_writer(user_id);
        rDTO.setFk_user_seq(user_seq);
        rDTO.setFk_group_seq(fk_group_seq);
        rDTO.setFk_group_user_seq(fk_group_user_seq);
        rDTO.setFile_seq(seq);


        res = cBoardService.InsertBoardInfo(rDTO);
        if (res == 1) {
            log.info("게시글 작성 완료 !");
        }else {
            log.info("게시글 작성 실패 ! : 로직 에러 .");
        }

        log.info("CBoardController : BoardWriteLogic 끝 !");
        return res;
    }

    @GetMapping(value = "COMG/BoardUpdate")
    public String BoardUpdate(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{
        log.info("CBoardController : BoardUpdate 페이지 시작! ");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
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
        }

        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));
        String board_seq = CmmUtil.nvl(request.getParameter("board_seq"));
        CBoardDTO bDTO = null;
        CBoardDTO oDTO = null;
        bDTO = new CBoardDTO();
        oDTO = new CBoardDTO();

        bDTO.setBoard_seq(board_seq);
        oDTO = cBoardService.getBoardInfo(bDTO);
        if (oDTO == null){
            log.info("CBoardController : getBoardInfo 로직 실패! ");
            oDTO = new CBoardDTO();
        }
        log.info("게시글 내용 : "+oDTO.getBoard_contents());
        model.addAttribute("oDTO", oDTO);
        log.info("CBoardController : BoardUpdate 페이지 끝! ");
        return "/COMGBoard/BoardUpdate";
    }
    @ResponseBody
    @GetMapping(value = "COMG/BoardUpdateLogic")
    public int BoardUpdateLogic(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("CBoardController : BoardUpdateLogic 시작! ");
        int res = 0;

        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        CBoardDTO bDTO = null;
        bDTO = new CBoardDTO();

        String user_id = CmmUtil.nvl(uDTO.getUser_name());
        String contents = request.getParameter("board");
        String board_seq = CmmUtil.nvl(request.getParameter("board_seq"));

        bDTO.setBoard_reg_user(user_id);
        bDTO.setBoard_contents(contents);
        bDTO.setBoard_seq(board_seq);
        
        res = cBoardService.UpdateBoardInfo(bDTO);
        if(res == 1){
            log.info("CBoardController : 게시글 수정 완료 !");
        }else {
            log.info("CBoardController : 게시글 수정 실패 !");
        }
        log.info("CBoardController : BoardUpdateLogic 끝! ");
        return res;
    }
    
    @GetMapping(value = "COMG/BoardDeleteLogic")
    public String BoardDeleteLogic(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("CBoardController : BoardDeleteLogic 시작! ");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        int res = 0;

        String group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
        String board_seq = CmmUtil.nvl(request.getParameter("board_seq"));
        CBoardDTO bDTO = null;
        bDTO = new CBoardDTO();
        bDTO.setBoard_seq(board_seq);
        res = cBoardService.DeleteBoard(bDTO);
        if (res == 1){
            alert_title = "게시글 삭제 성공!";
            alert_contents = "게시글 삭제가 완료되었습니다.";
            alert_state = "success";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }else{
            alert_title = "게시글 삭제 실패!";
            alert_contents = "게시글 삭제가 실패하였습니다.";
            alert_state = "warning";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }

        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);

        log.info("CBoardController : BoardDeleteLogic 끝! ");
        return "/alert/sweetalert";
    }
}
