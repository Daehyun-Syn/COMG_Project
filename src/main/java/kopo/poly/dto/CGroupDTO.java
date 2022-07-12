package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CGroupDTO {

    private String group_seq;
    private String group_name;
    private String group_regid;
    private String group_regdate;
    private String group_introduce;
    private String group_url;
    private String group_password;
    private String exists_yn;
    private String file_seq;
    private String fk_user_seq;
    private String fk_group_seq;
    private String user_id;
    private String group_user_seq;
    private String user_auth;

    //etc
    private String group_profile;
}
