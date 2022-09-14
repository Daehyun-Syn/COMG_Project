package kopo.poly.service;

import kopo.poly.dto.CCommentDTO;


import java.util.List;


public interface ICCommentService {

    int InsertCommentInfo(CCommentDTO cDTO) throws Exception;

    List<CCommentDTO> getCommentList(CCommentDTO cDTO) throws Exception;

    int UpdateCommentInfo(CCommentDTO cDTO) throws Exception;

    int DeleteComment(CCommentDTO cDTO) throws Exception;

 }

