package kopo.poly.service;

import kopo.poly.dto.CGroupDTO;

public interface IKakaoService {
    String sendKaKao(CGroupDTO pDTO)throws Exception;
}
