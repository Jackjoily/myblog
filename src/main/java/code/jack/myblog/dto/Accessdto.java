package code.jack.myblog.dto;

import lombok.Data;

@Data
public class Accessdto {
    private String state;
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
