package org.syy.rz.for51.entity;

/**
 * 51job登陆的实体
 * Created by syy on 2015/3/22.
 */
public class User {

    /**用户名*/
    private String name;
    /**组名*/
    private String group;
    /**登陆密码*/
    private String password;


    public User(String name, String group, String password) {
        this.name = name;
        this.group = group;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
