package kopo.poly.service.impl;

import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICIdSearchMapper;
import kopo.poly.service.ICIdSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CIdSearchService")
public class CIdSearchService implements ICIdSearchService {

    private final ICIdSearchMapper cIdSearchMapper;

    @Autowired
    public CIdSearchService(ICIdSearchMapper cIdSearchMapper) {
        this.cIdSearchMapper = cIdSearchMapper;
    }


    @Override
    public CUserDTO IDSearch(CUserDTO uDTO) throws Exception {
        
        log.info("CIdSearchService : IDSearch 시작 ");
        // res = 1 성공, 0 실패
        if(uDTO == null) {
            uDTO = new CUserDTO();
            log.info(" 컨트롤러에서 받은 uDTO가 NULL이라 CIdSearchService에서 메모리에 올림");
        }
        CUserDTO nDTO = null;
        nDTO = new CUserDTO();
        nDTO = cIdSearchMapper.IDsearch(uDTO);

        if(uDTO.getUser_id() == null) {
            log.info("Mapper에서 로직 처리 결과 아이디 찾기가 제대로 실행되지 않음.");
        }else {
            log.info("Mapper에서 로직 처리 결과 아이디 찾기 성공");
        }

        log.info("CIdSearchService : IDSearch 끝 ");
        return nDTO;
    }
}
