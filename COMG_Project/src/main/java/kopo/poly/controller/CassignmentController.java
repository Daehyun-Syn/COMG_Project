package kopo.poly.controller;

import com.amazonaws.Request;
import com.amazonaws.services.mturk.model.CreateQualificationTypeResult;
import kopo.poly.dto.*;
import kopo.poly.service.ICAssignmentService;
import kopo.poly.service.ICBoardService;
import kopo.poly.service.ICGroupService;
import kopo.poly.service.ICMypageService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class CassignmentController {

    @Resource(name = "CAssignmentService")
    private ICAssignmentService cAssignmentService;

    @Resource(name = "CMypageService")
    private ICMypageService cMypageService;

    @Resource(name = "CGroupService")
    private ICGroupService cGroupService;

    @GetMapping(value = "COMG/assignment")
    public String assignment(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName()+"과제제출 현황 및 유저 리스트 조회하기 컨트롤러 시작!");
        log.info("CassignmentController : CreateAssignment 페이지 시작! ");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        String group_seq = "";
        String user_name = "";
        // 비로그인 접속 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CassignmentController : 로그인을 하지않고 메인페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        } else {
            user_name = CmmUtil.nvl((String) session.getAttribute("user_name"));
            model.addAttribute("user_name", user_name);
        }
        if (request.getParameter("group_seq") == null) {
            alert_title = "비정상적인 접근입니다!";
            alert_contents = "정상적인 경로로 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/main";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        } else {
            group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
            model.addAttribute("group_seq", group_seq);
        }
        String fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
        List<CAssignmentDTO> rList = null;

        try {
            //과제목록 보여주기
            CAssignmentDTO aDTO = null;
            aDTO = new CAssignmentDTO();
            aDTO.setFk_group_seq(fk_group_seq);

            rList = cAssignmentService.getAssignmentList(aDTO);

            if (rList == null) {
                log.info("과제 가져오는 쿼리 에러");
            } else {
                log.info("과제를 정상적으로 가져와 계속 진행");
                for (int i = 0; i < rList.size(); i++) {

                    //날짜를 받아와 비교함
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    // 현재날짜를 받아옴
                    Date now = new Date();
                    String firstDate1 = sdf.format(now);
                    // 비교하기위해 String을 date타입으로 변환함
                    Date firstDate = sdf.parse(firstDate1);
                    Date secondDate = sdf.parse(rList.get(i).getAssignment_deadline());

                    long diff = secondDate.getTime() - firstDate.getTime();

                    TimeUnit time = TimeUnit.DAYS;
                    // 오늘 날짜와 과제 마감일을 비교후 몇일이 남았는지 반환
                    long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                    log.info("마감일까지의 남은 기한은 : " + diffrence + "일");
                    rList.get(i).setDiffrence_deadline(diffrence);

                    // 과제 생성일을 원하는 포멧으로 변경함
                    Date assignment_regdate1 = sdf.parse(rList.get(i).getAssignment_regdate());
                    String assignment_regdate2 = sdf.format(assignment_regdate1);
                    log.info("assignment_regdate2" + assignment_regdate2);
                    rList.get(i).setAssignment_start_date(assignment_regdate2);
                    //파일이 있으면
                    if (!Objects.equals(rList.get(i).getFile_seq(), "0")) {
                        String Assignment_file_seq = rList.get(i).getFile_seq();
                        // SEQ 값을 기준으로 파일 패스를 가져옴
                        CFileDTO pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_file_seq);
                        CFileDTO zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 첨부 파일)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 첨부 파일) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_file_path(file_path);
                        }

                        // SEQ 값을 기준으로 과제 프로필 패스를 가져옴
                        String Assignment_profile_seq = rList.get(i).getAssignment_profile_seq();
                        log.info("Assignment_profile_seq :" + Assignment_profile_seq);
                        pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_profile_seq);
                        zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 프로필)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 프로필) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_profile_path(file_path);
                        }
                    } else {
                        // SEQ 값을 기준으로 과제 프로필 패스를 가져옴
                        String Assignment_profile_seq = rList.get(i).getAssignment_profile_seq();
                        log.info("Assignment_profile_seq :" + Assignment_profile_seq);
                        CFileDTO pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_profile_seq);
                        CFileDTO zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 프로필)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 프로필) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_profile_path(file_path);
                        }
                    }

                    String fk_assignment_room_seq1 = rList.get(i).getAssignment_room_seq();
                    CAssignmentDTO sDTO = null;
                    CAssignmentDTO mDTO = null;
                    sDTO = new CAssignmentDTO();
                    mDTO = new CAssignmentDTO();
                    sDTO.setFk_assignment_room_seq(fk_assignment_room_seq1);
                    sDTO.setFk_group_seq(fk_group_seq);
                    mDTO = cAssignmentService.getAssignmentCount(sDTO);
                    if (mDTO.getAssignment_count() == 0) {
                        log.info("과제 제출자가 0 명임");
                        int count = mDTO.getAssignment_count();
                        rList.get(i).setAssignment_count(count);
                    } else {
                        int count = mDTO.getAssignment_count();
                        CGroupDTO gDTO = null;
                        CGroupDTO wDTO = null;
                        gDTO = new CGroupDTO();
                        wDTO = new CGroupDTO();
                        gDTO.setFk_group_seq(fk_group_seq);
                        wDTO = cGroupService.getGroupUserCount(gDTO);
                        int group_count = wDTO.getGroup_user_count();
                        int avg = (count * 100 / group_count);
                        rList.get(i).setAssignment_count(avg);
                    }

                }
            }

        } catch (Exception e) {
            log.info("로직 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            model.addAttribute("rList", rList);
        }

        log.info(this.getClass().getName()+"과제제출 현황 및 유저 리스트 조회하기 컨트롤러 종료!");

        return "COMGassignment/Status";
    }


    @GetMapping(value = "COMG/assignment2")
    public String assignment2(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName()+"과제제출 현황 및 유저 리스트 조회하기 컨트롤러 시작!");
        String fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
        String fk_assignment_room_seq = CmmUtil.nvl(request.getParameter("room_seq"));

        log.info("그룹 seq : " + fk_group_seq +"\n" + "과제 seq : " + fk_assignment_room_seq);
        CAssignmentDTO qDTO = new CAssignmentDTO();

        qDTO.setFk_group_seq(fk_group_seq);
        qDTO.setFk_assignment_room_seq(fk_assignment_room_seq);
        List<CAssignmentDTO> rList = null;

        List<CAssignmentDTO> pList = cAssignmentService.AssingSendUserList(qDTO);
        log.info("최종 가져온 과제 리스트 사이즈 : " + pList.size());

        try {


            //과제목록 보여주기
            CAssignmentDTO aDTO = null;
            aDTO = new CAssignmentDTO();
            aDTO.setFk_group_seq(fk_group_seq);

            rList = cAssignmentService.getAssignmentList(aDTO);

            if (rList == null) {
                log.info("과제 가져오는 쿼리 에러");
            } else {
                log.info("과제를 정상적으로 가져와 계속 진행");
                for (int i = 0; i < rList.size(); i++) {

                    //날짜를 받아와 비교함
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    // 현재날짜를 받아옴
                    Date now = new Date();
                    String firstDate1 = sdf.format(now);
                    // 비교하기위해 String을 date타입으로 변환함
                    Date firstDate = sdf.parse(firstDate1);
                    Date secondDate = sdf.parse(rList.get(i).getAssignment_deadline());

                    long diff = secondDate.getTime() - firstDate.getTime();

                    TimeUnit time = TimeUnit.DAYS;
                    // 오늘 날짜와 과제 마감일을 비교후 몇일이 남았는지 반환
                    long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                    log.info("마감일까지의 남은 기한은 : " + diffrence + "일");
                    rList.get(i).setDiffrence_deadline(diffrence);

                    // 과제 생성일을 원하는 포멧으로 변경함
                    Date assignment_regdate1 = sdf.parse(rList.get(i).getAssignment_regdate());
                    String assignment_regdate2 = sdf.format(assignment_regdate1);
                    log.info("assignment_regdate2" + assignment_regdate2);
                    rList.get(i).setAssignment_start_date(assignment_regdate2);
                    //파일이 있으면
                    if (!Objects.equals(rList.get(i).getFile_seq(), "0")) {
                        String Assignment_file_seq = rList.get(i).getFile_seq();
                        // SEQ 값을 기준으로 파일 패스를 가져옴
                        CFileDTO pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_file_seq);
                        CFileDTO zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 첨부 파일)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 첨부 파일) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_file_path(file_path);
                        }

                        // SEQ 값을 기준으로 과제 프로필 패스를 가져옴
                        String Assignment_profile_seq = rList.get(i).getAssignment_profile_seq();
                        log.info("Assignment_profile_seq :" + Assignment_profile_seq);
                        pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_profile_seq);
                        zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 프로필)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 프로필) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_profile_path(file_path);
                        }
                    } else {
                        // SEQ 값을 기준으로 과제 프로필 패스를 가져옴
                        String Assignment_profile_seq = rList.get(i).getAssignment_profile_seq();
                        log.info("Assignment_profile_seq :" + Assignment_profile_seq);
                        CFileDTO pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_profile_seq);
                        CFileDTO zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 프로필)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 프로필) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_profile_path(file_path);
                        }
                    }

                    String fk_assignment_room_seq1 = rList.get(i).getAssignment_room_seq();
                    CAssignmentDTO sDTO = null;
                    CAssignmentDTO mDTO = null;
                    sDTO = new CAssignmentDTO();
                    mDTO = new CAssignmentDTO();
                    sDTO.setFk_assignment_room_seq(fk_assignment_room_seq1);
                    sDTO.setFk_group_seq(fk_group_seq);
                    mDTO = cAssignmentService.getAssignmentCount(sDTO);
                    if (mDTO.getAssignment_count() == 0) {
                        log.info("과제 제출자가 0 명임");
                        int count = mDTO.getAssignment_count();
                        rList.get(i).setAssignment_count(count);
                    } else {
                        int count = mDTO.getAssignment_count();
                        CGroupDTO gDTO = null;
                        CGroupDTO wDTO = null;
                        gDTO = new CGroupDTO();
                        wDTO = new CGroupDTO();
                        gDTO.setFk_group_seq(fk_group_seq);
                        wDTO = cGroupService.getGroupUserCount(gDTO);
                        int group_count = wDTO.getGroup_user_count();
                        int avg = (count * 100 / group_count);
                        rList.get(i).setAssignment_count(avg);
                    }

                }
            }

        } catch (Exception e) {
            log.info("로직 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            model.addAttribute("rList", rList);
        }

        model.addAttribute("pList", pList);
        log.info(this.getClass().getName()+"과제제출 현황 및 유저 리스트 조회하기 컨트롤러 종료!");

        return "COMGassignment/Status2";
    }
    @GetMapping(value = "COMG/CreateAssignment")
    public String CreateAssignment(HttpServletRequest request, HttpSession session, ModelMap model) {

        log.info("CassignmentController : CreateAssignment 페이지 시작! ");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        String group_seq = "";
        String user_name = "";
        // 비로그인 접속 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CassignmentController : 로그인을 하지않고 메인페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        } else {
            user_name = CmmUtil.nvl((String) session.getAttribute("user_name"));
            model.addAttribute("user_name", user_name);
        }
        if (request.getParameter("GroupSEQ") == null) {
            alert_title = "비정상적인 접근입니다!";
            alert_contents = "정상적인 경로로 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/main";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        } else {
            group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
            model.addAttribute("group_seq", group_seq);
        }

        log.info("CassignmentController : CreateAssignment 페이지 끝! ");
        return "COMGassignment/Create";
    }

    @ResponseBody
    @GetMapping(value = "COMG/CreateAssignmentLogic")
    public int CreateAssignmentLogic(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
        log.info("CassignmentController : CreateAssignmentLogic 시작 !");
        int res = 0;

        CAssignmentDTO rDTO = null;
        CUserDTO tDTO = null;
        tDTO = new CUserDTO();
        tDTO = (CUserDTO) session.getAttribute("suDTO");

        String user_id = CmmUtil.nvl(tDTO.getUser_id());
        String assignment_room_name = CmmUtil.nvl(request.getParameter("assignment_name"));
        String fk_group_seq = CmmUtil.nvl(request.getParameter("group_seq"));
        String assignment_room_contents = CmmUtil.nvl(request.getParameter("board"));
        String assignment_deadline = CmmUtil.nvl(request.getParameter("assignment_deadline"));
        String file_seq = "0";

        int[] profiles = {62, 63, 64, 65, 66, 67, 68, 69};
        Random rand = new Random();
        int int_file_seq = profiles[rand.nextInt(8)];
        String assignment_profile_seq = int_file_seq + "";

        log.info("contents : " + assignment_room_contents);
        rDTO = new CAssignmentDTO();
        rDTO.setAssignment_room_name(assignment_room_name);
        rDTO.setAssignment_room_creater(user_id);
        rDTO.setAssignment_room_contents(assignment_room_contents);
        rDTO.setAssignment_deadline(assignment_deadline);
        rDTO.setFk_group_seq(fk_group_seq);
        rDTO.setAssignment_profile_seq(assignment_profile_seq);
        rDTO.setFile_seq(file_seq);


        res = cAssignmentService.InsertAssignmentInfo(rDTO);
        if (res == 1) {
            log.info("과제 생성 완료 !");
        } else {
            log.info("과제 생성 실패 ! : 로직 에러 .");
        }

        log.info("CassignmentController : CreateAssignmentLogic 시작 !");
        return res;
    }

    @GetMapping(value = "COMG/AssignmentSubmit")
    public String Assignment(HttpServletRequest request, HttpSession session, ModelMap model) {
        log.info("CassignmentController : Assignment 페이지 시작! ");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        String group_seq = "";
        String user_name = "";
        List<CAssignmentDTO> rList = null;
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
        } else {
            user_name = CmmUtil.nvl((String) session.getAttribute("user_name"));
            model.addAttribute("user_name", user_name);
        }
        if (request.getParameter("GroupSEQ") == null) {
            alert_title = "비정상적인 접근입니다!";
            alert_contents = "정상적인 경로로 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/main";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        } else {
            group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
            model.addAttribute("group_seq", group_seq);
        }
        try {
            //과제목록 보여주기
            CAssignmentDTO aDTO = null;
            aDTO = new CAssignmentDTO();
            aDTO.setFk_group_seq(group_seq);

            rList = cAssignmentService.getAssignmentList(aDTO);

            if (rList == null) {
                log.info("과제 가져오는 쿼리 에러");
            } else {
                log.info("과제를 정상적으로 가져와 계속 진행");
                for (int i = 0; i < rList.size(); i++) {


                    //날짜를 받아와 비교함
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    // 현재날짜를 받아옴
                    Date now = new Date();
                    String firstDate1 = sdf.format(now);
                    // 비교하기위해 String을 date타입으로 변환함
                    Date firstDate = sdf.parse(firstDate1);
                    Date secondDate = sdf.parse(rList.get(i).getAssignment_deadline());

                    long diff = secondDate.getTime() - firstDate.getTime();

                    TimeUnit time = TimeUnit.DAYS;
                    // 오늘 날짜와 과제 마감일을 비교후 몇일이 남았는지 반환
                    long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                    log.info("마감일까지의 남은 기한은 : " + diffrence + "일");
                    rList.get(i).setDiffrence_deadline(diffrence);

                    // 과제 생성일을 원하는 포멧으로 변경함
                    Date assignment_regdate1 = sdf.parse(rList.get(i).getAssignment_regdate());
                    String assignment_regdate2 = sdf.format(assignment_regdate1);
                    log.info("assignment_regdate2" + assignment_regdate2);
                    rList.get(i).setAssignment_start_date(assignment_regdate2);
                    //파일이 있으면
                    if (!Objects.equals(rList.get(i).getFile_seq(), "0")) {
                        String Assignment_file_seq = rList.get(i).getFile_seq();
                        // SEQ 값을 기준으로 파일 패스를 가져옴
                        CFileDTO pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_file_seq);
                        CFileDTO zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 첨부 파일)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 첨부 파일) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_file_path(file_path);
                        }

                        // SEQ 값을 기준으로 과제 프로필 패스를 가져옴
                        String Assignment_profile_seq = rList.get(i).getAssignment_profile_seq();
                        log.info("Assignment_profile_seq :" + Assignment_profile_seq);
                        pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_profile_seq);
                        zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 프로필)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 프로필) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_profile_path(file_path);
                        }
                    } else {
                        // SEQ 값을 기준으로 과제 프로필 패스를 가져옴
                        String Assignment_profile_seq = rList.get(i).getAssignment_profile_seq();
                        log.info("Assignment_profile_seq :" + Assignment_profile_seq);
                        CFileDTO pDTO = null;
                        pDTO = new CFileDTO();
                        pDTO.setFk_file_seq(Assignment_profile_seq);
                        CFileDTO zDTO = null;
                        zDTO = new CFileDTO();
                        zDTO = cMypageService.getFile(pDTO);
                        if (zDTO == null) {
                            log.info("zDTO(과제 프로필)이 null이라 메모리에 강제로 올려줌");
                            zDTO = new CFileDTO();
                        } else {
                            log.info("zDTO(과제 프로필) 가져오기 성공!");
                            String file_path = CmmUtil.nvl(zDTO.getServer_file_path());
                            rList.get(i).setAssignment_profile_path(file_path);
                        }
                    }

                    String fk_as_group_seq = group_seq;
                    String fk_assignment_room_seq = rList.get(i).getAssignment_room_seq();

                    CUserDTO tDTO = null;
                    tDTO = new CUserDTO();
                    tDTO = (CUserDTO) session.getAttribute("suDTO");
                    String user_seq = tDTO.getUser_seq();

                    CAssignmentDTO sDTO = null;
                    CAssignmentDTO mDTO = null;
                    sDTO = new CAssignmentDTO();
                    mDTO = new CAssignmentDTO();

                    CAssignmentDTO vDTO = null;
                    vDTO = new CAssignmentDTO();

                    sDTO.setFk_assignment_room_seq(fk_assignment_room_seq);
                    sDTO.setFk_group_seq(fk_as_group_seq);
                    sDTO.setFk_user_seq(user_seq);
                    vDTO = cAssignmentService.getFeedBack(sDTO);
                    if(vDTO != null){
                        rList.get(i).setAssignment_feedback(vDTO.getAssignment_feedback());
                    }
                    mDTO = cAssignmentService.getAssignmentCount(sDTO);
                    if (mDTO.getAssignment_count() == 0) {
                        log.info("과제 제출자가 0 명임");
                        int count = mDTO.getAssignment_count();
                        rList.get(i).setAssignment_count(count);
                    } else {
                        int count = mDTO.getAssignment_count();
                        CGroupDTO gDTO = null;
                        CGroupDTO wDTO = null;
                        gDTO = new CGroupDTO();
                        wDTO = new CGroupDTO();
                        gDTO.setFk_group_seq(fk_as_group_seq);
                        wDTO = cGroupService.getGroupUserCount(gDTO);
                        int group_count = wDTO.getGroup_user_count();
                        int avg = (count * 100 / group_count);
                        rList.get(i).setAssignment_count(avg);
                    }

                }
            }

        } catch (Exception e) {
            log.info("로직 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            model.addAttribute("rList", rList);
        }
        log.info("CassignmentController : Assignment 페이지 끝! ");

        return "COMGassignment/Submit";
    }

    @GetMapping(value = "COMG/AssignmentSubmitInfo")
    public String AssignmentSubmitInfo(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
        log.info("CAssignmentController : AssignmentSubmitInfo 페이지 시작! ");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        String group_seq = "";

        String page_group_seq = request.getParameter("GroupSEQ");
        log.info("jsp에서 가져온 그룹 seq : " + page_group_seq);

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
        if (request.getParameter("GroupSEQ") == null) {
            alert_title = "비정상적인 접근입니다!";
            alert_contents = "정상적인 경로로 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/main";

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        } else {
            group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
            model.addAttribute("group_seq", group_seq);
        }
        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));
        String assignment_room_seq = CmmUtil.nvl(request.getParameter("AssignmentSEQ"));
        model.addAttribute("assignment_room_seq", assignment_room_seq);
        CAssignmentDTO bDTO = null;
        CAssignmentDTO oDTO = null;
        bDTO = new CAssignmentDTO();
        oDTO = new CAssignmentDTO();

        bDTO.setAssignment_room_seq(assignment_room_seq);
        oDTO = cAssignmentService.getAssignmentInfo(bDTO);
        if (oDTO == null) {
            log.info("CAssignmentController : getAssignmentInfo 로직 실패! ");
            oDTO = new CAssignmentDTO();
        } else {
            log.info("CAssignmentController : getAssignmentInfo 로직 성공! ");
            if (!Objects.equals(oDTO.getFile_seq(), "0")) {
                //파일이 있으면
                String assignment_file_seq = oDTO.getFile_seq();
                // SEQ 값을 기준으로 파일 패스를 가져옴
                CFileDTO pDTO = null;
                pDTO = new CFileDTO();
                pDTO.setFk_file_seq(assignment_file_seq);
                CFileDTO zDTO = null;
                zDTO = new CFileDTO();
                zDTO = cMypageService.getFile(pDTO);
                if (zDTO == null) {
                    log.info("zDTO(과제 첨부 파일)이 null이라 메모리에 강제로 올려줌");
                    zDTO = new CFileDTO();
                } else {
                    log.info("zDTO(과제 첨부 파일) 가져오기 성공!");
                    String server_file_name = CmmUtil.nvl(zDTO.getServer_file_name());
                    String origin_file_name = CmmUtil.nvl(zDTO.getOrigin_file_name());
                    oDTO.setAssignment_file_name(origin_file_name);
                    String file_path = server_file_name + "/" + origin_file_name;
                    oDTO.setAssignment_file_path(file_path);
                }
            }
        }
        log.info("과제 내용 : " + oDTO.getAssignment_contents());
        model.addAttribute("oDTO", oDTO);
        log.info("CAssignmentController : AssignmentSubmitInfo 페이지 끝! ");


        return "COMGassignment/AssignmentSending";
    }

    @ResponseBody
    @PostMapping(value = "COMG/CrateFeedBack")
    public int CrateFeedBack(HttpServletRequest request, HttpSession session, @RequestParam Map<String, String> map)throws Exception{
        int res = 0;

        String feedback = map.get("feedback");
        String assignment_seq = map.get("homework_seq");
        log.info("feedback : " + feedback + " " + "assignment_seq" + assignment_seq);
        CAssignmentDTO pDTO = new CAssignmentDTO();
        pDTO.setAssignment_feedback(feedback);
        pDTO.setAssignment_seq(assignment_seq);

        res = cAssignmentService.updateFeedback(pDTO);
        log.info("최종 저장 및 카카오 전송 피드백 결과는 ? " + res);

        return res;
    }

}