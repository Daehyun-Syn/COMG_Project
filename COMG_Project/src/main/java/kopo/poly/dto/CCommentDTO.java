package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CCommentDTO {

    private String comment_seq;
    private String comment_contents;
    private String comment_writer;
    private String comment_date;
    private String comment_reg_date;
    private String comment_reg_user;
    private String fk_board_seq;
    private String fk_user_seq;
    private String fk_group_seq;
    private String fk_group_user_seq;


    //etc
    private String comment_writer_name;
    private String comment_writer_profile;

}
