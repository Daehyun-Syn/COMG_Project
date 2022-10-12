package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KakaoLoginMapper {
    CUserDTO getUserKakao(CUserDTO uDTO);

    int insertUserKakao(CUserDTO uDTO);
}
