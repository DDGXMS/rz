package org.syy.rz.for51.entity;

/**
 * 51job登陆的实体
 * Created by syy on 2015/3/22.
 */
public class User {

    /**用户名*/
    private String userName;
    /**组名*/
    private String memberName;
    /**登陆密码*/
    private String password;


    public User(String memberName, String userName, String password) {
        this.userName = userName;
        this.memberName = memberName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
