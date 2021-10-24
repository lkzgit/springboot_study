package com.demo.chat.utils;

import com.demo.chat.pojo.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @version v1.0
 * @ClassName: MessageUtils
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
public class MessageUtils {

    /*
     * 系统消息格式：{"isSystem":true,"fromName":null,"message","你好"}
     * 推送给某一个的消息格式：{"isSystem":true,"fromName":"张三","message",["李四","王五"]}
     */
    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {
        try {
            ResultMessage result = new ResultMessage();
            result.setIsSystem(isSystemMessage);
            result.setMessage(message);
            if(fromName != null) {
                result.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
