package org.paktitucci.fineDust.model;

public class FineDustInfo {

    private String locationName;

    private String fineDustStatus;

    private String fineDustValue;


    public FineDustInfo() {

    }

    public FineDustInfo(String locationName) {
        this.locationName = locationName;
    }


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFineDustStatus() {
        return fineDustStatus;
    }

    public void setFineDustStatus(String fineDustStatus) {
        this.fineDustStatus = fineDustStatus;
    }

    public String getFineDustValue() {
        return fineDustValue;
    }

    public void setFineDustValue(String fineDustValue) {
        this.fineDustValue = fineDustValue;
    }

    @Override
    public String toString() {
        return "FineDustInfo{" +
                "locationName='" + locationName + '\'' +
                ", fineDustStatus='" + fineDustStatus + '\'' +
                ", fineDustValue='" + fineDustValue + '\'' +
                '}';
    }
}
