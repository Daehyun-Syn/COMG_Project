package kopo.poly.service.impl;

import com.google.gson.Gson;
import kopo.poly.dto.ScheduleDTO;
import kopo.poly.dto.ScheduleReqDTO;
import kopo.poly.persistance.mapper.FullCalenderMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("FullCalenderService")
public class FullCalenderService  {

    FullCalenderMapper fullCalenderMapper;

    public FullCalenderService(FullCalenderMapper fullCalenderMapper) { this.fullCalenderMapper = fullCalenderMapper; }

    public List<ScheduleDTO> getScheduleList(ScheduleReqDTO reqDTO) {
        return fullCalenderMapper.selectAssignMentList(reqDTO);
    }

}
