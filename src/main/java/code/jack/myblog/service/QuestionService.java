package code.jack.myblog.service;

import code.jack.myblog.dto.PageDto;
import code.jack.myblog.dto.QuestionDto;
import code.jack.myblog.dto.QuestionQueryDto;
import code.jack.myblog.exception.CustomizeErrorCode;
import code.jack.myblog.exception.CustomizeException;
import code.jack.myblog.mapper.QuestionExtMapper;
import code.jack.myblog.mapper.QuestionMapper;
import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.Question;
import code.jack.myblog.model.QuestionExample;
import code.jack.myblog.model.User;
import code.jack.myblog.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

    public PageDto getQuestionDtoList(String search,Integer page, Integer size) {
        //page
        QuestionQueryDto questionQueryDto = new QuestionQueryDto();

        if(StringUtils.isNotBlank(search)){
            String[] split = StringUtils.split(search ," ");
             search = Arrays.stream(split).collect(Collectors.joining("|"));
            questionQueryDto.setSearch(search);

        }
        int offset = size * (page - 1);
        questionQueryDto.setPage(offset);
        questionQueryDto.setSize(size);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionExtMapper.selectBySearch(questionQueryDto );
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageDto<QuestionDto> pageDto = new PageDto();
        if (questionList != null) {
            for (Question question : questionList) {
                QuestionDto dto = new QuestionDto();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andIdEqualTo(question.getCreator());
                User user = userMapper.selectByExample(userExample).get(0);
                BeanUtils.copyProperties(question, dto);
                dto.setUser(user);
                questionDtoList.add(dto);
            }
            pageDto.setData(questionDtoList);

            int totalcount =questionExtMapper.countBySearch(questionQueryDto);
            pageDto.setPagein(totalcount, page, size);
        } else {
            throw new CustomizeException(CustomizeErrorCode.QUESION_NOT_FOUNF);
        }
        return pageDto;
    }

    public PageDto getPageDtoListByCreator(int  id, Integer page, Integer size) {
        //page
        int offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(page, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageDto<QuestionDto> pageDto = new PageDto<>();
        for (Question question : questionList) {
            QuestionDto dto = new QuestionDto();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            User user = userMapper.selectByExample(userExample).get(0);
            BeanUtils.copyProperties(question, dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        pageDto.setData(questionDtoList);
        QuestionExample qexample = new QuestionExample();
        qexample.createCriteria().andCreatorEqualTo(id);
        int totalcount = (int) questionMapper.countByExample(qexample);
        pageDto.setPagein(totalcount, page, size);
        return pageDto;
    }
    public PageDto getPageDtoListByTitle(String title, Integer page, Integer size) {
        //page
        int offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andTitleLike(title);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(page, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageDto<QuestionDto> pageDto = new PageDto<>();
        for (Question question : questionList) {
            QuestionDto dto = new QuestionDto();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            User user = userMapper.selectByExample(userExample).get(0);
            BeanUtils.copyProperties(question, dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        pageDto.setData(questionDtoList);
        int totalcount = (int) questionMapper.countByExample(questionExample);
        pageDto.setPagein(totalcount, page, size);
        return pageDto;
    }
    public QuestionDto getQuestionDtoById(Integer id) {
        Question q = questionMapper.selectByPrimaryKey(id);
        if (q == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESION_NOT_FOUNF);
        } else {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(q.getCreator());
            User user = userMapper.selectByExample(userExample).get(0);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            questionDto.setUser(user);
            return questionDto;
        }

    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            questionMapper.insertSelective(question);
        } else {
            Question q = new Question();
            q.setGmtModify(System.currentTimeMillis());
            q.setTitle(question.getTitle());
            q.setDescription(question.getDescription());
            q.setTag(question.getTag());
            QuestionExample qq = new QuestionExample();
            qq.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(q, qq);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESION_NOT_FOUNF);
            }
        }

    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incview(question);
    }

    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        if (StringUtils.isBlank(questionDto.getTag())) {
            return new ArrayList<>();
        }
        String[] split = StringUtils.split(questionDto.getTag(), ",");
        String regexpTag = Arrays.stream(split).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDto> collect = questions.stream().map(q -> {
                    QuestionDto questionDto1 = new QuestionDto();
                    BeanUtils.copyProperties(q, questionDto1);
                    return questionDto1;
                }
        ).collect(Collectors.toList());
        return collect;
    }
}
