package kopo.poly.persistance.mapper;

import kopo.poly.dto.ScheduleDTO;
import kopo.poly.dto.ScheduleReqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FullCalenderMapper {

    List<ScheduleDTO> selectAssignMentList(ScheduleReqDTO reqDTO);
}
