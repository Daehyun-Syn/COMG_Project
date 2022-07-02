package kopo.poly.service;


import kopo.poly.dto.CAssignmentDTO;

import java.util.List;

public interface ICAssignmentService {

  int InsertAssignmentInfo(CAssignmentDTO rDTO) throws Exception;

  List<CAssignmentDTO> getAssignmentList(CAssignmentDTO bDTO) throws Exception;


  CAssignmentDTO getAssignmentInfo(CAssignmentDTO bDTO) throws Exception;

  CAssignmentDTO getAssignmentCount(CAssignmentDTO sDTO) throws Exception;

  int insertAssignmentSend(CAssignmentDTO aDTO) throws Exception;

  List<CAssignmentDTO> AssingSendUserList(CAssignmentDTO pDTO) throws Exception;

  int updateFeedback(CAssignmentDTO pDTO) throws Exception;

  List<CAssignmentDTO> getAssignment(CAssignmentDTO pDTO) throws Exception;

    CAssignmentDTO getFeedBack(CAssignmentDTO sDTO) throws Exception;
}

