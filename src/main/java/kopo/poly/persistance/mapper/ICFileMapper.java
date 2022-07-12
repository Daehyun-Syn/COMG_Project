package kopo.poly.persistance.mapper;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICFileMapper {

    CFileDTO FileSeqSearch(CFileDTO nDTO) throws Exception;

    // 파일 정보를 DB 저장
    int InsertFileInfo(CFileDTO fDTO) throws Exception;

    // 파일 상세 정보를 DB 저장
    int InsertFileMoreInfo(CFileDTO gDTO) throws Exception;
}
