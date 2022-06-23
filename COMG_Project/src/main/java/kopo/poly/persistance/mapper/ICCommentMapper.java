package kopo.poly.persistance.mapper;


import kopo.poly.dto.CBoardDTO;
import kopo.poly.dto.CCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICCommentMapper {

    int InsertCommentInfo(CCommentDTO cDTO) throws Exception;

    List<CCommentDTO> getCommentList(CCommentDTO cDTO) throws Exception;

    int UpdateCommentInfo(CCommentDTO cDTO) throws Exception;

    int DeleteComment(CCommentDTO cDTO) throws Exception;
}
