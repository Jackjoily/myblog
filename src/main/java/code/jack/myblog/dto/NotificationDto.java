package code.jack.myblog.dto;

import code.jack.myblog.model.User;
import lombok.Data;

@Data
public class NotificationDto {
    private Integer id;
    private Long gmtCreate;
    private Integer staus;
    private User notifier;
    private String outerTitle;
    private Integer outerId;
    private String  type;
}
