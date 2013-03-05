package com.oocl.inittools.perfrpt.common;

public class Model extends AvgModel {

    public Model() {

    }

    /**
     * Sample values:
     * 
     * @param requestName searchJobCost
     * @param hitCount 50
     * @param maxElapsedTime 1000
     * @param minElapsedTime 10
     * @param avgElapsedTime 100
     * @param totalElapsedTime 500
     * @param requestDate 2012-01-12
     * @param type IPS; IPS_BK
     */
    public Model(String requestName, String hitCount, String maxElapsedTime, String minElapsedTime,
            String avgElapsedTime, String totalElapsedTime, String requestDate, String type) {
        super();
        this.requestName = requestName;
        this.hitCount = hitCount;
        this.maxElapsedTime = maxElapsedTime;
        this.minElapsedTime = minElapsedTime;
        this.avgElapsedTime = avgElapsedTime;
        this.totalElapsedTime = totalElapsedTime;
        this.requestDate = requestDate;
        this.type = type;
    }

    // public String requestName;

    public String requestDate;

    // public String hitCount;

    public String maxElapsedTime;

    public String minElapsedTime;

    // public String avgElapsedTime;

    // public String totalElapsedTime;

    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }

    public String getMaxElapsedTime() {
        return maxElapsedTime;
    }

    public void setMaxElapsedTime(String maxElapsedTime) {
        this.maxElapsedTime = maxElapsedTime;
    }

    public String getMinElapsedTime() {
        return minElapsedTime;
    }

    public void setMinElapsedTime(String minElapsedTime) {
        this.minElapsedTime = minElapsedTime;
    }

    public String getAvgElapsedTime() {
        return avgElapsedTime;
    }

    public void setAvgElapsedTime(String avgElapsedTime) {
        this.avgElapsedTime = avgElapsedTime;
    }

    public String getTotalElapsedTime() {
        return totalElapsedTime;
    }

    public void setTotalElapsedTime(String totalElapsedTime) {
        this.totalElapsedTime = totalElapsedTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getRequestDate());
        sb.append(",");
        sb.append(this.getRequestName());
        sb.append(",");
        sb.append(this.getHitCount());
        sb.append(",");
        sb.append(this.getMaxElapsedTime());
        sb.append(",");
        sb.append(this.getMinElapsedTime());
        sb.append(",");
        sb.append(this.getAvgElapsedTime());
        sb.append(",");
        sb.append(this.getTotalElapsedTime());
        sb.append(",");
        sb.append(this.getType());

        return sb.toString();

    }

    public static String toHeaderString() {
        StringBuilder sb = new StringBuilder();

        sb.append("RequestDate");
        sb.append(",");
        sb.append("RequestName");
        sb.append(",");
        sb.append("HitCount");
        sb.append(",");
        sb.append("MaxElapsedTime");
        sb.append(",");
        sb.append("MinElapsedTime");
        sb.append(",");
        sb.append("AvgElapsedTime");
        sb.append(",");
        sb.append("TotalElapsedTime");
        sb.append(",");
        sb.append("Type");

        return sb.toString();

    }

    // public String toString() {
    // StringBuilder sb = new StringBuilder();
    //
    // sb.append("RequestDate-> " + this.getRequestDate());
    // sb.append(" | ");
    // sb.append("RequestName-> " + this.getRequestName());
    // sb.append(" | ");
    // sb.append("HitCount-> " + this.getHitCount());
    // sb.append(" | ");
    // sb.append("MaxElapsedTime-> " + this.getMaxElapsedTime());
    // sb.append(" | ");
    // sb.append("MinElapsedTime-> " + this.getMinElapsedTime());
    // sb.append(" | ");
    // sb.append("AvgElapsedTime-> " + this.getAvgElapsedTime());
    // sb.append(" | ");
    // sb.append("TotalElapsedTime-> " + this.getTotalElapsedTime());
    // sb.append(" | ");
    // sb.append("Type-> " + this.getType());
    //
    // return sb.toString();
    //
    // }

    public String getKey() {

        return this.getRequestName() + "-" + this.getRequestDate();

    }

}
