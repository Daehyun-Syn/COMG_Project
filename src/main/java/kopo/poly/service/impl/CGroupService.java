package kopo.poly.service.impl;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.persistance.mapper.ICGroupMapper;
import kopo.poly.service.ICGroupService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("CGroupService")
public class CGroupService implements ICGroupService {

    private final ICGroupMapper cgroupMapper;


    @Autowired
    public CGroupService(ICGroupMapper cgroupMapper) {
        this.cgroupMapper = cgroupMapper;
    }

    @Transactional
    @Override
    public int InsertGroupInfo(CGroupDTO uDTO) throws Exception {

        log.info("CGroupService : InsertGroupInfo(그룹 생성 로직) 시작 !");
        // 그룹생성 성공: 1,
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CGroupDTO();
        }
        if(uDTO.getFile_seq() == null){
            //그룹 이름 중복방지를 위해 DB에서 데이터를 조회
            CGroupDTO rDTO = cgroupMapper.getGroupExists(uDTO);
            log.info("그룹이름 중복제거 결과 true(중복), false(중복아님): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));
            //중복된 그룹 이름이 있는 경우, 결과값을 2로 변경하고 더이상 작업을 진행하지않음
            if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
                log.info("그룹 이름이 중복이므로 더이상 작업 진행하지 않음!");
                res=2;

            } else {
                log.info("그룹 이름이 중복이 아니므로 그룹 생성 진행함");
                //그룹 생성
                log.info("CGroupService : InsertGroupInfo, 그룹 생성 시작");
                int success = cgroupMapper.InsertGroupInfo(uDTO);

                //db에 데이터가 등록되었다면,
                if(success > 0) {
                    res=1;
                    log.info("CGroupService : InsertGroupInfo, 그룹 생성 완료");
                }else {
                    res = 0;
                }

            }

            log.info("CGroupService : InsertGroupInfo(그룹 생성 로직) 끝 !");
            return res;
        }else {
            //그룹 이름 중복방지를 위해 DB에서 데이터를 조회
            CGroupDTO rDTO = cgroupMapper.getGroupExists(uDTO);
            log.info("로그인 중복제거 결과 true(중복), false(중복아님): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));
            //중복된 그룹 이름이 있는 경우, 결과값을 2로 변경하고 더이상 작업을 진행하지않음
            if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
                log.info("그룹 이름이 중복이므로 더이상 작업 진행하지 않음!");
                res=2;
            } else {
                //그룹 생성
                log.info("그룹 이름이 중복이 아니므로 그룹 생성 진행함");
                log.info("CGroupService : InsertGroupInfo, 그룹 생성 시작");
                int success = cgroupMapper.InsertFileGroupInfo(uDTO);

                //db에 데이터가 등록되었다면,
                if(success > 0) {
                    res=1;
                    log.info("CGroupService : InsertGroupInfo, 그룹 생성 완료");
                }else {
                    res = 0;
                }

            }
            log.info("CGroupService : InsertGroupInfo(그룹 생성 로직) 끝 !");
            return res;
        }


    }


    @Override
    public int GroupCheckForAjax(CGroupDTO uDTO) throws Exception {

        log.info("CGroupService : GroupCheckForAjax(아작스 그룹이름 중복체크) 시작 !");
        // 중복된 그룹 없음 : 0, 중복된 그룹 있음 : 1
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CGroupDTO();
        }

        //그룹 이름 중복방지를 위해 DB에서 데이터를 조회
        CGroupDTO rDTO = cgroupMapper.getGroupExists(uDTO);
        log.info("로그인 중복제거 결과 true(중복), false(중복아님): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));

        // 중복된 그룹 없음 : 0, 중복된 그룹 있음 : 1 을 res값에 담아 보내줌.
        if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
            res = 1;
            log.info("CGroupService : GroupCheckForAjax, 그룹 이름 중복 ");
        } else {
            res = 0;
            log.info("CGroupService : GroupCheckForAjax, 그룹 이름 중복되지 않음 ");
        }
        
        log.info("CGroupService : GroupCheckForAjax(아작스 그룹이름 중복체크) 끝 !");
        return res;
    }

    @Override
    public List<CGroupDTO> getGroupList(CGroupDTO gDTO) throws Exception {
        log.info("CGroupService : getGroupList 시작 !");
        if (gDTO == null){
            log.info("컨트롤러에서 받아온 gDTO 가 null 이라 메모리에 올림");
            gDTO = new CGroupDTO();
        }

        List<CGroupDTO> rlist = new ArrayList<>();
        rlist = cgroupMapper.getGroupList(gDTO);

        if(rlist == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("CGroupService : 그룹이 존재하지않아 강제로 메모리에 rlist를 올림!");
            rlist = new ArrayList<>();
        }
        else {
            log.info("CGroupService : 그룹리스트 가져오기 성공");
        }

        log.info("CGroupService : getGroupList 끝 !");
        return rlist;
    }

    @Override
    public List<CFileDTO> getFileList() throws Exception {
        log.info("CGroupService : getFileList 시작 !");
        List<CFileDTO> flist = new ArrayList<>();
        flist = cgroupMapper.getFileList();

        if(flist == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("CGroupService : 파일이 존재하지않아 강제로 메모리에 rlist를 올림!");
            flist = new ArrayList<>();
        }
        else {
            log.info("CGroupService : 파일리스트 가져오기 성공");
        }

        log.info("CGroupService : getGroupList 끝 !");
        return flist;
    }

    @Override
    public CGroupDTO getGroupInfo(CGroupDTO gDTO) throws Exception {
        log.info("CGroupService : getGroupInfo 시작");

        if(gDTO == null) {
            gDTO = new CGroupDTO();
        }
        CGroupDTO nDTO = null;
        nDTO = new CGroupDTO();
        nDTO = cgroupMapper.getGroupInfo(gDTO);
        if(nDTO == null) {
            log.info("Group정보를 가져오지 못함");
            nDTO = new CGroupDTO();
        }

        log.info("CGroupService : getGroupInfo 끝");
        return nDTO;
    }

    @Override
    public CFileDTO getFilePath(CFileDTO fDTO) throws Exception {
        log.info("CGroupService : getFilePath 시작");
        if(fDTO == null) {
            fDTO = new CFileDTO();
        }

        CFileDTO iDTO = null;
        iDTO = new CFileDTO();
        iDTO = cgroupMapper.getFilePath(fDTO);
        if(iDTO.getServer_file_path() == null){
            log.info("CGroupService : 파일 패스를 가져오지 못함");
            iDTO = new CFileDTO();
        }

        log.info("CGroupService : getFilePath 끝");
        return iDTO;
    }

    @Transactional
    @Override
    public int InsertGroupUserInfo(CGroupDTO gDTO) throws Exception {
        log.info("CGroupService : InsertGroupUserInfo(그룹 가입 로직) 시작 !");
        // 그룹가입 성공: 1,
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (gDTO == null) {
            gDTO = new CGroupDTO();
        }

        int success = cgroupMapper.InsertGroupUserInfo(gDTO);

        //db에 데이터가 등록되었다면,
        if(success > 0) {
            res=1;
            log.info("CGroupService : InsertGroupUserInfo, 그룹 가입 성공");
        }else {
            log.info("CGroupService : InsertGroupUserInfo, 그룹 가입 실패");
        }

        return res;
    }

    @Override
    public CGroupDTO SelectGroupUserInfo(CGroupDTO gDTO) throws Exception {
        log.info("CGroupService : SelectGroupUserInfo 시작 !");

        if(gDTO == null){
            gDTO = new CGroupDTO();
        }

        CGroupDTO uDTO = null;
        uDTO = new CGroupDTO();
        uDTO = cgroupMapper.SelectGroupUserInfo(gDTO);
        if(uDTO == null){
            uDTO = new CGroupDTO();
            log.info("SelectGroupUserInfo 매퍼로직 에러");
        }else {
            log.info("SelectGroupUserInfo 매퍼로직 성공");
        }


        log.info("CGroupService : SelectGroupUserInfo 끝 !");
        return uDTO;
    }

    @Override
    public int DeleteGroupUser(CGroupDTO gDTO) throws Exception {
        log.info("CGroupService : DeleteGroupUser 시작 !");
        // 0 :실패, 1 : 성공
        int res = 0;
        
        if(gDTO == null){
            gDTO = new CGroupDTO();
        }
        
        int success = cgroupMapper.DeleteGroupUser(gDTO);
        if (success > 0) {
            log.info("회원 탈퇴 성공!");
            res = 1;
        }else {
            log.info("회원 탈퇴 실패!");
        }
        log.info("CGroupService : DeleteGroupUser 끝 !");
        return res;
    }

    @Override
    public CGroupDTO SelectJoinGroup(CGroupDTO jDTO) throws Exception {
        log.info("CGroupService : SelectJoinGroup 시작 !");

        if(jDTO == null){
            jDTO = new CGroupDTO();
        }
        CGroupDTO nDTO = null;
        nDTO = new CGroupDTO();
        nDTO = cgroupMapper.SelectJoinGroup(jDTO);
        if(nDTO == null){
            log.info("그룹에 해당하는 사용자가 없음!");
            nDTO = new CGroupDTO();
        }else {
            log.info("그룹에 해당하는 사용자가 있음!");
        }
        log.info("CGroupService : SelectJoinGroup 끝 !");
        return nDTO;
    }
    @Transactional
    @Override
    public int UpdateGroupInfo(CGroupDTO bDTO) throws Exception {
        log.info("CGroupService : UpdateGroupInfo 시작 !");
        int res = 0;
        int success = 0;
        if (bDTO == null){
            bDTO = new CGroupDTO();
        }

        if (bDTO.getFile_seq() != null){
            success = cgroupMapper.UpdateGroupFileInfo(bDTO);
        }else {
            success = cgroupMapper.UpdateGroupInfo(bDTO);
        }
        if (success > 0) {
            log.info("그룹 수정 성공!");
            res = 1;
        }else {
            log.info("그룹 수정 실패!");
        }
        log.info("CGroupService : UpdateGroupInfo 끝 !");
        return res;
    }
}
