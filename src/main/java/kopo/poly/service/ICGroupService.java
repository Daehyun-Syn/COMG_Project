package kopo.poly.service;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;

import java.util.List;


public interface ICGroupService {

 	int InsertGroupInfo(CGroupDTO uDTO) throws Exception;

    int InsertGroupUserInfo(CGroupDTO gDTO) throws Exception;

    int GroupCheckForAjax(CGroupDTO uDTO) throws Exception;

    CGroupDTO getGroupInfo(CGroupDTO gDTO) throws Exception;

    CFileDTO getFilePath(CFileDTO fDTO) throws Exception;
    // 그룹 목록 + 해당하는 그룹의 대표사진 가져오기
    List<CGroupDTO> getGroupList(CGroupDTO gDTO) throws Exception;
    List<CFileDTO> getFileList() throws Exception;

    CGroupDTO SelectGroupUserInfo(CGroupDTO gDTO) throws Exception;

    int DeleteGroupUser(CGroupDTO gDTO) throws Exception;

    CGroupDTO SelectJoinGroup(CGroupDTO jDTO) throws Exception;

    int UpdateGroupInfo(CGroupDTO bDTO) throws Exception;
}

