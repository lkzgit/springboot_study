package com.demo.chat.pojo;

/**
 * @version v1.0
 * @ClassName: Message
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
public class Message {
    private String toName;
    private String message;

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
