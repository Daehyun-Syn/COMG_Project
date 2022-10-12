package kopo.poly.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ScheduleDTO {

    private int assignmentRoomSeq;
    private String assignmentRoomName;
    private String assignmentRegDate;
    private String assignmentDeadLine;
}
