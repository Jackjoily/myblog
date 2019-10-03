package code.jack.myblog.enums;

public enum CommentTypeEnums {
    COMMENT(1),
    QUESTION(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    CommentTypeEnums(Integer type) {
        this.type = type;
    }

    public static boolean isExistsType(Integer type) {
        for (CommentTypeEnums value : CommentTypeEnums.values()) {
            if (type == value.type) {
                return true;
            }
        }
        return false;
    }
}
