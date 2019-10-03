package code.jack.myblog.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESION_NOT_FOUNF(2001, "你找的问题不在了"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论回复"),
    NO_LOGIN(2003, "当前操作需要，请登录之后进行尝试"),
    SYS_ERRO(2004, "系统出现异常了"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或者不存在"),
    COMMENT_NOT_FUND (2006,"评论不存在了，要不换个试试"),
    COMMENT_CONTENT_EMPTY(2007,"评论内容不能为空");
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
