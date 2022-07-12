package kopo.poly.service.impl;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICGroupMapper;
import kopo.poly.persistance.mapper.ICMypageMapper;
import kopo.poly.service.ICGroupService;
import kopo.poly.service.ICMypageService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("CMypageService")
public class CMypageService implements ICMypageService {

    private final ICMypageMapper cMypageMapper;


    @Autowired
    public CMypageService(ICMypageMapper cMypageMapper) {
        this.cMypageMapper = cMypageMapper;
    }


    @Override
    public CUserDTO getUserInfo(CUserDTO iDTO) throws Exception {
        log.info("CMypageService : getUserInfo 시작!");
        if(iDTO == null) {
            iDTO = new CUserDTO();
            log.info("iDTO를 강제로 메모리에 올림");
        }

        CUserDTO mDTO = cMypageMapper.getUserInfo(iDTO);
        if(mDTO.getUser_id() == null){
            log.info("getUserInfo 쿼리 오류로 값을 가져오지 못함 !");
        }else {
            log.info("getUserInfo 쿼리 성공 정상적으로 값을 가져옴!");
        }

        log.info("CMypageService : getUserInfo 끝!");
        return mDTO;
    }

    @Override
    public CFileDTO getFile(CFileDTO pDTO) throws Exception {
        log.info("CMypageService : getFile 시작 ");
        // res = 1 성공, 0 실패
        if(pDTO == null) {
            pDTO = new CFileDTO();
            log.info(" 컨트롤러에서 받은 pDTO가 NULL이라 CIdSearchService에서 메모리에 올림");
        }
        CFileDTO nDTO = null;
        nDTO = new CFileDTO();
        nDTO = cMypageMapper.getFile(pDTO);

        if(nDTO.getServer_file_path() == null) {
            nDTO = new CFileDTO();
            log.info("Mapper에서 로직 처리 유저 프로필 가져오기가 제대로 실행되지 않음.");
        }else {
            log.info("Mapper에서 로직 처리 결과 유저 프로필 가져오기 성공");
        }

        log.info("CMypageService : getFile 끝 ");

        return nDTO;
    }

    @Transactional
    @Override
    public int deleteUser(CUserDTO nDTO) throws Exception {
        log.info("CMypageService" + " : deleteUse(회원탈퇴) 시작 !");
        // 회원탈퇴 성공: 1,
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (nDTO == null) {
            nDTO = new CUserDTO();
        }

        log.info("회원탈퇴 시작");
        int success = cMypageMapper.deleteUser(nDTO);

        //db 데이터가 수정되었다면,
        if(success > 0) {
            res = 1;
            log.info("회원탈퇴 완료");
        }else {
            res = 0;
            log.info("회원탈퇴 실패");
        }

        log.info("CMypageService" + " : deleteUse(회원탈퇴) 끝 !");
        return res;
    }
    @Transactional
    @Override
    public int updateUser(CUserDTO uDTO) throws Exception {
        log.info("CMypageService" + " : updateUser(회원정보 수정) 시작 !");
        // 회원정보 수정 성공: 1, 실패 0
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CUserDTO();
        }
        log.info("회원정보 수정 시작");
        int success = cMypageMapper.updateUser(uDTO);

        //db 데이터가 수정되었다면,
        if(success > 0) {
            res = 1;
            log.info("회원정보 수정 완료");
        }else {
            res = 0;
            log.info("회원정보 수정 실패");
        }

        log.info("CMypageService" + " : updateUser(회원정보 수정) 끝 !");
        return res;
    }

    @Override
    public int passwordChange(CUserDTO uDTO) throws Exception {
        log.info("CMypageService" + " : passwordChange(비밀번호 수정) 시작 !");
        // 회원정보 수정 성공: 1, 실패 0
        int res = 0;
        CUserDTO rDTO = null;
        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CUserDTO();
        }

        // 입력받은 현재 비밀번호와 사용중인 비밀번호가 일치하는지 조회
        // 일치 : Y
        rDTO = new CUserDTO();
        rDTO = cMypageMapper.getPasswordCheckExists(uDTO);
        log.info("입력 비밀번호 확인 결과 true(일치), false(불일치): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));

        // 조회한 비밀번호가 일치할 경우 비밀번호 변경진행
        if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
            log.info("조회한 비밀번호가 일치하여 비밀번호 변경 진행함");

            // 같은 비밀번호로 변경 방지를 위해 DB에서 데이터를 조회
            rDTO = null;
            rDTO = new CUserDTO();
            log.info("변경비밀번호 중복확인 실행");
            rDTO = cMypageMapper.getPasswordExists(uDTO);
            log.info("변경비밀번호 중복확인 결과 true(중복), false(중복아님): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));
            //비밀번호가 중복일 경우, 결과값을 2로 변경하고 더이상 작업을 진행하지않음
            if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
                res=2;
                log.info("비밀번호가 중복이므로 비밀번호 변경 진행하지 않음");
            } else {
                log.info("비밀번호가 중복이 아니므로 비밀번호 변경 진행함");
                int success = cMypageMapper.updatePassword(uDTO);
                //db에 데이터가 등록되었다면,
                if(success > 0) {
                    res = 1;
                    log.info("비밀번호 변경 완료");
                }else {
                    res = 0;
                    log.info("비밀번호 변경 실패");
                }
            }
        }else {
            res=3;
            log.info("조회한 비밀번호가 일치하지 않아 비밀번호 변경 하지않음");
        }

        log.info("CMypageService" + " : passwordChange(비밀번호 수정) 끝 !");
        return res;
    }
}
