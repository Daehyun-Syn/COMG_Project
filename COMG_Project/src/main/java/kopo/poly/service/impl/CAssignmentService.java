package kopo.poly.service.impl;


import kopo.poly.dto.CAssignmentDTO;
import kopo.poly.dto.CFileDTO;
import kopo.poly.persistance.mapper.ICAssignmentMapper;
import kopo.poly.service.ICAssignmentService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service("CAssignmentService")
public class CAssignmentService implements ICAssignmentService {

    private final ICAssignmentMapper cAssignmentMapper;


    @Autowired
    public CAssignmentService(ICAssignmentMapper cAssignmentMapper) {
        this.cAssignmentMapper = cAssignmentMapper;
    }

    @Override
    public int InsertAssignmentInfo(CAssignmentDTO rDTO) throws Exception {
        log.info("CAssignmentService : InsertAssignmentInfo 시작!");
        // 성공 1, 실패 0
        int res = 0;

        if (rDTO == null) {
            log.info("컨트롤러에서 받아온 rDTO가 null이라 강제로 메모리에 올림");
            rDTO = new CAssignmentDTO();
        }

        int success = cAssignmentMapper.InsertAssignmentInfo(rDTO);
        if (success > 0) {
            res = 1;
            log.info("게시글 작성 완료 !");
        } else {
            log.info("게시글 작성 실패 !");
        }

        log.info("CAssignmentService : InsertAssignmentInfo 끝!");
        return res;
    }

    @Override
    public List<CAssignmentDTO> getAssignmentList(CAssignmentDTO bDTO) throws Exception {
        log.info("CAssignmentService : getAssignmentList 시작 !");

        if (bDTO == null) {
            log.info("CAssignmentService : bDTO가 null이라 강제로 메모리에 올림!");
            bDTO = new CAssignmentDTO();
        }
        List<CAssignmentDTO> rlist = new ArrayList<>();
        rlist = cAssignmentMapper.getAssignmentList(bDTO);

        if (rlist == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("CAssignmentService : 과제가 존재하지않아 강제로 메모리에 rlist를 올림!");
            rlist = new ArrayList<>();
        } else {
            log.info("CAssignmentService : 과제 가져오기 성공");
        }

        log.info("CAssignmentService : getAssignmentList 끝 !");
        return rlist;
    }

    @Override
    public CAssignmentDTO getAssignmentInfo(CAssignmentDTO bDTO) throws Exception {
        log.info("CAssignmentService : getAssignmentInfo 시작!");

        if (bDTO == null) {
            log.info("CAssignmentService : bDTO가 null이라 강제로 메모리에 올림!");
            bDTO = new CAssignmentDTO();
        }

        CAssignmentDTO oDTO = null;
        oDTO = new CAssignmentDTO();
        oDTO = cAssignmentMapper.getAssignmentInfo(bDTO);
        if (oDTO == null) {
            log.info("CAssignmentService : 과제가 존재하지않아 강제로 메모리에 oDTO를 올림!");
            oDTO = new CAssignmentDTO();
        }

        log.info("CAssignmentService : getAssignmentInfo 끝!");
        return oDTO;
    }

    @Override
    public CAssignmentDTO getAssignmentCount(CAssignmentDTO sDTO) throws Exception {
        log.info("CAssignmentService : getAssignmentCount 시작!");

        if (sDTO == null) {
            log.info("CAssignmentService : sDTO가 null이라 강제로 메모리에 올림!");
            sDTO = new CAssignmentDTO();
        }

        CAssignmentDTO oDTO = null;
        oDTO = new CAssignmentDTO();
        oDTO = cAssignmentMapper.getAssignmentCount(sDTO);
        if (oDTO.getAssignment_count() == 0) {
            log.info("CAssignmentService : 아무도 과제를 제출하지 않음");
            oDTO = new CAssignmentDTO();
        } else {
            log.info("CAssignmentService : 과제를 제출자 있음 : " + oDTO.getAssignment_count() + "명");
        }

        log.info("CAssignmentService : getAssignmentCount 끝!");
        return oDTO;
    }

    @Override
    public int insertAssignmentSend(CAssignmentDTO aDTO) throws Exception {
        log.info(this.getClass().getName() + "과제제출 현황 서비스 시작!");

        int res = 0;

        if (aDTO == null) {
            aDTO = new CAssignmentDTO();
        }


        int success = cAssignmentMapper.insertAssignmentSend(aDTO);

        if (success > 0) {
            log.info("성공");
            res = 1;
        } else {
            log.info("실패");
        }

        log.info(this.getClass().getName() + "과제제출 현황 서비스 종료!");
        return res;
    }

