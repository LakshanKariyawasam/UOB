package com.perisic.rmiinterface;

import java.io.Serializable;
import java.util.ArrayList;

import org.bson.Document;

import com.perisic.mongoclient.SurveyAccessService;
import com.perisic.mongoclient.SurveyLogingService;

public class UserRoles implements Serializable {

    private static final long serialVersionUID = -6010824197871684473L;
    private String userId;

    public UserRoles() {
        super();
    }

    public ArrayList<Document> generateUserLogin(String userName, String login) {
        String encyPass = null;
        SurveyAccessService userInfo = new SurveyAccessService();
        SurveyLogingService surveyAccs = new SurveyLogingService();

        if (!userName.isEmpty()) {
            this.userId = userInfo.getUserIdByUserName(userName);
            System.out.println(this.userId);
            if(this.userId == null) {
            	return null;
            } else {
                encyPass = surveyAccs.decryptLoggins(login, userId);
                System.out.println(encyPass);
            }
        }
        if (!encyPass.isEmpty() || encyPass != null) {
            String userHash = userInfo.getUserAccessHash(this.userId);
            if (encyPass.equals(userHash)) {
                SurveyAccessService sas = new SurveyAccessService();
                return sas.getUserDetails(userName);
            }
        } 
        return null;
    }
    
    public Integer createPaneUser(String currntUserName, String usrname, String fname, String lname, boolean isSuper) {
        SurveyAccessService userInfo = new SurveyAccessService();

        int val = userInfo.createPaneUser(currntUserName, usrname, fname, lname, isSuper);
        
        return val;

    }
    
    
    public Integer updatePaneUser(String currntUserName, String usrname, String fname, String lname, final boolean isSuper, boolean isPasswordReset, boolean active) {
        SurveyAccessService userInfo = new SurveyAccessService();

        int val = userInfo.updatePaneUser(currntUserName,usrname,fname,lname,isSuper,isPasswordReset,active);
        
        return val;

    }
    
    public static ArrayList<Document> getBasicUserDetails() {
        SurveyAccessService userInfo = new SurveyAccessService();
        return userInfo.getUserDetails(null);

    }

}

/**
 * Debug main for SurveyQuestionService Remove once the implementaion completed
 */

class StartUserService {
    public static void main(final String[] args) throws Exception {
        final UserRoles acc = new UserRoles();
        System.out.println(acc.generateUserLogin("admin@","apZJ"));
    }
}