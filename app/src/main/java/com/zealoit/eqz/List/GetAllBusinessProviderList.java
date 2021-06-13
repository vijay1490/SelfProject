package com.zealoit.eqz.List;

public class GetAllBusinessProviderList {

    private int business_id;
    private String business_name;
    private int queueCount;
    private String business_area;
    private int status;

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id){
        this.business_id = business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name){
        this.business_name = business_name;
    }

    public int getQueueCount() {
        return queueCount;
    }

    public void setQueueCount(int queueCount){
        this.queueCount = queueCount;
    }

    public String getBusiness_area() {
        return business_area;
    }

    public void setBusiness_area(String business_area){
        this.business_area = business_area;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }
}
