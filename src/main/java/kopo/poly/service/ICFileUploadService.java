package kopo.poly.service;

import kopo.poly.dto.CFileDTO;

public interface ICFileUploadService {


    // 파일 정보를 DB 저장
    int InsertFileInfo(CFileDTO fDTO) throws Exception;

    // 파일 상세 정보를 DB 저장
    int InsertFileMoreInfo(CFileDTO gDTO) throws Exception;

    CFileDTO FileSeqSearch(CFileDTO uDTO) throws Exception;
}
