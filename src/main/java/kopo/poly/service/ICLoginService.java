package kopo.poly.service;

import kopo.poly.dto.CUserDTO;

public interface ICLoginService {

    // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
    int Logincheck(CUserDTO uDTO) throws Exception;
    
    
    // 추후 사용할수 있기 때문에 사용자의 정보를 DTO에 담아온다
    CUserDTO SelectUserInfo(CUserDTO uDTO) throws Exception;
}
