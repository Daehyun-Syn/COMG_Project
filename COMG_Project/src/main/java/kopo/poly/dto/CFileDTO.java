package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CFileDTO {
    // FILE_INFO
    private String file_seq;
    private String file_from_table;
    private String file_code;

    // FILE_MORE_INFO
    private String file_more_seq;
    private String fk_file_seq;
    private String server_file_name;
    private String server_file_path;
    private String origin_file_name;
    private String origin_file_extension;

    //etc
    private String group_seq;

}
