package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CUserDTO {
    private String user_id;
    private String user_password;
    private String user_name;
    private String student_id;
    private String user_seq;
    private String exists_yn;
    private String file_seq;
    private String password_check;
}
