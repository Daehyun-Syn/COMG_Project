package kopo.search.service.impl;

import kopo.poly.dto.CGroupDTO;
import kopo.search.persistance.mapper.IGSMapper;
import kopo.search.service.IGSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("GSService")
public class GSService implements IGSService {

    private final IGSMapper gsMapper;


    @Autowired
    public GSService(IGSMapper gsMapper) {
        this.gsMapper = gsMapper;
    }

    @Override
    public List<CGroupDTO> getGroupListForAjax(CGroupDTO gDTO) throws Exception {
        log.info("GSService : getGroupListForAjax 시작");
        List<CGroupDTO> rList = new ArrayList<>();
        rList = gsMapper.getGroupListForAjax(gDTO);

        if(rList == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("GSService : 그룹이 존재하지않아 강제로 메모리에 rlist를 올림!");
            rList = new ArrayList<>();
        }
        else {
            log.info("GSService : 그룹리스트 가져오기 성공");
        }

        log.info("GSService : getGroupListForAjax 끝 !");
        return rList;
    }


    @Override
    public List<CGroupDTO> getAutoGroupName(CGroupDTO gDTO) throws Exception {
        log.info("GSService : getAutoGroupName 시작");
        List<CGroupDTO> rList = new ArrayList<>();
        rList = gsMapper.getAutoGroupName(gDTO);

        if(rList == null) {
            //rlist가 null 일 경우 오류 방지를 위해 강제로 메모리에 LIST 를 올림
            log.info("GSService : 일치하는 그룹이 존재하지않아 강제로 메모리에 rlist를 올림!");
            rList = new ArrayList<>();
        }
        else {
            log.info("GSService : 그룹이름 가져오기 성공");
        }

        log.info("GSService : getAutoGroupName 끝 !");
        return rList;
    }

}
