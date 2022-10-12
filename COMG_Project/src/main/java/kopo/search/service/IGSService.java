package kopo.search.service;

import kopo.poly.dto.CGroupDTO;

import java.util.List;

public interface IGSService {

    List<CGroupDTO> getGroupListForAjax(CGroupDTO gDTO) throws Exception;

    List<CGroupDTO> getAutoGroupName(CGroupDTO gDTO) throws Exception;
}
