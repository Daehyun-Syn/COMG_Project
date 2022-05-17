package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CBoardDTO {
    // BOARD_INFO
    private String board_contents;
    private String board_writer;
    private String board_reg_user;
    private String fk_user_seq;
    private String fk_group_seq;
    private String fk_group_user_seq;
    private String file_seq;
    private String board_seq;

    // COMMENT_INFO
    private String comment_contents;
    private String comment_writer;
    private String comment_reg_user;
    private String fk_board_seq;

    //etc
    private String writer_name;
    private String board_writer_profile;
    private String board_file_path;
    private String board_file_name;
    private List<CCommentDTO> reviews;
}
