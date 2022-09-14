package kopo.poly.service.impl;

import kopo.poly.dto.CBoardDTO;
import kopo.poly.dto.CCommentDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICCommentMapper;
import kopo.poly.persistance.mapper.ICJoinMapper;
import kopo.poly.service.ICCommentService;
import kopo.poly.service.ICJoinService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("CCommentService")
public class CCommentService implements ICCommentService {

    private final ICCommentMapper cCommentMapper;


    @Autowired
    public CCommentService(ICCommentMapper cCommentMapper) {
        this.cCommentMapper = cCommentMapper;
    }

    @Override
    public int InsertCommentInfo(CCommentDTO cDTO) throws Exception {
        log.info("CCommentService : InsertCommentInfo 시작!");
        // 성공 1, 실패 0
        int res = 0;

        if(cDTO == null){
            log.info("컨트롤러에서 받아온 cDTO가 null이라 강제로 메모리에 올림");
            cDTO = new CCommentDTO();
        }

        int success = cCommentMapper.InsertCommentInfo(cDTO);
        if(success > 0 ){
            res=1;
            log.info("댓글 작성 완료 !");
        }else {
            log.info("댓글 작성 실패 !");
        }

        log.info("CCommentService : InsertCommentInfo 끝!");
        return res;
    }

    @Override
    public List<CCommentDTO> getCommentList(CCommentDTO cDTO) throws Exception {
        log.info("CCommentService : getCommentList 시작 !");

        if(cDTO == null){
            log.info("CBoardService : cDTO가 null이라 강제로 메모리에 올림!");
            cDTO = new CCommentDTO();
        }
        List<CCommentDTO> rlist = new ArrayList<>();
        rlist = cCommentMapper.getCommentList(cDTO);

        if(rlist == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("CCommentService : 댓글이 존재하지않아 강제로 메모리에 rlist를 올림!");
            rlist = new ArrayList<>();
        } else {
            log.info("CCommentService : 댓글 가져오기 성공");
        }

        log.info("CCommentService : getCommentList 끝 !");
        return rlist;
    }


    @Override
    public int UpdateCommentInfo(CCommentDTO cDTO) throws Exception {
        log.info("CCommentService : UpdateCommentInfo 시작!");

        // 성공 1, 실패 0
        int res = 0;

        if(cDTO == null){
            log.info("컨트롤러에서 받아온 cDTO가 null이라 강제로 메모리에 올림");
            cDTO = new CCommentDTO();
        }

        int success = cCommentMapper.UpdateCommentInfo(cDTO);

        if(success > 0 ){
            res=1;
            log.info("댓글 수정 완료 !");
        }else {
            log.info("댓글 수정 실패 !");
        }

        log.info("CCommentService : UpdateCommentInfo 끝!");
        return res;
    }

    @Override
    public int DeleteComment(CCommentDTO cDTO) throws Exception {
        log.info("CBoardService : DeleteComment 시작!");
        // 성공 1, 실패 0
        int res = 0;

        if(cDTO == null){
            log.info("컨트롤러에서 받아온 cDTO가 null이라 강제로 메모리에 올림");
            cDTO = new CCommentDTO();
        }

        int success = cCommentMapper.DeleteComment(cDTO);

        if(success > 0 ){
            res=1;
            log.info("게시글 삭제 완료 !");
        }else {
            log.info("게시글 삭제 실패 !");
        }

        log.info("CCommentService : DeleteComment 끝!");
        return res;
    }
}
