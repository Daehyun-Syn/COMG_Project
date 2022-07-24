package kopo.poly.service.impl;

import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICPwSearchMapper;
import kopo.poly.service.ICPwSearchService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CPwSearchService")
public class CPwSearchService implements ICPwSearchService {

    private final ICPwSearchMapper cPwSearchMapper;


    @Autowired
    public CPwSearchService(ICPwSearchMapper cPwSearchMapper) {
        this.cPwSearchMapper = cPwSearchMapper;
    }


    @Override
    public int PWupdate(CUserDTO oDTO) throws Exception {
        log.info("CPwSearchService : PWupdate 시작 !");
        int res = 0;
        if(oDTO==null) {
            oDTO = new CUserDTO();
        }
        log.info("변경될(업데이트문) 임시비밀번호 :" + oDTO.getUser_password());
        String change_password = CmmUtil.nvl(oDTO.getUser_password());

        res = cPwSearchMapper.PWupdate(oDTO);


        if(res == 1){
            log.info("CPwSearchService : PWupdate(비밀번호 변경 성공)");

        }else if(res ==0){

            log.info("CPwSearchService : PWupdate(비밀번호 변경 실패)");
        }

        log.info("CPwSearchService : PWupdate 끝 ! ");
        return res;

    }
    @Override
    public int IDSearch(CUserDTO oDTO) throws Exception {
        int res = 0;

        if(oDTO==null) {
            oDTO = new CUserDTO();
        }

        log.info("컨트롤러에서 받아온 아이디 :" + oDTO.getUser_id());

        CUserDTO nDTO = new CUserDTO();

        log.info("아이디가 일치하는지 확인");
        nDTO = cPwSearchMapper.IDsearch(oDTO);

        if(nDTO ==null) {
            res = 0;
            log.info("CPwSearchService : IDSearch(없는 아이디 입니다)");
        } else {
            res = 1;
            log.info("CPwSearchService : IDSearch(일치하는 아이디가 있습니다)");
        }

        return res;
    }

}
