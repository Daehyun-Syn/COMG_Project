package kopo.poly.service;

import kopo.poly.dto.CMailDTO;

public interface ICMailService {

    int doSendmail(CMailDTO pDTO) throws Exception;

}
