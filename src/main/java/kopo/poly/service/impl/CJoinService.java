package kopo.poly.service.impl;

import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICJoinMapper;
import kopo.poly.service.ICJoinService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("CjoinService")
public class CJoinService implements ICJoinService {

    private final ICJoinMapper cjoinMapper;


    @Autowired
    public CJoinService(ICJoinMapper cjoinMapper) {
        this.cjoinMapper = cjoinMapper;
    }

    @Transactional
    @Override
    public int InsertUserInfo(CUserDTO uDTO) throws Exception {

        log.info("CjoinService : InsertUserInfo(회원가입 로직) 시작 !");
        // 회원가입 성공: 1,
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CUserDTO();
        }

        //회원 가입 중복방지를 위해 DB에서 데이터를 조회
        CUserDTO rDTO = cjoinMapper.getUserExists(uDTO);
        log.info("로그인 중복제거 결과 true(중복), false(중복아님): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));
        //중복된 회원 정보가 있는 경우, 결과값을 2로 변경하고 더이상 작업을 진행하지않음
        if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
            res=2;
            //회원가입이 중복이 아니므로 회원가입 진행함
        } else {
            //회원가입
            log.info("회원가입 시작");
            int success = cjoinMapper.InsertUserInfo(uDTO);

            //db에 데이터가 등록되었다면,
            if(success > 0) {
                res=1;
                log.info("회원가입 완료");
            }else {
                res = 0;
            }

        }

        log.info("CjoinService : InsertUserInfo(회원가입 로직) 끝 !");
        return res;

    }


    @Override
    public int IdCheckForAjax(CUserDTO uDTO) throws Exception {

        log.info("CjoinService : IdCheckForAjax(아작스 아이디 중복체크) 시작 !");
        // 중복된 아이디 없음 : 0, 중복된 아이디 있음 : 1
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CUserDTO();
        }

        //회원 가입 중복방지를 위해 DB에서 데이터를 조회
        CUserDTO rDTO = cjoinMapper.getUserExists(uDTO);
        log.info("로그인 중복제거 결과 true(중복), false(중복아님): "+ (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")));

        // 중복된 아이디 없음 : 0, 중복된 아이디 있음 : 1 을 res값에 담아 보내줌.
        if(CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")){
            res = 1;
            log.info("CjoinService : 아이디 중복 ");
        } else {
            res = 0;
            log.info("CjoinService : 아이디 중복되지 않음 ");
        }
        
        log.info("CjoinService : IdCheckForAjax(아작스 아이디 중복체크) 끝 !");
        return res;
    }

    @Transactional
    @Override
    public int UpdateUserInfo(CUserDTO uDTO) throws Exception {

        log.info("CjoinService : UpdateUserInfo(회원정보 수정) 시작 !");
        // 회원정보수정 성공: 1,
        int res = 0;

        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (uDTO == null) {
            uDTO = new CUserDTO();
        }
        
        log.info("회원정보수정  시작");
        int success = cjoinMapper.UpdateUserInfo(uDTO);

        //db 데이터가 수정되었다면,
        if(success > 0) {
            res=1;
            log.info("회원정보수정 완료");
        }else {
            res = 0;
            log.info("회원정보수정 실패");
        }
        

        log.info("CjoinService : UpdateUserInfo(회원정보 수정) 끝 !");
        return res;
    }
}
