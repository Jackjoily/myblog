package code.jack.myblog.dto;

import lombok.Data;

@Data
public class QuestionQueryDto {
    private String search;
    private Integer page;
    private Integer size;
}
