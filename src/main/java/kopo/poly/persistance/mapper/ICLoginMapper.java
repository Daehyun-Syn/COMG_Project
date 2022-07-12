package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ICLoginMapper {

	CUserDTO Logincheck(CUserDTO mDTO) throws Exception;

}
