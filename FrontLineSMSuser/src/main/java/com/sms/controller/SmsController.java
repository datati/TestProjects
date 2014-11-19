package com.sms.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.kevinsawicki.http.HttpRequest;
import com.sms.model.User;

/**
 * @author Douglas O Atati
 * @version 1.0
 */

@Controller
@RequestMapping("/welcome")
public class SmsController {

	@RequestMapping(method = RequestMethod.GET)
	public String getUserInformation(Map<String, Object> model) {
		User userForm = new User();		
		model.put("userForm", userForm);
		return "sendAlert";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user",method = RequestMethod.POST)
	public String processAlert(@ModelAttribute("userForm") User user, Map<String, Object> model) throws IOException {	
		
		String userNames = user.getFirstname() +" "+ user.getLastName();
		String phoneNumber = user.getPhoneNumber().trim();
		String serverStatus = siteStatus("https://cloud.frontlinesms.com");
		String amessage = "Hello "+userNames+"."+"Servers status is "+serverStatus;
		System.out.println(amessage);
		
		//if the server is down:TODO logic
		/*if(!serverStatus.equalsIgnoreCase('200')){
			int response = HttpRequest.get(new URL("https://cloud.frontlinesms.com/api/1/smssync/2762/kingslanding?task=send&messages="+amessage+"&recipients="+phoneNumber)).code();
		}*/
		
		//1. RECEIVING AN INCOMING MESSAGE IN FRONTLINE SMS
		//Perform a GET request and get the status of the response(uses the kevinsawicki maven plugin.)It sends the sms to the frontlinesms account
		int response = HttpRequest.get("https://cloud.frontlinesms.com/api/1/smssync/2762/kingslanding",true, "message",amessage, "from", phoneNumber).code();
		System.out.println("Doug your Get Response was :" + response);		
		
		//2. SENDING AN OUTGOING MESSAGE FROM FRONTLINE SMS
		
		//make this is JSON object from form properties given by the user
		JSONObject obj = new JSONObject();
		obj.put("secret","kingslanding");
		obj.put("message", amessage);
		JSONObject obj8 = new JSONObject();
		obj8.put("type", "address");
		obj8.put("value", phoneNumber);
		JSONArray list = new JSONArray();
		list.add(obj8);
		obj.put("recipients", list);
		
		//POST json to https and read response. Getting a 500 error on this:(
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); 		
		try { 
			HttpPost request = new HttpPost("https://cloud.frontlinesms.com/api/1/smssync/2762/kingslanding"); 
			StringEntity params = new StringEntity(obj.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params); 
			HttpResponse postresponse =httpClient.execute(request);
			// handle response here...
			System.out.println("Doug your Post Response was :" + postresponse);
			
		} catch (Exception ex) { 
			// handle exception here 
		} 
		finally {
			httpClient.close(); 
		}		

		return "receivedAlert";
	}
	
	public String siteStatus(String url) throws IOException{
		URL u = new URL (url);
		HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
		huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
		huc.connect () ; 
		int code = huc.getResponseCode();
		String codeString = ""+code;
		return codeString;
	}

}
