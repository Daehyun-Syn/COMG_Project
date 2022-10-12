package kopo.poly.persistance.mapper;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ICGroupMapper {

	//회원 가입 전 중복 체크하기(DB조회하기)
	CGroupDTO getGroupExists(CGroupDTO pDTO) throws Exception;

	int InsertGroupInfo(CGroupDTO uDTO) throws Exception;

	int InsertGroupUserInfo(CGroupDTO gDTO) throws Exception;
	int InsertFileGroupInfo(CGroupDTO uDTO) throws Exception;
	// 그룹 리스트를 가져옴
	List<CGroupDTO> getGroupList(CGroupDTO gDTO) throws Exception;

	// 그룹 프로필 사진을 가져옴
	List<CFileDTO> getFileList() throws Exception;

	// 그룹 정보를 가져옴
	CGroupDTO getGroupInfo(CGroupDTO nDTO) throws Exception;

	CFileDTO getFilePath(CFileDTO fDTO) throws Exception;

	CGroupDTO SelectGroupUserInfo(CGroupDTO gDTO) throws Exception;

	int DeleteGroupUser(CGroupDTO gDTO) throws Exception;

	CGroupDTO SelectJoinGroup(CGroupDTO jDTO) throws Exception;

	int UpdateGroupInfo(CGroupDTO bDTO) throws Exception;

	int UpdateGroupFileInfo(CGroupDTO bDTO) throws Exception;
}
