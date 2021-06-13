package com.zealoit.eqz.List;

public class PastbookingList {
    private String businessName;
    private int providerId;
    private String providerTitele;
    private String queueNumber;
    private String date;
    private String serviceName;
    private int status;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId){
        this.providerId = providerId;
    }

    public String getProviderTitele() {
        return providerTitele;
    }

    public void setProviderTitele(String providerTitele){
        this.providerTitele = providerTitele;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber){
        this.queueNumber = queueNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }
}
