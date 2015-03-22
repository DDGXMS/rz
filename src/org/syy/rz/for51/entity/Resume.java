package org.syy.rz.for51.entity;

/**
 * 简历信息
 */
public class Resume {

    /**匹配度*/
    private String matchDegree;
    /**姓名*/
    private String name;
    /**职位*/
    private String position;
    /**申请公司*/
    private String company;
    /**工作地点*/
    private String workPlace;
    /**年龄*/
    private String age;
    /**学历*/
    private String education;
    /**工作年限*/
    private String workYears;
    /**投递时间*/
    private String sendTime;

    public Resume() {
    }

    public Resume(String matchDegree, String name, String position, String company, String workPlace,
                  String age, String education, String workYears, String sendTime) {

        super();
        this.matchDegree = matchDegree;
        this.name = name;
        this.position = position;
        this.company = company;
        this.workPlace = workPlace;
        this.age = age;
        this.education = education;
        this.workYears = workYears;
        this.sendTime = sendTime;
    }

    /**
     * @return the matchDegree
     */
    public String getMatchDegree() {

        return matchDegree;
    }

    /**
     * @param matchDegree
     *            the matchDegree to set
     */
    public void setMatchDegree(String matchDegree) {

        this.matchDegree = matchDegree;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return the position
     */
    public String getPosition() {

        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(String position) {

        this.position = position;
    }

    /**
     * @return the company
     */
    public String getCompany() {

        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(String company) {

        this.company = company;
    }

    /**
     * @return the workPlace
     */
    public String getWorkPlace() {

        return workPlace;
    }

    /**
     * @param workPlace
     *            the workPlace to set
     */
    public void setWorkPlace(String workPlace) {

        this.workPlace = workPlace;
    }

    /**
     * @return the age
     */
    public String getAge() {

        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(String age) {

        this.age = age;
    }

    /**
     * @return the education
     */
    public String getEducation() {

        return education;
    }

    /**
     * @param education
     *            the education to set
     */
    public void setEducation(String education) {

        this.education = education;
    }

    /**
     * @return the workYears
     */
    public String getWorkYears() {

        return workYears;
    }

    /**
     * @param workYears
     *            the workYears to set
     */
    public void setWorkYears(String workYears) {

        this.workYears = workYears;
    }

    /**
     * @return the sendTime
     */
    public String getSendTime() {

        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(String sendTime) {

        this.sendTime = sendTime;
    }

}
