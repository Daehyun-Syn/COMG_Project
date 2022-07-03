package kopo.poly.service;


import kopo.poly.dto.CBoardDTO;
import kopo.poly.dto.CFileDTO;

import java.util.List;

public interface ICBoardService {

  int InsertBoardInfo(CBoardDTO rDTO) throws Exception;

  List<CBoardDTO> getBoardList(CBoardDTO bDTO) throws Exception;


  CBoardDTO getBoardInfo(CBoardDTO bDTO) throws Exception;

  int UpdateBoardInfo(CBoardDTO bDTO) throws Exception;

  int DeleteBoard(CBoardDTO bDTO) throws Exception;

  //사용자 파일 path와 이름 가져오기
   List <CFileDTO> getFilePath(CFileDTO tDTO)throws Exception;
}

