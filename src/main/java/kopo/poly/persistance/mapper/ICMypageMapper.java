package kopo.poly.persistance.mapper;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICMypageMapper {

	CUserDTO getUserInfo(CUserDTO iDTO) throws Exception;

	// 그룹 프로필 사진을 가져옴
	CFileDTO getFile(CFileDTO pDTO) throws Exception;

	int deleteUser(CUserDTO uDTO) throws Exception;

	int updateUser(CUserDTO nDTO) throws Exception;

	// 입력받은 현재 비밀번호와 사용중인 비밀번호가 일치하는지 조회함
	CUserDTO getPasswordCheckExists(CUserDTO pDTO) throws Exception;

	// 변경할 비밀번호가 현재 사용중인 비밀번호와 일치하는지
	CUserDTO getPasswordExists(CUserDTO pDTO) throws Exception;

	int updatePassword(CUserDTO nDTO) throws Exception;
}
