package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ICJoinMapper {

	//회원 가입 전 중복 체크하기(DB조회하기)
	CUserDTO getUserExists(CUserDTO pDTO) throws Exception;

	int InsertUserInfo(CUserDTO uDTO) throws Exception;

	int UpdateUserInfo(CUserDTO uDTO) throws Exception;
}
