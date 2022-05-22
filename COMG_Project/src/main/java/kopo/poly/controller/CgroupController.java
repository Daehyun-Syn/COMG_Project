package kopo.poly.controller;

import kopo.poly.dto.*;
import kopo.poly.service.*;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class CgroupController {

    @Resource(name = "CGroupService")
    private ICGroupService cGroupService;

    @Resource(name = "CMypageService")
    private ICMypageService cMypageService;

    @Resource(name = "CBoardService")
    private ICBoardService cBoardService;

    @Resource(name = "CCommentService")
    private ICCommentService cCommentService;

    @Resource(name = "CAssignmentService")
    private ICAssignmentService cAssignmentService;

    @GetMapping(value = "COMG/Group")
    public String ComgIndex(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception{
        log.info("CgroupController : ComgIndex 그룹 페이지 출력 !");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        String user_profile = "";
        CGroupDTO gDTO = null;
        CGroupDTO rDTO = null;

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
        try {
            CUserDTO uDTO = null;
            uDTO = new CUserDTO();
            uDTO = (CUserDTO) session.getAttribute("suDTO");

            if (uDTO.getUser_id() == null) {
                uDTO = new CUserDTO();
                log.info("CgroupController : 로그인을 하지않고 그룹페이지 접속함!");

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

            if (uDTO.getUser_id() != null) {
                log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
                String user_seq = CmmUtil.nvl(uDTO.getUser_seq());
                // SEQ값을 기준으로 사용자 정보를 가져옴
                CUserDTO sDTO = null;
                sDTO = new CUserDTO();
                CUserDTO eDTO = null;
                eDTO = new CUserDTO();
                CFileDTO pDTO = null;
                sDTO.setUser_seq(user_seq);
                eDTO = cMypageService.getUserInfo(sDTO);

                if (eDTO.getUser_name() == null) {
                    log.info("CgroupController : 쿼리 오류로 값을 가져오지 못함 !");
                } else {
                    model.addAttribute("user_name", CmmUtil.nvl(eDTO.getUser_name()));
                    model.addAttribute("user_id", CmmUtil.nvl(eDTO.getUser_id()));
                    log.info(eDTO.getUser_id());
                    // 유저프로필 가져오기
                    String file_seq = CmmUtil.nvl(eDTO.getFile_seq());
                    pDTO = new CFileDTO();
                    pDTO.setFk_file_seq(file_seq);
                    CFileDTO zDTO = null;
                    zDTO = new CFileDTO();
                    zDTO = cMypageService.getFile(pDTO);
                    if (zDTO == null) {
                        log.info("zDTO(유저프로필) 이 null이라 메모리에 강제로 올려줌");
                        zDTO = new CFileDTO();
                    } else {
                        log.info("zDTO(유저프로필) 가져오기 성공!");
                        user_profile = CmmUtil.nvl(zDTO.getServer_file_path());
                        model.addAttribute("user_profile", user_profile);
                    }
                }

                String group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
                log.info("파라미터로 받아온 그룹seq값 : " + group_seq);
                // jsp에서 받아온 Seq값으로 게시글 가져오기
                CBoardDTO bDTO = null;
                bDTO = new CBoardDTO();
                bDTO.setFk_group_seq(group_seq);
                List<CBoardDTO> rList = null;
                rList = cBoardService.getBoardList(bDTO);
                if (rList == null) {
                    log.info("게시글 가져오는 쿼리 에러");
                } else {
                    log.info("게시글 정상적으로 가져와 계속 진행");

                    // 작성자 이름 + 사진 가져오기
                    for (int i = 0; i < rList.size(); i++) {
                        // 게시판의 파일이 있으면
                        if (!Objects.equals(rList.get(i).getFile_seq(), "0")) {
                            String board_file_seq = rList.get(i).getFile_seq();
                            // SEQ 값을 기준으로 파일 패스를 가져옴
                            pDTO = new CFileDTO();
                            pDTO.setFk_file_seq(board_file_seq);
                            CFileDTO zDTO = null;
                            zDTO = new CFileDTO();
                            zDTO = cMypageService.getFile(pDTO);
                            if (zDTO == null) {
                                log.info("zDTO(게시판 첨부 파일)이 null이라 메모리에 강제로 올려줌");
                                zDTO = new CFileDTO();
                            } else {
                                log.info("zDTO(게시판 첨부 파일) 가져오기 성공!");
                                String server_file_name = CmmUtil.nvl(zDTO.getServer_file_name());
                                String origin_file_name = CmmUtil.nvl(zDTO.getOrigin_file_name());
                                rList.get(i).setBoard_file_name(origin_file_name);
                                String file_path = server_file_name+"/"+origin_file_name;
                                rList.get(i).setBoard_file_path(file_path);
                            }
                            String fk_user_seq = rList.get(i).getFk_user_seq();
                            // SEQ값을 기준으로 사용자 정보를 가져옴
                            sDTO = null;
                            sDTO = new CUserDTO();
                            eDTO = null;
                            eDTO = new CUserDTO();
                            pDTO = null;
                            sDTO.setUser_seq(fk_user_seq);
                            eDTO = cMypageService.getUserInfo(sDTO);

                            if (eDTO.getUser_name() == null) {
                                log.info("CgroupController : 쿼리 오류로 값을 가져오지 못함 !");
                            } else {
                                rList.get(i).setWriter_name(CmmUtil.nvl(eDTO.getUser_name()));
                                String file_seq = CmmUtil.nvl(eDTO.getFile_seq());
                                pDTO = new CFileDTO();
                                pDTO.setFk_file_seq(file_seq);
                                zDTO = null;
                                zDTO = new CFileDTO();
                                zDTO = cMypageService.getFile(pDTO);
                                if (zDTO == null) {
                                    log.info("zDTO(유저프로필) 이 null이라 메모리에 강제로 올려줌");
                                    zDTO = new CFileDTO();
                                } else {
                                    log.info("zDTO(유저프로필) 가져오기 성공!");
                                    user_profile = CmmUtil.nvl(zDTO.getServer_file_path());
                                    rList.get(i).setBoard_writer_profile(user_profile);
                                }
                            }

                            //댓글정보 가져오기
                            CCommentDTO cDTO = null;
                            cDTO = new CCommentDTO();
                            cDTO.setFk_board_seq(rList.get(i).getBoard_seq());
                            log.info("게시판 seq 번호 : "+rList.get(i).getBoard_seq());

                            List<CCommentDTO> cList = null;
                            cList = cCommentService.getCommentList(cDTO);
                            if (cList == null) {
                                log.info("댓글 가져오는 쿼리 에러");
                            } else {
                                log.info("댓글 정상적으로 가져와 계속 진행");
                                for(int a = 0; a < cList.size(); a++){
                                    String comment_user_seq = cList.get(a).getFk_user_seq();
                                    // SEQ값을 기준으로 사용자 정보를 가져옴
                                    sDTO = null;
                                    sDTO = new CUserDTO();
                                    eDTO = null;
                                    eDTO = new CUserDTO();
                                    pDTO = null;
                                    sDTO.setUser_seq(comment_user_seq);
                                    eDTO = cMypageService.getUserInfo(sDTO);

                                    if (eDTO.getUser_name() == null) {
                                        log.info("CgroupController : 쿼리 오류로 값을 가져오지 못함 !");
                                    } else {
                                        cList.get(a).setComment_writer_name(CmmUtil.nvl(eDTO.getUser_name()));
                                        String file_seq = CmmUtil.nvl(eDTO.getFile_seq());
                                        pDTO = new CFileDTO();
                                        pDTO.setFk_file_seq(file_seq);
                                        zDTO = null;
                                        zDTO = new CFileDTO();
                                        zDTO = cMypageService.getFile(pDTO);
                                        if (zDTO == null) {
                                            log.info("zDTO(유저프로필) 이 null이라 메모리에 강제로 올려줌");
                                            zDTO = new CFileDTO();
                                        } else {
                                            log.info("zDTO(유저프로필) 가져오기 성공!");
                                            user_profile = CmmUtil.nvl(zDTO.getServer_file_path());
                                            cList.get(a).setComment_writer_profile(user_profile);
                                        }
                                    }
                                }
                                rList.get(i).setReviews(cList);
                            }
                        } else {
                            // 파일이 있으면
                            String fk_user_seq = rList.get(i).getFk_user_seq();
                            // SEQ값을 기준으로 사용자 정보를 가져옴
                            sDTO = null;
                            sDTO = new CUserDTO();
                            eDTO = null;
                            eDTO = new CUserDTO();
                            pDTO = null;
                            sDTO.setUser_seq(fk_user_seq);
                            eDTO = cMypageService.getUserInfo(sDTO);

                            if (eDTO.getUser_name() == null) {
                                log.info("CgroupController : 쿼리 오류로 값을 가져오지 못함 !");
                            } else {
                                rList.get(i).setWriter_name(CmmUtil.nvl(eDTO.getUser_name()));
                                String file_seq = CmmUtil.nvl(eDTO.getFile_seq());
                                pDTO = new CFileDTO();
                                pDTO.setFk_file_seq(file_seq);
                                CFileDTO zDTO = null;
                                zDTO = new CFileDTO();
                                zDTO = cMypageService.getFile(pDTO);
                                if (zDTO == null) {
                                    log.info("zDTO(유저프로필) 이 null이라 메모리에 강제로 올려줌");
                                    zDTO = new CFileDTO();
                                } else {
                                    log.info("zDTO(유저프로필) 가져오기 성공!");
                                    user_profile = CmmUtil.nvl(zDTO.getServer_file_path());
                                    rList.get(i).setBoard_writer_profile(user_profile);

                                    //댓글정보 가져오기
                                    CCommentDTO cDTO = null;
                                    cDTO = new CCommentDTO();
                                    cDTO.setFk_board_seq(rList.get(i).getBoard_seq());
                                    log.info("게시판 seq 번호 : "+rList.get(i).getBoard_seq());

                                    List<CCommentDTO> cList = null;
                                    cList = cCommentService.getCommentList(cDTO);
                                    if (cList == null) {
                                        log.info("댓글 가져오는 쿼리 에러");
                                    } else {
                                        log.info("댓글 정상적으로 가져와 계속 진행");
                                        for(int a = 0; a < cList.size(); a++){
                                            String comment_user_seq = cList.get(a).getFk_user_seq();
                                            // SEQ값을 기준으로 사용자 정보를 가져옴
                                            sDTO = null;
                                            sDTO = new CUserDTO();
                                            eDTO = null;
                                            eDTO = new CUserDTO();
                                            pDTO = null;
                                            sDTO.setUser_seq(comment_user_seq);
                                            eDTO = cMypageService.getUserInfo(sDTO);

                                            if (eDTO.getUser_name() == null) {
                                                log.info("CgroupController : 쿼리 오류로 값을 가져오지 못함 !");
                                            } else {
                                                cList.get(a).setComment_writer_name(CmmUtil.nvl(eDTO.getUser_name()));
                                                String user_file_seq = CmmUtil.nvl(eDTO.getFile_seq());
                                                pDTO = new CFileDTO();
                                                pDTO.setFk_file_seq(user_file_seq);
                                                zDTO = null;
                                                zDTO = new CFileDTO();
                                                zDTO = cMypageService.getFile(pDTO);
                                                if (zDTO == null) {
                                                    log.info("zDTO(유저프로필) 이 null이라 메모리에 강제로 올려줌");
                                                    zDTO = new CFileDTO();
                                                } else {
                                                    log.info("zDTO(유저프로필) 가져오기 성공!");
                                                    user_profile = CmmUtil.nvl(zDTO.getServer_file_path());
                                                    cList.get(a).setComment_writer_profile(user_profile);
                                                }
                                            }
                                        }
                                        rList.get(i).setReviews(cList);
                                    }
                                  }
                                }
                            }


                    }
                    model.addAttribute("rList", rList);
                    gDTO = new CGroupDTO();
                    gDTO.setGroup_seq(group_seq);
                    gDTO.setFk_user_seq(CmmUtil.nvl(eDTO.getUser_seq()));
                    model.addAttribute("group_seq", group_seq);

                    // jsp에서 받아온 Seq값으로 그룹 정보 가져오기
                    rDTO = new CGroupDTO();
                    rDTO = cGroupService.SelectGroupUserInfo(gDTO);
                    if (rDTO == null) {
                        log.info("CgroupController : SelectGroupUserInfo 실패!");
                        rDTO = new CGroupDTO();
                    } else {
                        log.info("CgroupController : SelectGroupUserInfo 성공!");
                        String fk_group_user_seq = CmmUtil.nvl(rDTO.getGroup_user_seq());
                        log.info("fk_group_user_seq : " + fk_group_user_seq);
                        model.addAttribute("fk_group_user_seq", fk_group_user_seq);
                    }
                    rDTO = cGroupService.getGroupInfo(gDTO);
                    if (rDTO.getGroup_name() == null) {
                        log.info("rDTO가 null이므로 강제로 메모리에 올림 ");
                        rDTO = new CGroupDTO();
                    } else {
                        log.info("rDTO가 null이 아니므로 로직 계속 진행함");
                        model.addAttribute("rDTO", rDTO);

                        String file_seq = CmmUtil.nvl(rDTO.getFile_seq());
                        CFileDTO fDTO = null;
                        CFileDTO iDTO = null;
                        fDTO = new CFileDTO();
                        iDTO = new CFileDTO();
                        fDTO.setFk_file_seq(file_seq);
                        iDTO = cGroupService.getFilePath(fDTO);
                        if (iDTO.getServer_file_path() == null) {
                            log.info("CgroupController : 그룹 프로필 사진 가져오기 실패!");
                        } else {
                            log.info("CgroupController : 그룹 프로필 사진 가져오기 성공!");
                            String file_path = CmmUtil.nvl(iDTO.getServer_file_path());
                            model.addAttribute("file_path", file_path);
                        }


                    }
                    String fk_group_seq = rDTO.getGroup_seq();
                    String fk_user_seq = uDTO.getUser_seq();
                    CGroupDTO jDTO = null;
                    jDTO = new CGroupDTO();
                    CGroupDTO nDTO = null;
                    nDTO = new CGroupDTO();

                    jDTO.setFk_group_seq(fk_group_seq);
                    jDTO.setFk_user_seq(fk_user_seq);
                    nDTO = cGroupService.SelectJoinGroup(jDTO);
                    if (nDTO.getGroup_seq() == null) {
                        log.info("CgroupController : 그룹 정보 조인해서 가져오기 실패!");

                        alert_title = "그룹 가입이 필요합니다!";
                        alert_contents = "그룹 가입 후 접속하여 주세요.";
                        alert_state = "warning";
                        alert_aftersending = "COMG/Joingroup?GroupSEQ="+rDTO.getGroup_seq();

                        model.addAttribute("alert_title", alert_title);
                        model.addAttribute("alert_contents", alert_contents);
                        model.addAttribute("alert_state", alert_state);
                        model.addAttribute("alert_aftersending", alert_aftersending);

                        return "/alert/sweetalert";
                    } else {
                        log.info("CgroupController : 그룹 정보 조인해서 가져오기 성공!");
                        model.addAttribute("nDTO", nDTO);
                    }

                }

                //파일 path 이름 5가지만 limit 주고 가져오기
                CFileDTO tDTO = null;
                tDTO = new CFileDTO();
                tDTO.setGroup_seq(group_seq);
                List<CFileDTO> fileList = cBoardService.getFilePath(tDTO);
                model.addAttribute("fileList",fileList);

                // 과제 5개만 가져오기
                CAssignmentDTO dDTO = null;
                dDTO = new CAssignmentDTO();
                dDTO.setFk_group_seq(group_seq);
                List<CAssignmentDTO> sList = cAssignmentService.getAssignment(dDTO);
                if (sList == null) {
                    log.info("과제 5개 가져오는 쿼리 에러");
                } else {
                    log.info("과제 5개를 정상적으로 가져와 계속 진행");
                    for (int i = 0; i < sList.size(); i++) {

                        //날짜를 받아와 비교함
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                        // 현재날짜를 받아옴
                        Date now = new Date();
                        String firstDate1 = sdf.format(now);
                        // 비교하기위해 String을 date타입으로 변환함
                        Date firstDate = sdf.parse(firstDate1);
                        Date secondDate = sdf.parse(sList.get(i).getAssignment_deadline());

                        long diff = secondDate.getTime() - firstDate.getTime();

                        TimeUnit time = TimeUnit.DAYS;
                        // 오늘 날짜와 과제 마감일을 비교후 몇일이 남았는지 반환
                        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                        log.info("마감일까지의 남은 기한은 : " + diffrence + "일");
                        sList.get(i).setDiffrence_deadline(diffrence);

                        // 과제 생성일을 원하는 포멧으로 변경함
                        Date assignment_regdate1 = sdf.parse(sList.get(i).getAssignment_regdate());
                        String assignment_regdate2 = sdf.format(assignment_regdate1);
                        log.info("assignment_regdate2" + assignment_regdate2);
                        sList.get(i).setAssignment_start_date(assignment_regdate2);

                    }
                    model.addAttribute("sList",sList);
                }

            }






        }catch(Exception e){
                log.info("로직 실패 :" + e.toString());
                log.info(e.toString());
                e.printStackTrace();
        }finally{
                //전달하기
                log.info("");
        }

        log.info("CgroupController : ComgIndex 그룹 페이지 끝 !");
        return "/COMGgroup/GroupIndex";
    }

    @GetMapping(value = "COMG/EditGroup")
    public String EditGroup(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception{
        log.info("CgroupController : EditGroup 페이지 시작 !");
        
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        CGroupDTO gDTO = null;
        CGroupDTO rDTO = null;
        String group_seq = CmmUtil.nvl(request.getParameter("update_GroupSEQ"));
        log.info("jsp 에서 받아온 GroupSEQ : "+group_seq);
        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");

        // 비로그인 접속 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CgroupController : 로그인을 하지않고 그룹페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login?GroupSEQ="+group_seq;

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }else {
            log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
            model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));
        }
        gDTO = new CGroupDTO();
        gDTO.setGroup_seq(group_seq);
        // jsp에서 받아온 Seq값으로 그룹 정보 가져오기
        rDTO = new CGroupDTO();
        rDTO = cGroupService.getGroupInfo(gDTO);
        if (rDTO.getGroup_name() == null) {
            log.info("rDTO가 null이므로 강제로 메모리에 올림 ");
            rDTO = new CGroupDTO();
        } else {
            log.info("rDTO가 null이 아니므로 로직 계속 진행함");
            model.addAttribute("rDTO",rDTO);

            String file_seq = CmmUtil.nvl(rDTO.getFile_seq());
            CFileDTO fDTO = null;
            CFileDTO iDTO = null;
            fDTO = new CFileDTO();
            iDTO = new CFileDTO();
            fDTO.setFk_file_seq(file_seq);
            iDTO = cGroupService.getFilePath(fDTO);
            if (iDTO.getServer_file_path() == null){
                log.info("CgroupController : 그룹 프로필 사진 가져오기 실패!");
            }else {
                log.info("CgroupController : 그룹 프로필 사진 가져오기 성공!");
                String file_path = CmmUtil.nvl(iDTO.getServer_file_path());
                model.addAttribute("file_path",file_path);
            }

        }
        log.info("CgroupController : EditGroup 페이지 끝 !");
        return "/COMGgroup/EditGroup";
    }

    @ResponseBody
    @PostMapping(value = "COMG/EditGroupLogic")
    public int EditGroupLogic(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception{
        log.info("CgroupController : EditGroupLogic 시작 !");

        int res = 0;
        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        CGroupDTO bDTO = null;
        bDTO = new CGroupDTO();

        String user_id = CmmUtil.nvl(uDTO.getUser_id());
        String group_introduce = request.getParameter("group_introduce");
        String group_seq = CmmUtil.nvl(request.getParameter("group_seq"));

        bDTO.setGroup_regid(user_id);
        bDTO.setGroup_introduce(group_introduce);
        bDTO.setGroup_seq(group_seq);

        res = cGroupService.UpdateGroupInfo(bDTO);
        if(res == 1){
            log.info("CgroupController : 그룹정보 수정 완료 !");
        }else {
            log.info("CgroupController : 그룹정보 수정 실패 !");
        }

        log.info("CgroupController : EditGroupLogic 끝 !");
        return res;
    }

    @GetMapping(value = "COMG/Creategroup")
    public String CreateGroup(HttpServletRequest request, HttpSession session, ModelMap model)  {
        log.info("CgroupController : Creategroup 그룹 페이지 출력 !");

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
        }else {
            model.addAttribute("user_name",session.getAttribute("user_name"));
        }

        log.info("CgroupController : Creategroup 그룹 페이지 출력 끝!");
        return "/COMGmain/CreateGroup";
    }

    @GetMapping(value = "COMG/Joingroup")
    public String Joingroup(HttpServletRequest request, HttpSession session, ModelMap model)  {
        log.info("CgroupController : Joingroup 그룹 가입 페이지 시작 !");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        CGroupDTO gDTO = null;
        CGroupDTO rDTO = null;
        String group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
        log.info("JoinGroup 에서 받아온 GroupSEQ : "+group_seq);

        // 비로그인 접속 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CmainController : 로그인을 하지않고 메인페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login?GroupSEQ="+group_seq;

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }

        try {
            CUserDTO uDTO = null;
            uDTO = new CUserDTO();
            uDTO = (CUserDTO) session.getAttribute("suDTO");

            if (uDTO.getUser_id() == null) {
                uDTO = new CUserDTO();
                log.info("CgroupController : 로그인을 하지않고 그룹페이지 접속함!");

                alert_title = "로그인이 필요합니다!";
                alert_contents = "로그인 후 접속하여 주세요.";
                alert_state = "warning";
                alert_aftersending = "COMG/login";

                model.addAttribute("alert_title", alert_title);
                model.addAttribute("alert_contents", alert_contents);
                model.addAttribute("alert_state", alert_state);
                model.addAttribute("alert_aftersending", alert_aftersending);

                return "/alert/sweetalert";

            } else if (uDTO.getUser_id() != null) {
                log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
                model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));
            }

            gDTO = new CGroupDTO();
            gDTO.setGroup_seq(group_seq);

            // jsp에서 받아온 Seq값으로 그룹 정보 가져오기
            rDTO = new CGroupDTO();
            rDTO = cGroupService.getGroupInfo(gDTO);
            if (rDTO.getGroup_name() == null) {
                log.info("rDTO가 null이므로 강제로 메모리에 올림 ");
                rDTO = new CGroupDTO();
            } else {
                log.info("rDTO가 null이 아니므로 로직 계속 진행함");
                model.addAttribute("rDTO",rDTO);

                String file_seq = CmmUtil.nvl(rDTO.getFile_seq());
                CFileDTO fDTO = null;
                CFileDTO iDTO = null;
                fDTO = new CFileDTO();
                iDTO = new CFileDTO();
                fDTO.setFk_file_seq(file_seq);
                iDTO = cGroupService.getFilePath(fDTO);
                if (iDTO.getServer_file_path() == null){
                    log.info("CgroupController : 그룹 프로필 사진 가져오기 실패!");
                }else {
                    log.info("CgroupController : 그룹 프로필 사진 가져오기 성공!");
                    String file_path = CmmUtil.nvl(iDTO.getServer_file_path());
                    model.addAttribute("file_path",file_path);
                }

            }

        }catch(Exception e){
            log.info("로직 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();
        }finally{
            //전달하기

        }

        log.info("CgroupController : Joingroup 그룹 가입 페이지 끝 !");

        return "/COMGgroup/JoinGroup";
    }

    @ResponseBody
    @PostMapping(value = "COMG/JoingroupLogic")
    public int JoingroupLogic(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("CgroupController : JoingroupLogic 그룹 가입 로직 시작 !");
        int res = 0;

        try {
            String group_seq = request.getParameter("group_seq");
            log.info("ajax로 받아온 group_seq : " + group_seq);
            CUserDTO uDTO = null;
            uDTO = new CUserDTO();
            uDTO = (CUserDTO) session.getAttribute("suDTO");
            String user_seq = CmmUtil.nvl(uDTO.getUser_seq());
            String user_id = CmmUtil.nvl(uDTO.getUser_id());

            CGroupDTO gDTO = null;
            gDTO = new CGroupDTO();
            gDTO.setFk_group_seq(group_seq);
            gDTO.setFk_user_seq(user_seq);
            gDTO.setUser_id(user_id);
            res = cGroupService.InsertGroupUserInfo(gDTO);
            
            if(res == 1){
                log.info("CgroupController : 그룹 가입 성공!");
            }else {
                log.info("CgroupController : 그룹 가입 실패!");
                
            }

        } catch (Exception e) {
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();

        }

        log.info("CgroupController : JoingroupLogic 그룹 가입 로직 끝 !");
        return res;
    }

    @ResponseBody
    @PostMapping(value = "COMG/CreategroupLogic")
    public int CreategroupLogic(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("CgroupController : CreategroupLogic(그룹생성 로직) 시작 !");

        CUserDTO uDTO = null;
        CGroupDTO nDTO = null;
        int res = 0;
        try {
            // 세션에 담긴 사용자 정보에서 내가 원하는 정보만 추출함
            uDTO = new CUserDTO();
            uDTO = (CUserDTO) session.getAttribute("suDTO");

            String group_name = CmmUtil.nvl(request.getParameter("group_name"));
            String group_reg_id = CmmUtil.nvl(uDTO.getUser_id());
            String group_password = CmmUtil.nvl(request.getParameter("group_pwd"));
            String group_introduce = CmmUtil.nvl(request.getParameter("group_introduce"));
            nDTO = new CGroupDTO();
            nDTO.setGroup_name(group_name);
            nDTO.setGroup_regid(group_reg_id);
            nDTO.setGroup_password(group_password);
            nDTO.setGroup_introduce(group_introduce);
            res = cGroupService.InsertGroupInfo(nDTO);

            if (res==1) {
                log.info("그룹 생성 성공! : 그룹 생성이 성공하였습니다.");
                // 그룹 생성 성공하여 DB에서 GROUP_SEQ 받아옴
                CGroupDTO oDTO = null;
                oDTO = new CGroupDTO();
                oDTO = cGroupService.getGroupSeq(nDTO);
                if (oDTO == null){
                    res = 3;
                    log.info("그룹 seq 가져오기 실패!");

                }else {
                    log.info("그룹 seq 가져오기 성공!");
                    String fk_user_seq = CmmUtil.nvl(uDTO.getUser_seq());
                    String fk_group_seq = CmmUtil.nvl(oDTO.getGroup_seq());
                    String user_id = CmmUtil.nvl(uDTO.getUser_id());;
                    String user_auth ="1";
                    nDTO = null;
                    nDTO = new CGroupDTO();
                    nDTO.setFk_user_seq(fk_user_seq);
                    nDTO.setFk_group_seq(fk_group_seq);
                    nDTO.setUser_id(user_id);
                    nDTO.setUser_auth(user_auth);
                    int InsertAdmin= cGroupService.InsertGroupUserAdminInfo(nDTO);
                    
                    if (InsertAdmin == 1){
                        log.info("CgroupController : 그룹 어드민 등록 성공!");
                        res = 1;
                    }else {
                        log.info("CgroupController : 그룹 어드민 등록 실패!");
                        res = 3;
                    }
                }

            }else if (res==2) {
                log.info("그룹 생성 실패! : 그룹 이름이 이미 존재합니다.");

            }else {
                log.info("그룹 생성 실패! : 오류로 인해 그룹 생성이 실패하였습니다.");
            }

        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            log.info("그룹 생성 로직 실패 :" + e.toString());
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".CreategroupLogic end!");
            //그룹 생성 여부 결과 메시지 전달하기

        }
        log.info("CgroupController : CreategroupLogic(그룹생성 로직) 끝 !");
        return res;
    }

    @PostMapping (value = "COMG/DeleteGroupUser")
    public String DeleteGroupUser(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{
        log.info("CjoinController : DeleteGroupUser 시작!");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        int res = 0;

        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        if (uDTO == null) {
            uDTO = new CUserDTO();
            log.info("입력받은 정보와 일치하는 아이디가 없어 nDTO 널처리를 진행함");
        }
        String delete_group_seq = CmmUtil.nvl(request.getParameter("delete_group_seq"));
        String fk_user_seq = CmmUtil.nvl(uDTO.getUser_seq());

        CGroupDTO gDTO = null;
        gDTO = new CGroupDTO();
        gDTO.setFk_user_seq(fk_user_seq);
        gDTO.setFk_group_seq(delete_group_seq);

        res = cGroupService.DeleteGroupUser(gDTO);

        if (res==1) {
            alert_title = "그룹 탈퇴 성공!";
            alert_contents = "탈퇴되었습니다.";
            alert_state = "success";
            alert_aftersending = "COMG/Mypage";
        }else {
            alert_title= "그룹 탈퇴 실패!";
            alert_contents= "다시 한번 시도해 주세요.";
           alert_state= "error";
            alert_aftersending = "COMG/Mypage";
        }

        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);

        log.info("CjoinController : DeleteGroup 끝!");
        return "/alert/sweetalert";
    }

    @GetMapping(value = "COMG/EditMessage")
    public String EditMessage(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception{
        log.info("CgroupController : EditMessage 페이지 시작 !");

        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";
        CGroupDTO gDTO = null;
        CGroupDTO rDTO = null;
        String group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
        log.info("jsp 에서 받아온 GroupSEQ : "+group_seq);
        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");

        // 비로그인 접속 방지
        if (session.getAttribute("user_name") == null) {
            log.info("CgroupController : 로그인을 하지않고 그룹페이지 접속함!");

            alert_title = "로그인이 필요합니다!";
            alert_contents = "로그인 후 접속하여 주세요.";
            alert_state = "warning";
            alert_aftersending = "COMG/login?GroupSEQ="+group_seq;

            model.addAttribute("alert_title", alert_title);
            model.addAttribute("alert_contents", alert_contents);
            model.addAttribute("alert_state", alert_state);
            model.addAttribute("alert_aftersending", alert_aftersending);

            return "/alert/sweetalert";
        }else {
            log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
            model.addAttribute("user_name", CmmUtil.nvl(uDTO.getUser_name()));
        }
        gDTO = new CGroupDTO();
        gDTO.setGroup_seq(group_seq);
        // jsp에서 받아온 Seq값으로 그룹 정보 가져오기
        rDTO = new CGroupDTO();
        rDTO = cGroupService.getGroupInfo(gDTO);
        if (rDTO.getGroup_name() == null) {
            log.info("rDTO가 null이므로 강제로 메모리에 올림 ");
            rDTO = new CGroupDTO();
        } else {
            log.info("rDTO가 null이 아니므로 로직 계속 진행함");
            model.addAttribute("rDTO",rDTO);

            String file_seq = CmmUtil.nvl(rDTO.getFile_seq());
            CFileDTO fDTO = null;
            CFileDTO iDTO = null;
            fDTO = new CFileDTO();
            iDTO = new CFileDTO();
            fDTO.setFk_file_seq(file_seq);
            iDTO = cGroupService.getFilePath(fDTO);
            if (iDTO.getServer_file_path() == null){
                log.info("CgroupController : 그룹 프로필 사진 가져오기 실패!");
            }else {
                log.info("CgroupController : 그룹 프로필 사진 가져오기 성공!");
                String file_path = CmmUtil.nvl(iDTO.getServer_file_path());
                model.addAttribute("file_path",file_path);
            }

        }
        log.info("CgroupController : EditMessage 페이지 끝 !");
        return "/COMGgroup/EditMessage";
    }

    @PostMapping(value = "COMG/EditMessageLogic")
    public String EditMessageLogic(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception{
        log.info("CgroupController : EditGroupLogic 시작 !");
        String alert_title = "";
        String alert_contents = "";
        String alert_state = "";
        String alert_aftersending = "";

        int res = 0;
        CUserDTO uDTO = null;
        uDTO = new CUserDTO();
        uDTO = (CUserDTO) session.getAttribute("suDTO");
        log.info("세션에 담긴 DTO에서 꺼내온 값 :" + uDTO.getUser_name());
        CGroupDTO bDTO = null;
        bDTO = new CGroupDTO();

        String user_id = CmmUtil.nvl(uDTO.getUser_id());
        String group_msg = request.getParameter("group_msg");
        String group_seq = CmmUtil.nvl(request.getParameter("group_seq"));

        bDTO.setGroup_regid(user_id);
        bDTO.setGroup_msg(group_msg);
        bDTO.setGroup_seq(group_seq);

        res = cGroupService.UpdateGroupMsg(bDTO);
        if (res==1) {
            alert_title = "메세지 수정 성공!";
            alert_contents = "메세지가 수정되었습니다.";
            alert_state = "success";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }else {
            alert_title= "메세지 수정 실패!";
            alert_contents= "다시 한번 시도해 주세요.";
            alert_state= "error";
            alert_aftersending = "COMG/Group?GroupSEQ="+group_seq;
        }

        model.addAttribute("alert_title", alert_title);
        model.addAttribute("alert_contents", alert_contents);
        model.addAttribute("alert_state", alert_state);
        model.addAttribute("alert_aftersending", alert_aftersending);
        
        log.info("CgroupController : EditGroupLogic 끝 !");
        return "/alert/sweetalert";
    }
}
