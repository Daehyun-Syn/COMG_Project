package kopo.poly.service;

import kopo.poly.dto.CFileDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.dto.CUserDTO;

import java.util.List;


public interface ICMypageService {

    CFileDTO getFile(CFileDTO pDTO) throws Exception;

    CUserDTO getUserInfo(CUserDTO iDTO) throws Exception;

    int deleteUser(CUserDTO nDTO) throws Exception;

    int updateUser(CUserDTO uDTO) throws Exception;

    int passwordChange(CUserDTO uDTO) throws Exception;
 }

