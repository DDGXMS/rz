package org.syy.rz.entity;

/**
 * TODO
 * 
 * @author syy
 * @since 2014-11-26 下午2:59:42
 * @version 1.0
 */
public class Rz {

    private String pipei;
    private String name;
    private String postion;
    private String company;
    private String workPlace;
    private String age;
    private String education;
    private String workYears;
    private String sendTime;

    public Rz(String pipei, String name, String postion, String company, String workPlace,
            String age, String education, String workYears, String sendTime) {

        super();
        this.pipei = pipei;
        this.name = name;
        this.postion = postion;
        this.company = company;
        this.workPlace = workPlace;
        this.age = age;
        this.education = education;
        this.workYears = workYears;
        this.sendTime = sendTime;
    }

    /**
     * @return the pipei
     */
    public String getPipei() {

        return pipei;
    }

    /**
     * @param pipei
     *            the pipei to set
     */
    public void setPipei(String pipei) {

        this.pipei = pipei;
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
     * @return the postion
     */
    public String getPostion() {

        return postion;
    }

    /**
     * @param postion
     *            the postion to set
     */
    public void setPostion(String postion) {

        this.postion = postion;
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
