package kopo.poly.service.impl;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CUserDTO;
import kopo.poly.persistance.mapper.ICFileMapper;
import kopo.poly.persistance.mapper.ICIdSearchMapper;
import kopo.poly.service.ICFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("CFileUploadService")
public class CFileUploadService implements ICFileUploadService {

    private final ICFileMapper cFileMapper;

    @Autowired
    public CFileUploadService(ICFileMapper cFileMapper) {
        this.cFileMapper = cFileMapper;
    }


    @Override
    public int InsertFileInfo(CFileDTO fDTO) throws Exception {
        log.info("CFileUploadService : InsertFileInfo 시작 !");
        // 파일정보 등록 성공 : 1,
        int res = 0;
        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (fDTO == null) {
            fDTO = new CFileDTO();
        }
        //회원가입
        log.info("파일정보 DB등록(InsertFileInfo) 시작");
        int success = cFileMapper.InsertFileInfo(fDTO);

        //db에 데이터가 등록되었다면,
        if(success > 0) {
            res = 1;
            log.info("파일정보 DB등록(InsertFileInfo) 완료");
        }else {
            res = 0;
        }
        return res;
    }

    @Override
    public int InsertFileMoreInfo(CFileDTO gDTO) throws Exception {
        log.info("CFileUploadService : InsertFileMoreInfo 시작 !");
        // 파일정보 등록 성공 : 1,
        int res = 0;
        //controller에서 값이 정상적으로 못넘어오는 경우를 대비하기위해 사용함
        if (gDTO == null) {
            gDTO = new CFileDTO();
        }
        //회원가입
        log.info("파일정보 DB등록(InsertFileMoreInfo) 시작");
        int success = cFileMapper.InsertFileMoreInfo(gDTO);

        //db에 데이터가 등록되었다면,
        if(success > 0) {
            res = 1;
            log.info("파일정보 DB등록(InsertFileMoreInfo) 완료");
        }else {
            res = 0;
        }
        return res;
    }

    @Override
    public CFileDTO FileSeqSearch(CFileDTO uDTO) throws Exception {

        log.info("CFileUploadService : FileSeqSearch 시작 ");
        // res = 1 성공, 0 실패
        if(uDTO == null) {
            uDTO = new CFileDTO();
            log.info(" 컨트롤러에서 받은 uDTO가 NULL이라 CFileUploadService에서 메모리에 올림");
        }
        CFileDTO nDTO = null;
        nDTO = new CFileDTO();
        nDTO = cFileMapper.FileSeqSearch(uDTO);

        if(uDTO.getFile_seq() == null) {
            log.info("Mapper에서 로직 처리 결과 아이디 찾기가 제대로 실행되지 않음.");
        }else {
            log.info("Mapper에서 로직 처리 결과 아이디 찾기 성공");
        }

        log.info("CIdSearchService : IDSearch 끝 ");
        return nDTO;
    }
}
