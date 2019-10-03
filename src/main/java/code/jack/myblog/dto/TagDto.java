package code.jack.myblog.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDto {
    private boolean first;
    private String categoryName;
    private List<String> tags;
}
