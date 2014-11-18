package com.sms.controller;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.kevinsawicki.http.HttpRequest;

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

		// TO DO:Will make this is JSON object from form properties given by the user
		JSONObject obj = new JSONObject();
		obj.put("secret","kingslanding");
		obj.put("message", "Hello, Douglas. Servers are down!");
		JSONObject obj8 = new JSONObject();
		obj8.put("type", "address");
		obj8.put("value", "1234567890");
		JSONArray list = new JSONArray();
		obj.put("recipients", list);
		
		//Perform a GET request and get the status of the response. This uses the kevinsawicki maven plugin.This works..
		int response = HttpRequest.get("https://cloud.frontlinesms.com/api/1/smssync/2762/kingslanding?task=send&messages=Hello+World+response&recipients=12027019440").code();
		System.out.println("Doug your Get Response was :" + response);
		
		//POST json to https and read response. Getting a 500 error on this:(
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); 
		try { 
			HttpPost request = new HttpPost("https://cloud.frontlinesms.com/api/1/smssync/2762/kingslanding"); 
			StringEntity params = new StringEntity(obj.toJSONString());
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
		model.addAttribute("messages", obj);
		return "helloWorld";
	}

}