    @Override
    public List<CAssignmentDTO> AssingSendUserList(CAssignmentDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + "과제제출 현황 및 유저 리스트 가져오기 시작!");

        List<CAssignmentDTO> uList = null;
        List<CAssignmentDTO> sList = null;

        if (pDTO == null) {
            pDTO = new CAssignmentDTO();
        }

        uList = new ArrayList<CAssignmentDTO>();

        uList = cAssignmentMapper.AssingSendUserList(pDTO);
        log.info("가져온 그룹별 유저리스트 사이즈는 ? " + uList.size());

        if (uList == null) {
            uList = new ArrayList<CAssignmentDTO>();
        } else {
            sList = cAssignmentMapper.AssingSendList(pDTO);
            log.info("가져온 과제 현황은 ? " + sList.size());
            if (sList == null) {
                sList = new ArrayList<CAssignmentDTO>();
            } else {
                for (int i = 0; i < uList.size(); i++) {
                    for (int j = 0; j < sList.size(); j++) {
                        if (uList.get(i).getUser_seq().equals(sList.get(j).getFk_user_seq())) {
                            uList.get(i).setAssignment_feedback(sList.get(j).getAssignment_feedback());
                            uList.get(i).setAssignment_send_regdate(sList.get(j).getAssignment_send_regdate());
                            uList.get(i).setAssignment_seq(sList.get(j).getAssignment_seq());
                            uList.get(i).setDownload_file_path(sList.get(j).getServer_file_name()+"/"+sList.get(j).getOrigin_file_name());
                            log.info("가져온 과제제출일은 ? " + sList.get(j).getAssignment_send_regdate());
                            log.info("가져온 과제제출일은 ? " + uList.get(i).getAssignment_send_regdate());
                        }
                    }
                }
            }
        }

        log.info(this.getClass().getName() + "과제제출 현황 및 유저 리스트 가져오기 종료!");
        return uList;
    }

    @Override
    public int updateFeedback(CAssignmentDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+"피드백 업데이트 및 카톡 전송 시작!");
        int res = 0;
        String fullPath = ""; // 카카오톡 전송 결과 success or fail
        if(pDTO == null){
            pDTO = new CAssignmentDTO();
        }

        res = cAssignmentMapper.updateFeedback(pDTO);

        log.info("피드백 저장 결과는 : " + res);

        if(res == 1){
            log.info(this.getClass().getName() + "카카오톡 메시지 전송 서비스 시작!");

            UrlUtil uu = new UrlUtil();
            String url = "http://localhost:8000"; // 파이썬 포트 5005
            String api = "/kakaoFriend";  // 파이썬 app.route 명
            String data = "?content=";
            String content_data = "교수님이 피드백을 완료했습니다";

            fullPath = uu.urlReadforString(url + api + data + URLEncoder.encode(content_data,"UTF-8"));

            log.info("파이썬에서 넘어온 메시지 전송 결과는 ? " + fullPath);
            log.info(this.getClass().getName() + "카카오톡 메시지 전송 서비스 종료!");
        }

        if(fullPath.equals("success")){
            res = 1;
        }else{
            res =2;
        }



        log.info(this.getClass().getName()+"피드백 업데이트 및 카톡 전송 종료!");
        return res;
    }

    @Override
    public List<CAssignmentDTO> getAssignment(CAssignmentDTO pDTO) throws Exception {
        log.info("CAssignmentService : getAssignment5 시작!");
        if (pDTO == null){
            pDTO = new CAssignmentDTO();
        }

        List<CAssignmentDTO> uList = null;
        uList = new ArrayList<>();
        uList = cAssignmentMapper.getAssignment(pDTO);
        if (uList == null) {
        uList = new ArrayList<>();
        }else {
            log.info("CAssignmentService : Assignment 5개 가져옴");
        }

        log.info("CAssignmentService : getAssignment5 끝!");
        return uList;
    }

    @Override
    public CAssignmentDTO getFeedBack(CAssignmentDTO sDTO) throws Exception {
        if (sDTO == null){
            sDTO = new CAssignmentDTO();
        }
        CAssignmentDTO oDTO = null;
        oDTO = new CAssignmentDTO();
        oDTO = cAssignmentMapper.getFeedBack(sDTO);
        if (oDTO == null){
            oDTO = new CAssignmentDTO();
        }
        return oDTO;
    }
}
