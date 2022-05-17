package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CAssignmentDTO {

    //ASSIGNMENT_INFO 테이블
    private String assignment_room_seq;
    private String assignment_room_name;
    private String assignment_room_creater;
    private String assignment_room_contents;
    private String assignment_deadline;
    private String assignment_message_time;
    private String fk_group_seq;
    private String file_seq;
    private String assignment_profile_seq;
    private String assignment_regdate;

    //ASSIGNMENT_SEND_INFO 테이블
    private String assignment_seq;
    private String assignment_name;
    private String assignment_contents;
    private String assignment_user_name;
    private String assignment_feedback;
    private String fk_assignment_room_seq;
    private String fk_user_seq;
    private String fk_group_user_seq;
    private String assignment_send_regdate;
    private String user_seq;
    private String user_id;
    private String user_name;
    private String student_id;
    private String server_file_path;

    // etc

    private String assignment_file_path;
    private String assignment_profile_path;
    private long diffrence_deadline;
    private int assignment_count;
    private int assignment_avg;
    private String assignment_start_date;
    private String assignment_file_name;

    //Messgae
    private String covid_message_seq;
    private String covid_message_contents;
    private String covid_message_write;
    private String cnt_work;
    private String download_file_path;
    private String server_file_name;
    private String origin_file_name;


}
