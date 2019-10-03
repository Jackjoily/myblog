package code.jack.myblog.enums;

public enum NotificationStausEnum {
    UNREAD(0),READ(1);
    private int status;

    NotificationStausEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
