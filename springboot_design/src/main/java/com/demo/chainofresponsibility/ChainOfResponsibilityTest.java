package com.demo.chainofresponsibility;


/**
 * 责任链模式
 * 模式定义： 为请求创建了一个接收者对象的链。
 * 应用场景：
 *      一个请求的处理需要多个对象当中的一个或几个协作处理。
 *  优点:
 *      1.请求的发送者和接受者解耦
 *      2.可以控制执行顺序
 *      3.符合开闭原则和单一职责原则
 *经典案例
 *      1 javax.servlet.Filter
 *      2 javax.servlet.FilterChain
 *
 */
public class ChainOfResponsibilityTest {
    public static void main(String[] args) {

        Request request=new Request.RequestBuilder().frequentOk( true ).loggedOn( true ).build();


        RequestFrequentHandler requestFrequentHandler=new RequestFrequentHandler( new LoggingHandler( null ) );
            //每一步都会进行判断
        if (requestFrequentHandler.process( request )) {

            System.out.println(" 正常业务处理");
        }else{
            System.out.println(" 访问异常");
        }

    }
}
class Request{
    private boolean loggedOn;
    private boolean frequentOk;
    private boolean isPermits;
    private boolean containsSensitiveWords;
    private String  requestBody;

    private Request(boolean loggedOn, boolean frequentOk, boolean isPermits, boolean containsSensitiveWords) {
        this.loggedOn=loggedOn;
        this.frequentOk=frequentOk;
        this.isPermits=isPermits;
        this.containsSensitiveWords=containsSensitiveWords;
    }

    static class RequestBuilder{
        private boolean loggedOn;
        private boolean frequentOk;
        private boolean isPermits;
        private boolean containsSensitiveWords;

        RequestBuilder loggedOn(boolean loggedOn){
            this.loggedOn=loggedOn;
            return this;
        }

        RequestBuilder frequentOk(boolean frequentOk){
            this.frequentOk=frequentOk;
            return this;
        }
        RequestBuilder isPermits(boolean isPermits){
            this.isPermits=isPermits;
            return this;
        }
        RequestBuilder containsSensitiveWords(boolean containsSensitiveWords){
            this.containsSensitiveWords=containsSensitiveWords;
            return this;
        }

        public Request build(){
            Request request=new Request( loggedOn, frequentOk, isPermits, containsSensitiveWords);
            return request;
        }

    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public boolean isFrequentOk() {
        return frequentOk;
    }

    public boolean isPermits() {
        return isPermits;
    }

    public boolean isContainsSensitiveWords() {
        return containsSensitiveWords;
    }
}


abstract class Handler{
    Handler next;

    public Handler(Handler next) {
        this.next=next;
    }

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next=next;
    }

    abstract boolean process(Request request);

}
class RequestFrequentHandler extends Handler{

    public RequestFrequentHandler(Handler next) {
        super( next );
    }

    @Override
    boolean process(Request request) {

        System.out.println("访问频率控制.");
        if (request.isFrequentOk()){
              Handler next=getNext();
              if (null==next){
                  return true;
              }
            if (!next.process( request )) {
                return false;
            }else{
                  return true;
            }
        }
        return false;
    }
}

class LoggingHandler extends Handler{

    public LoggingHandler(Handler next) {
        super( next );
    }

    @Override
    boolean process(Request request) {
        System.out.println(" 登录验证");
        if (request.isLoggedOn()){
            Handler next=getNext();
            if (null==next){
                return true;
            }
            if (!next.process( request )) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
}








