package org.syy.rz.for51.entity;

/**
 * Created by Administrator on 2015/4/3.
 */
public class Position {

    private String positionName;
    private String company;
    private String workPlace;
    private String publishDate;
    private String refreshDate;
    private String endDate;
    private boolean canFresh;

    public Position() {
    }

    public Position(String positionName, String company, String workPlace, String publishDate, String refreshDate, String endDate, boolean canFresh) {
        this.positionName = positionName;
        this.company = company;
        this.workPlace = workPlace;
        this.publishDate = publishDate;
        this.refreshDate = refreshDate;
        this.endDate = endDate;
        this.canFresh = canFresh;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public boolean isCanFresh() {
        return canFresh;
    }

    public void setCanFresh(boolean canFresh) {
        this.canFresh = canFresh;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + positionName + '\'' +
                ", company='" + company + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", refreshDate='" + refreshDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", canFresh=" + canFresh +
                '}';
    }
}
