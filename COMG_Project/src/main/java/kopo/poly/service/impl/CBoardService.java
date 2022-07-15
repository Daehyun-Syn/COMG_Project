package kopo.poly.service.impl;


import kopo.poly.dto.CBoardDTO;
import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.persistance.mapper.ICBoardMapper;
import kopo.poly.persistance.mapper.ICGroupMapper;
import kopo.poly.service.ICBoardService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service("CBoardService")
public class CBoardService implements ICBoardService {

    private final ICBoardMapper cBoardMapper;


    @Autowired
    public CBoardService(ICBoardMapper cBoardMapper) {
        this.cBoardMapper = cBoardMapper;
    }

    @Override
    public int InsertBoardInfo(CBoardDTO rDTO) throws Exception {
        log.info("CBoardService : InsertBoardInfo 시작!");
        // 성공 1, 실패 0
        int res = 0;

        if(rDTO == null){
            log.info("컨트롤러에서 받아온 rDTO가 null이라 강제로 메모리에 올림");
            rDTO = new CBoardDTO();
        }

        int success = cBoardMapper.InsertBoardInfo(rDTO);
        if(success > 0 ){
            res=1;
            log.info("게시글 작성 완료 !");
        }else {
            log.info("게시글 작성 실패 !");
        }

        log.info("CBoardService : InsertBoardInfo 끝!");
        return res;
    }

    @Override
    public List<CBoardDTO> getBoardList(CBoardDTO bDTO) throws Exception {
        log.info("CBoardService : getBoardList 시작 !");

        if(bDTO == null){
            log.info("CBoardService : bDTO가 null이라 강제로 메모리에 올림!");
            bDTO = new CBoardDTO();
        }
        List<CBoardDTO> rlist = new ArrayList<>();
        rlist = cBoardMapper.getBoardList(bDTO);

        if(rlist == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("CBoardService : 게시글이 존재하지않아 강제로 메모리에 rlist를 올림!");
            rlist = new ArrayList<>();
        } else {
            log.info("CGroupService : 게시글 가져오기 성공");
        }

        log.info("CBoardService : getBoardList 끝 !");
        return rlist;
    }

    @Override
    public CBoardDTO getBoardInfo(CBoardDTO bDTO) throws Exception {
        log.info("CBoardService : getBoardInfo 시작!");

        if (bDTO == null){
            log.info("CBoardService : bDTO가 null이라 강제로 메모리에 올림!");
            bDTO = new CBoardDTO();
        }

        CBoardDTO oDTO = null;
        oDTO = new CBoardDTO();
        oDTO = cBoardMapper.getBoardInfo(bDTO);
        if (oDTO == null){
            log.info("CBoardService : 게시글이 존재하지않아 강제로 메모리에 oDTO를 올림!");
            oDTO = new CBoardDTO();
        }

        log.info("CBoardService : getBoardInfo 끝!");
        return oDTO;
    }

    @Override
    public int UpdateBoardInfo(CBoardDTO bDTO) throws Exception {
        log.info("CBoardService : UpdateBoardInfo 시작!");

        // 성공 1, 실패 0
        int res = 0;
        int success = 0;
        if(bDTO == null){
            log.info("컨트롤러에서 받아온 bDTO가 null이라 강제로 메모리에 올림");
            bDTO = new CBoardDTO();
        }
        if (bDTO.getFile_seq() != null){
            success = cBoardMapper.UpdateBoardFileInfo(bDTO);
        }else {
            success = cBoardMapper.UpdateBoardInfo(bDTO);
        }
        if(success > 0 ){
            res=1;
            log.info("게시글 수정 완료 !");
        }else {
            log.info("게시글 수정 실패 !");
        }

        log.info("CBoardService : UpdateBoardInfo 끝!");
        return res;
    }

    @Override
    public int DeleteBoard(CBoardDTO bDTO) throws Exception {
        log.info("CBoardService : DeleteBoard 시작!");
        // 성공 1, 실패 0
        int res = 0;

        if(bDTO == null){
            log.info("컨트롤러에서 받아온 bDTO가 null이라 강제로 메모리에 올림");
            bDTO = new CBoardDTO();
        }

        int success = cBoardMapper.DeleteBoard(bDTO);

        if(success > 0 ){
            res=1;
            log.info("게시글 삭제 완료 !");
        }else {
            log.info("게시글 삭제 실패 !");
        }

        log.info("CBoardService : DeleteBoard 끝!");
        return res;
    }

    //파일 path랑 이름 가져오기
    @Override
    public List<CFileDTO> getFilePath(CFileDTO tDTO) throws Exception {
        log.info("CBoardService : getFilePath 시작!");

        if(tDTO == null){
            tDTO = new CFileDTO();
        }


        List<CFileDTO>rList = cBoardMapper.getBoardPath(tDTO);
        if (rList == null){
            log.info("파일정보 가져오기 실패!");
            rList = new ArrayList<>();
        }else{
            log.info("파일정보 가져오기 성공!");
            for(int i =0;i<rList.size();i++){
                log.info(this.getClass().getName()+"rList"+rList.get(i).getServer_file_path());
            }
        }

        log.info("CBoardService : getFilePath 끝!");
        return rList;
    }
}
