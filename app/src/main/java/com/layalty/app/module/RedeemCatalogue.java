package com.layalty.app.module;

/**
 * Created by 19569 on 6/27/2017.
 */

public class RedeemCatalogue {

    private String Name;
    private String Description__c;
    private String Points__c;
    private String Image_URL__c;
    private String Reward_Product_Name__c;

    public RedeemCatalogue(String Name, String Description__c, String Points__c,
                           String Image_URL__c, String Reward_Product_Name__c) {

        this.Name = Name;
        this.Description__c = Description__c;
        this.Points__c = Points__c;
        this.Image_URL__c = Image_URL__c;
        this.Reward_Product_Name__c = Reward_Product_Name__c;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription__c() {
        return Description__c;
    }

    public void setDescription__c(String description__c) {
        Description__c = description__c;
    }

    public String getPoints__c() {
        return Points__c;
    }

    public void setPoints__c(String points__c) {
        Points__c = points__c;
    }

    public String getImage_URL__c() {
        return Image_URL__c;
    }

    public void setImage_URL__c(String image_URL__c) {
        Image_URL__c = image_URL__c;
    }

    public String getReward_Product_Name__c() {
        return Reward_Product_Name__c;
    }

    public void setReward_Product_Name__c(String reward_Product_Name__c) {
        Reward_Product_Name__c = reward_Product_Name__c;
    }
}
