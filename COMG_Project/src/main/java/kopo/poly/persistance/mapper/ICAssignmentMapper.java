package kopo.poly.persistance.mapper;


import kopo.poly.dto.CAssignmentDTO;
import kopo.poly.dto.CFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICAssignmentMapper {

    int InsertAssignmentInfo(CAssignmentDTO rDTO) throws Exception;

    List<CAssignmentDTO> getAssignmentList(CAssignmentDTO bDTO) throws Exception;

    CAssignmentDTO getAssignmentInfo(CAssignmentDTO bDTO) throws Exception;

    CAssignmentDTO getAssignmentCount(CAssignmentDTO sDTO) throws Exception;
    int insertAssignmentSend(CAssignmentDTO aDTO) throws Exception;

    List<CAssignmentDTO> AssingSendUserList(CAssignmentDTO pDTO) throws Exception;

    List<CAssignmentDTO> AssingSendList(CAssignmentDTO pDTO) throws Exception;

    int updateFeedback(CAssignmentDTO pDTO) throws Exception;

    CAssignmentDTO KakaoSend(CAssignmentDTO pDTO)throws Exception;

    CAssignmentDTO proKakaoSend() throws Exception;

    List<CAssignmentDTO> getAssignment(CAssignmentDTO pDTO) throws Exception;

    CAssignmentDTO getFeedBack(CAssignmentDTO sDTO) throws Exception;

    CFileDTO getFile(CFileDTO pDTO) throws Exception;
}
