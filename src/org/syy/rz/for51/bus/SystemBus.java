package org.syy.rz.for51.bus;

import org.apache.http.client.HttpClient;
import org.jsoup.nodes.Document;
import org.syy.rz.for51.entity.User;

import java.util.Map;

/**
 * 数据总线，共享数据
 * Created by syy on 2015/3/22.
 */
public class SystemBus {

    private SystemBus() {
    }
    private static class SystemBusHandle {
        private static SystemBus instance = new SystemBus();
    }
    public static SystemBus instance() {
        return SystemBusHandle.instance;
    }

    /**当前登陆用户*/
    private User loginUser;
    /**已经登陆的客户端*/
    private HttpClient loginClient;
    /**第一页简历的请求参数*/
    private Map<String, String> firstParams;
    /**其他简历页的请求参数*/
    private Map<String, String> otherParams;
    /**简历总数*/
    private int totalResume;
    /**
     * 获得简历总页码
     * @return
     */
    public int getPageNum() {
        return totalResume%50==0? totalResume/50 : totalResume/50+1;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public HttpClient getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(HttpClient loginClient) {
        this.loginClient = loginClient;
    }

    public Map<String, String> getFirstParams() {
        return firstParams;
    }

    public void setFirstParams(Map<String, String> firstParams) {
        this.firstParams = firstParams;
    }

    public Map<String, String> getOtherParams() {
        return otherParams;
    }

    public void setOtherParams(Map<String, String> otherParams) {
        this.otherParams = otherParams;
    }

    public int getTotalResume() {
        return totalResume;
    }

    public void setTotalResume(int totalResume) {
        this.totalResume = totalResume;
    }
}
