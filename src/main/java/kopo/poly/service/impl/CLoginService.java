package kopo.poly.service.impl;

import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICLoginMapper;
import kopo.poly.service.ICLoginService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CloginService")
public class CLoginService implements ICLoginService {

    private final ICLoginMapper cloginMapper;

    @Autowired
    public CLoginService(ICLoginMapper cloginMapper) {
        this.cloginMapper = cloginMapper;
    }

    @Override
    public int Logincheck(CUserDTO uDTO) throws Exception {

        log.info("CjoinService : Logincheck(로그인 로직) 시작 !");
        
        // 로그인 성공 : 1, 실패 : 0
        int res = 0;

        // DTO가 null값으로 넘어오는 경우가 있어 null처리 해줌
        if(uDTO == null) {
            uDTO = new CUserDTO();
            log.info("uDTO를 강제로 메모리에 올림");
        }

        CUserDTO mDTO = cloginMapper.Logincheck(uDTO);

        // DTO가 null값으로 넘어오는 경우가 있어 null처리 해줌
        if(mDTO == null) {
            mDTO = new CUserDTO();
            log.info("mDTO를 강제로 메모리에 올림");
        }

        if(CmmUtil.nvl(mDTO.getUser_id()).length()>0) {
            res = 1;
            log.info("CjoinService : 로그인 성공 !");
        }else {
            res = 0;
            log.info("CjoinService : 로그인 실패 !");
        }
        log.info("CjoinService : Logincheck(로그인 로직) 끝 !");
        return res;
    }

    @Override
    public CUserDTO SelectUserInfo(CUserDTO uDTO) throws Exception {
        log.info("CjoinService : SelectUserInfo(로그인이 성공하여 회원정보 가져오는 로직 실행! )");
        if(uDTO == null) {
            uDTO = new CUserDTO();
            log.info("mDTO를 강제로 메모리에 올림");
        }

        CUserDTO mDTO = cloginMapper.Logincheck(uDTO);
        
        log.info("CjoinService : SelectUserInfo(로그인이 성공하여 회원정보 가져오는 로직 끝! )");
        return mDTO;
    }
}
