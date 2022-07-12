package kopo.poly.service;


import kopo.poly.dto.CUserDTO;

public interface ICIdSearchService {

    CUserDTO IDSearch(CUserDTO uDTO) throws Exception;
}
