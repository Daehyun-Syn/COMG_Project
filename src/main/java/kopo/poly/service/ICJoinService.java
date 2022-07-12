package kopo.poly.service;

import kopo.poly.dto.CUserDTO;


public interface ICJoinService {

 	int InsertUserInfo(CUserDTO uDTO) throws Exception;

    int IdCheckForAjax(CUserDTO uDTO) throws Exception;

    int UpdateUserInfo(CUserDTO uDTO) throws Exception;

 }

