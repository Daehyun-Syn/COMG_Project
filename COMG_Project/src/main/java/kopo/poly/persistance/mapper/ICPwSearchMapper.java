package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICPwSearchMapper {

    // 아이디 찾기
    CUserDTO IDsearch(CUserDTO oDTO) throws Exception;

    // 비밀번호 변경
    int PWupdate(CUserDTO uDTO) throws Exception;

}
