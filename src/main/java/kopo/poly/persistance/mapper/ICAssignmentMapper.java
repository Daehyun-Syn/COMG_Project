package kopo.poly.persistance.mapper;


import kopo.poly.dto.CBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICBoardMapper {

    int InsertBoardInfo(CBoardDTO rDTO) throws Exception;

    List<CBoardDTO> getBoardList(CBoardDTO bDTO) throws Exception;

    CBoardDTO getBoardInfo(CBoardDTO bDTO) throws Exception;

    int UpdateBoardInfo(CBoardDTO bDTO) throws Exception;

    int UpdateBoardFileInfo(CBoardDTO bDTO) throws Exception;

    int DeleteBoard(CBoardDTO bDTO) throws Exception;
}
