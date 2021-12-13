package com.demo.user.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

/**
 *
 * @description rest接口操作类（spring RestTemplate）
 * <p>
 * HTTP1.1和HTTP1.0相比较而言，最大的区别就是增加了持久连接支持(貌似最新的 http1.0 可以显示的指定 keep-alive),但还是无状态的，或者说是不可以信任的。
 * Spring RestTemplate使用http1.1协议，如果不指定为短连接{headers.set("Connection", "Close");}或指定templateFactory为HttpComponentsClientHttpRequestFactory，
 * 调完接口后会出现java.io.EOFException: null异常
 * @create 2018-02-27 14:15
 **/
public class RestUtil {

    /**
     * get请求，防止json转成xml导致lsit转换失败
     * @param url 地址
     * @param responseType 返回的数据类型
     * @param uriVariables 参数
     * @param <T>
     * @return
     * @throws RestClientException
     */
    public static <T> T getForObject(String url, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        RestTemplate template = getDefaultTemplate();
        HttpEntity entity = new HttpEntity<>( getDefaultHeaders());
        ResponseEntity<T> responseEntity;
        if(uriVariables == null){
            responseEntity = template.exchange(url, HttpMethod.GET, entity, responseType);
        } else {
            responseEntity = template.exchange(url, HttpMethod.GET, entity, responseType, uriVariables);
        }

        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        }
        throw new RuntimeException(url + " get failed , response code is " + responseEntity.getStatusCode());
    }

    public static <T> T getForObject(String url, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables,HttpHeaders headers) {
        RestTemplate template = getDefaultTemplate();
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity;
        if(uriVariables == null){
            responseEntity = template.exchange(url, HttpMethod.GET, entity, responseType);
        } else {
            responseEntity = template.exchange(url, HttpMethod.GET, entity, responseType, uriVariables);
        }

        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        }
        throw new RuntimeException(url + " get failed , response code is " + responseEntity.getStatusCode());
    }

    /**
     * 调用查询接口
     *
     * @param uri           uri接口
     * @param uriParameters uri参数
     * @param responseType  返回值Class
     * @param <T>           返回泛型
     * @return
     */
    public static <T> T getForEntity(String uri, Map<String, Object> uriParameters, HttpHeaders headers, Class<T> responseType) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            //默认数据采用json格式进行传输
            headers = getDefaultHeaders();
        }
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity;
        if (uriParameters == null) {
            responseEntity = template.exchange(uri, HttpMethod.GET, entity, responseType);
        } else {
            responseEntity = template.exchange(uri, HttpMethod.GET, entity, responseType, uriParameters);
        }

        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException(uri + " get failed , response code is " + responseEntity.getStatusCode());
        }

    }

    public static <T> T getForString(String uri, Map<String, String> uriParameters, HttpHeaders headers, Class<T> responseType) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            //默认数据采用json格式进行传输
            headers = getDefaultHeaders();
        }
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity;
        if (uriParameters == null) {
            responseEntity = template.exchange(uri, HttpMethod.GET, entity, responseType);
        } else {
            responseEntity = template.exchange(uri, HttpMethod.GET, entity, responseType, uriParameters);
        }

        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException(uri + " get failed , response code is " + responseEntity.getStatusCode());
        }

    }



    /**
     * 调用查询接口
     *
     * @param uri           uri接口
     * @param uriParameters uri参数
     * @param responseType  返回值Class
     * @param <T>           返回泛型
     * @return
     */
    public static <T> T getForEntity(String uri, Map<String, Object> uriParameters, Class<T> responseType) {
        RestTemplate template = getDefaultTemplate();
        HttpEntity entity = new HttpEntity<>( getDefaultHeaders());
        ResponseEntity<T> responseEntity;
        if(uriParameters == null){
            responseEntity = template.exchange(uri, HttpMethod.GET, entity, responseType);
        } else {
            responseEntity = template.exchange(uri, HttpMethod.GET, entity, responseType, uriParameters);
        }

        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException(uri + " get failed , response code is " + responseEntity.getStatusCode());
        }

    }

    /**
     * 调用新增接口
     *
     * @param uri
     * @param postParameters
     * @param headers
     * @param responseType
     * @param <T>
     * @return
     */
    public static <T> T postForEntity(String uri, MultiValueMap<String, String> postParameters, HttpHeaders headers, Class<T> responseType) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            //默认数据采用json格式进行传输
            headers = getDefaultHeaders();
        }
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
                postParameters, headers);
        ResponseEntity<T> responseEntity = template.postForEntity(uri, requestEntity, responseType);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException(uri + " post failed , response code is " + responseEntity.getStatusCode());
        }

    }

    /**
     * 调用新增接口
     *
     * @param uri            uri接口
     * @param bodyParameters 请求体参数
     * @param uriVariables   uri参数
     * @param headers        请求头，null为默认json
     * @param responseType   返回值Class
     * @param <T>            返回泛型
     * @return
     */
    public static <T> T postForEntity(String uri, Map<String, Object> bodyParameters, Map<String, Object> uriVariables, HttpHeaders headers, Class<T> responseType) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            //默认数据采用json格式进行传输
            headers = getDefaultHeaders();
        }
        HttpEntity<?> requestEntity = new HttpEntity(bodyParameters, headers);
        ResponseEntity<T> responseEntity = template.postForEntity(uri, requestEntity, responseType, uriVariables);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException(uri + " post failed , response code is " + responseEntity.getStatusCode());
        }

    }

    /**
     * 调用新增接口
     *
     * @param uri          uri接口
     * @param headers      请求头，null为默认json
     * @param responseType 返回值Class
     * @param <T>          返回泛型
     * @return
     */
    public static <T> T postForEntity(String uri, Object object, HttpHeaders headers, Class<T> responseType) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            //默认数据采用json格式进行传输
            headers = getDefaultHeaders();
        }
        HttpEntity<?> requestEntity = new HttpEntity(object, headers);
        ResponseEntity<T> responseEntity = template.postForEntity(uri, requestEntity, responseType);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException(uri + " post failed , response code is " + responseEntity.getStatusCode());
        }

    }

    /**
     * 返回默认json的post请求头
     *
     * @return
     */
    private static HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.set("Connection", "Close");
        headers.add("X-Auth-Token", UUID.randomUUID().toString());
        return headers;
    }


    /**
     * 调用修改接口
     *
     * @param uri           uri接口
     * @param uriParameters uri参数
     * @param headers       请求头
     * @return
     */
    public static void putForEntity(String uri, MultiValueMap<String, Object> uriParameters, HttpHeaders headers) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            headers = getDefaultHeaders();
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(uriParameters, headers);
        template.put(uri, requestEntity, uriParameters);
    }

    /**
     * 调用删除接口
     *
     * @param uri        uri接口
     * @param parameters uri参数
     * @param headers    请求头
     * @return
     */
    public static void deleteForEntity(String uri, MultiValueMap<String, Object> parameters, HttpHeaders headers) {
        RestTemplate template = getDefaultTemplate();
        if (headers == null) {
            headers = getDefaultHeaders();
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                parameters, headers);
        template.delete(uri, requestEntity, parameters);
    }

    /**
     * 构造默认的restTemplate
     *
     * @return
     */
    public static RestTemplate getDefaultTemplate() {
        RestTemplate template = new RestTemplate();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return template;
    }

  /*  请求图片
  HttpHeaders headers = new HttpHeaders();
headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
    HttpEntity<String> entity = new HttpEntity<String>(headers);
    ResponseEntity<byte[]> response = restTemplate.exchange(url,HttpMethod.GET, entity, byte[].class);
    byte[] imageBytes = response.getBody();*/

}
