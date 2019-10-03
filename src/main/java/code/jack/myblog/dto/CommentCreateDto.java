package code.jack.myblog.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private Integer parentId;
    private String content;
    private Integer type;
}
