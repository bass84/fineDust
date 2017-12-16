package org.paktitucci.fineDust.model;

public class FineDustInfo {

    private String locationName;

    private String fineDustStatus;


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

    @Override
    public String toString() {
        return "FineDustInfo{" +
                "locationName='" + locationName + '\'' +
                ", fineDustStatus='" + fineDustStatus + '\'' +
                '}';
    }
}
