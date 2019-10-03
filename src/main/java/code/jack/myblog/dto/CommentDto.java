package code.jack.myblog.dto;

import code.jack.myblog.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer likeCount;
    private String content;
    private User user;
    private Integer commentCount;

}
