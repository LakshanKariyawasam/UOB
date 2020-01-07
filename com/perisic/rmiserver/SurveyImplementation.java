package com.perisic.rmiserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import org.bson.Document;

import com.perisic.mongoclient.SurveyAccessService;
import com.perisic.mongoclient.SurveyAnswerService;
import com.perisic.mongoclient.SurveyQuestionService;
import com.perisic.rmiinterface.Question;
import com.perisic.rmiinterface.RemoteQuestions;
import com.perisic.rmiinterface.UserRoles;

import java.util.ArrayList;
import java.util.List;

public class SurveyImplementation extends UnicastRemoteObject implements RemoteQuestions {
	
    private static final long serialVersionUID = -3763231206310491048L;
    Vector<Question> myQuestions = new Vector<Question>();
    // Vector<UserRoles> surveyUserRoles = new Vector<UserRoles>();
    UserRoles surveyUserRoles =  new UserRoles();

    @SuppressWarnings("unchecked")
	SurveyImplementation() throws RemoteException {
        super();
        // System.out.println("QuestionServerImplementation Created");
        

        SurveyQuestionService svy = new SurveyQuestionService();
        for (org.bson.Document a : svy.getBasicQuestions()) {
            List<String> quesOptions = new ArrayList<String>();
            quesOptions = (List<String>) a.get("Answer");
            String[] answers = new String[quesOptions.size()];
            answers = quesOptions.toArray(answers);
            myQuestions.add(new Question((String) a.get("Question"),(String) a.get("Answer_Type"),(Integer) a.get("Question_ID"),(Boolean) a.get("Question_Status"), answers));
        }
    }

    @Override
    public Question getQuestion(int i) throws RemoteException {
        return myQuestions.elementAt(i);
    }
    
    @Override
    public ArrayList<Document> UserDetails() throws RemoteException {
        return surveyUserRoles.getBasicUserDetails();
    }

    @Override
    public int getNumberOfQuestions() throws RemoteException {
        return myQuestions.size();
    }

    @Override
	public void submitAnswer(int i, String answer, int userId, int quesid ) throws RemoteException {
		myQuestions.elementAt(i).addAnswer(answer,userId,quesid);
    }
    
    @Override
	public Integer updateQuestionPane(String description, int quesId, Boolean status) throws RemoteException {
    	return SurveyQuestionService.updateQuestionPane(description,quesId,status) ;
    }
    
    @Override
    public Integer addQuestionToSurvey(String questionDesc, String option, Boolean status) throws RemoteException {
    	return Question.addQuestionToSurvey(questionDesc,option,status) ;
    }
    
    @Override
    public Vector<Question> getData() {
    return myQuestions;
    }
    
    /**
     * Generate the standerd user login for survey users
     */
    @Override
    public ArrayList<Document> getSurveyAccess( String username, String pass){
        return surveyUserRoles.generateUserLogin(username, pass);
    }

	@Override
	public Integer createPaneUser(String currntUserName, String usrname, String fname, String lname, boolean isSuper) {
		return surveyUserRoles.createPaneUser(currntUserName, usrname, fname, lname, isSuper);
	}
	
    @Override
	public Integer updatePaneUser(String currntUserName, String usrname, String fname, String lname, final boolean isSuper, boolean isPasswordReset, boolean active) throws RemoteException {
    	return surveyUserRoles.updatePaneUser(currntUserName,usrname,fname,lname,isSuper,isPasswordReset,active) ;
    }
	
	public void getBasicQuestions() throws RemoteException {
        myQuestions.clear();
        Question sample  = new Question(null, null, null, null, null);
		for(Question a : sample.updateVectorQuestions()){
            myQuestions.add(a);
        }
    }
	
    @Override
    public String finalSurveyAnaly(String username) {
    	return SurveyAnswerService.finalSurveyAnalysis(username);
    }

}



