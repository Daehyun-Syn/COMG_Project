package kopo.search.persistance.mapper;

import kopo.poly.dto.CGroupDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IGSMapper {
    List<CGroupDTO> getGroupListForAjax(CGroupDTO gDTO) throws Exception;

    List<CGroupDTO> getAutoGroupName(CGroupDTO gDTO) throws Exception;

}
