package com.sms.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

/**
 * @author Douglas O Atati
 * @version 1.0
 */

@Controller
@RequestMapping("/welcome")
public class SmsController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String makeJson(Model model) throws IOException {
		
		//TO DO:this is for storing the form properties into a json object
		JSONObject obj = new JSONObject();
		obj.put("name", "dee");
		obj.put("age", new Integer(100));
		JSONArray list = new JSONArray();
		list.add("msg 1");
		list.add("msg 2");
		list.add("msg 3");
		obj.put("messages", list);
		JSONObject json = new JSONObject();
		json.put("someKey", "someValue");
		//test string to json
		String jsonStr = "{ 'secret':'kingslanding',"
				+ "'message':'Hello, Doug. The servers are down.',"
				+ "'recipients':[{ 'type':'group', '':'' },"
				+ "{ 'type':'group', 'name':'friends' },"
				+ "{ 'type':'smartgroup', 'id':'' },"
				+ "{ 'type':'smartgroup', 'name':''},"
				+"{ 'type':'contact', 'id':'' },"
				+"{ 'type':'contact', 'name':'' },"
				+"{ 'type':'address','value':'+1234567890'}]}";
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		try {
			HttpPost request = new HttpPost("https://cloud.frontlinesms.com/api/1/smssync/2762/kingslanding");
			StringEntity params = new StringEntity(jsonStr.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			
			//System.out.println("Doug your Response was :" + response);

			// handle response here...
		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.close();
		}

		model.addAttribute("messages", obj);
		
		return "helloWorld";
	}

}
