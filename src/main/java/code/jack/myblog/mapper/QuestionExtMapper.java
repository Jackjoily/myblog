package code.jack.myblog.mapper;

import code.jack.myblog.dto.QuestionQueryDto;
import code.jack.myblog.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incview(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
    int countBySearch(QuestionQueryDto questionQueryDto);
    List<Question> selectBySearch(QuestionQueryDto questionQueryDto);
}