package org.example.model;

/**
 * 功能：实体层--WEB日志类
 *     参考如下的日志格式：（用户访问ip地址   用户ID   客户ID   请求时间
 * 请求方法   请求页面  请求协议  返回的状态码  发送的页面字节数
 * 从什么页面跳转过来 用户使用的客户端信息）
 *
 */
public class WebLog {
    //远程地址
    private String remote_addr;
    //用户信息
    private String remote_user;
    private String remote_time;
    private String remote_method;
    private String remote_page;
    private String remote_http;
    private String request_status;
    private String send_bytes;
    private String user_agent;
    private String http_refers;
    private boolean validate;

    public String getRemote_addr() {
        return remote_addr;
    }

    public void setRemote_addr(String remote_addr) {
        this.remote_addr = remote_addr;
    }

    public String getRemote_user() {
        return remote_user;
    }

    public void setRemote_user(String remote_user) {
        this.remote_user = remote_user;
    }

    public String getRemote_time() {
        return remote_time;
    }

    public void setRemote_time(String remote_time) {
        this.remote_time = remote_time;
    }

    public String getRemote_method() {
        return remote_method;
    }

    public void setRemote_method(String remote_method) {
        this.remote_method = remote_method;
    }

    public String getRemote_page() {
        return remote_page;
    }

    public void setRemote_page(String remote_page) {
        this.remote_page = remote_page;
    }

    public String getRemote_http() {
        return remote_http;
    }

    public void setRemote_http(String remote_http) {
        this.remote_http = remote_http;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getSend_bytes() {
        return send_bytes;
    }

    public void setSend_bytes(String send_bytes) {
        this.send_bytes = send_bytes;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getHttp_refers() {
        return http_refers;
    }

    public void setHttp_refers(String http_refers) {
        this.http_refers = http_refers;
    }

    @Override
    public String toString() {
        return "WebLog{" +
                "remote_addr='" + remote_addr + '\'' +
                ", remote_user='" + remote_user + '\'' +
                ", remote_time='" + remote_time + '\'' +
                ", remote_method='" + remote_method + '\'' +
                ", remote_page='" + remote_page + '\'' +
                ", remote_http='" + remote_http + '\'' +
                ", request_status='" + request_status + '\'' +
                ", send_bytes='" + send_bytes + '\'' +
                ", user_agent='" + user_agent + '\'' +
                ", http_refers='" + http_refers + '\'' +
                ", validate=" + validate +
                '}';
    }
}
