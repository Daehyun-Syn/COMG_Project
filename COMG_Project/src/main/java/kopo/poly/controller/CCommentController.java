package kopo.poly.controller;

import kopo.poly.dto.CBoardDTO;
import kopo.poly.dto.CCommentDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.service.ICCommentService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CCommentController {

    @Resource(name = "CCommentService")
    private ICCommentService cCommentService;

    @ResponseBody
    @PostMapping(value = "COMG/CommentWrite")
    public int CommentWrite(HttpServletRequest request, HttpSession session) throws Exception{
        log.info("CCommentController : CommentWrite 시작! ");
        int res = 0;

        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");

        String comment_writer = CmmUtil.nvl(uDTO.getUser_id());
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + comment_writer);
        String fk_user_seq = CmmUtil.nvl(uDTO.getUser_seq());
        String fk_board_seq = CmmUtil.nvl(request.getParameter("fk_board_seq"));
        String fk_group_seq = CmmUtil.nvl(request.getParameter("fk_group_seq"));
        String fk_group_user_seq = CmmUtil.nvl(request.getParameter("fk_group_user_seq"));
        String comment_contents = CmmUtil.nvl(request.getParameter("comment_contents"));

        CCommentDTO cDTO = null;
        cDTO = new CCommentDTO();
        cDTO.setComment_contents(comment_contents);
        cDTO.setComment_writer(comment_writer);
        cDTO.setFk_board_seq(fk_board_seq);
        cDTO.setFk_user_seq(fk_user_seq);
        cDTO.setFk_group_seq(fk_group_seq);
        cDTO.setFk_group_user_seq(fk_group_user_seq);

        res = cCommentService.InsertCommentInfo(cDTO);
        if(res == 1){
            log.info("CCommentController : 댓글 작성 성공! ");
        }else{
            log.info("CCommentController : 댓글 작성 실패! ");
        }

        log.info("CCommentController : CommentWrite 끝! ");
        return res;
    }
    @PostMapping(value = "COMG/CommentDelete")
    public String CommentDelete(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("CommentDelete 컨트롤러 : CommentDelete 시작! ");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        int res = 0;

        String comment_seq = CmmUtil.nvl(request.getParameter("Delete_Comment_seq"));
        String group_seq = CmmUtil.nvl(request.getParameter("Delete_comment_group_seq"));
        CCommentDTO cDTO = null;
        cDTO = new CCommentDTO();
        cDTO.setComment_seq(comment_seq);
        res = cCommentService.DeleteComment(cDTO);
        if (res == 1){
            alert_title = "댓글 삭제 성공!";
            alert_contents = "댓글 삭제가 완료되었습니다.";
            alert_state = "success";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }else{
            alert_title = "댓글 삭제 실패!";
            alert_contents = "게시글 삭제가 실패하였습니다.";
            alert_state = "warning";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }

        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);

        log.info("CommentDelete 컨트롤러 : CommentDelete 시작! ");
        return "/alert/sweetalert";
    }

    @PostMapping(value = "COMG/CommentUpdateLogic")
    public String CommentUpdateLogic(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("CCommentController : CommentUpdateLogic 시작! ");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        int res = 0;

        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        CCommentDTO bDTO = null;
        bDTO = new CCommentDTO();

        String user_id = CmmUtil.nvl(uDTO.getUser_name());
        String update_comment_contents = request.getParameter("update_comment_contents");
        String group_seq = CmmUtil.nvl(request.getParameter("Update_comment_group_seq"));
        String update_Comment_seq = CmmUtil.nvl(request.getParameter("Update_Comment_seq"));

        bDTO.setComment_reg_user(user_id);
        bDTO.setComment_contents(update_comment_contents);
        bDTO.setComment_seq(update_Comment_seq);


        res = cCommentService.UpdateCommentInfo(bDTO);
        if(res == 1){
            log.info("CCommentController : 댓글 수정 완료 !");
            alert_title = "댓글 수정 성공!";
            alert_contents = "댓글 수정이 완료되었습니다.";
            alert_state = "success";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;

        }else {
            log.info("CCommentController : 댓글 수정 실패 !");
            alert_title = "댓글 수정 실패!";
            alert_contents = "댓글 수정이 실패하였습니다.";
            alert_state = "warning";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }

        log.info("CCommentController : CommentUpdateLogic 끝! ");
        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);
        return "/alert/sweetalert";
    }
}
