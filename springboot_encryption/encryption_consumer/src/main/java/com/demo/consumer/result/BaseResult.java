package com.demo.consumer.result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName BaseResult.java
 * @Description 返回结果
 * @createTime 2021年12月08日 22:18:00
 */
public class BaseResult implements Serializable {

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private String status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public static BaseResult build(String status, String msg, Object data) {
        return new BaseResult(status, msg, data);
    }

    public static BaseResult build(String status, String msg, Object data,Long total) {
        return new BaseResult(status, msg, data,total);
    }
    public BaseResult(String status, String msg, Object data, Long total) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public static BaseResult ok(Object data) {
        return new BaseResult(data);
    }

    public static BaseResult ok() {
        return new BaseResult(null);
    }

    public BaseResult() {

    }

    public static BaseResult build(String status, String msg) {
        return new BaseResult(status, msg, null);
    }

    public BaseResult(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BaseResult(Object data) {
        this.status = StatusCode.SUCCESSCODE;
        this.msg = StatusCode.SUCCESSCODEMSG;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为BaseResult对象
     *
     * @param jsonData json数据
     * @param clazz BaseResult中的object类型
     * @return
     */
    public static BaseResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, BaseResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").asText(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static BaseResult format(String json) {
        try {
            return MAPPER.readValue(json, BaseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * a
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static BaseResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").asText(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }


}
