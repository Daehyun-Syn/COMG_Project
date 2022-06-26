package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICIdSearchMapper {

    CUserDTO IDsearch(CUserDTO oDTO) throws Exception;
}
