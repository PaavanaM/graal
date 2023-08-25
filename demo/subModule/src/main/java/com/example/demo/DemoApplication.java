package com.example.demo;

import com.example.demo.model.UserInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.admin.directory.model.User;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)  {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public UserInfo getUserInfoFromGmailUser() {
		System.out.println("Inside listener");
		String jsonString = "{\"kind\":\"akindVal\",\"id\":\"738471341\",\"etag\":\"jnrfnfi-23n8nejnfkweh3\",\"primaryEmail\":\"aaditya.mehta@gmail.com\",\"name\":{\"givenName\":\"Aaditya\",\"familyName\":\"Mehta\",\"fullName\":\"Aaditya Mehta\"},\"isAdmin\":false,\"isDelegatedAdmin\":false,\"lastLoginTime\":\"2023-08-23T16:50:13.000Z\",\"creationTime\":\"2021-10-05T16:59:27.000Z\",\"agreedToTerms\":true,\"suspended\":false,\"archived\":false,\"changePasswordAtNextLogin\":false,\"ipWhitelisted\":false,\"emails\":[{\"address\":\"aaditya.mehta@gmail.com\",\"primary\":true}],\"externalIds\":[{\"value\":\"1819\",\"type\":\"organization\"}],\"relations\":[{\"value\":\"danielk@gmail.com\",\"type\":\"manager\"}],\"addresses\":[{\"type\":\"home\",\"country\":\"USA\"}],\"organizations\":[{\"title\":\"Associate Trader\",\"type\":\"work\",\"customType\":\"\",\"department\":\"Trading\",\"location\":\"USA\",\"description\":\"Permanent\",\"costCenter\":\"USA\"}],\"languages\":[{\"languageCode\":\"en-GB\",\"preference\":\"preferred\"}],\"customerId\":\"C01cn0tzs\",\"orgUnitPath\":\"/abc/NewYork/Trading\",\"isMailboxSetup\":true,\"isEnrolledIn2Sv\":true,\"isEnforcedIn2Sv\":true,\"includeInGlobalAddressList\":true}\n";
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		try {
			user = mapper.readValue(jsonString, User.class);
		} catch (JsonProcessingException e) {
			System.out.println("exception");
			throw new RuntimeException(e);
		}
		System.out.println("**** User is {}"+ user);
		String id = user.getId();
		String primEm = user.getPrimaryEmail();
		Boolean suspended = user.getSuspended();
		UserInfo.StatusEnum status = user.getSuspended() == null || user.getSuspended()  ? UserInfo.StatusEnum.INACTIVE :
				UserInfo.StatusEnum.ACTIVE;
		String fullname = user.getName().getFullName();
		String famname = user.getName().getFamilyName();
		UserInfo u = UserInfo.builder().id(user.getId()).uniquename(user.getPrimaryEmail())
				.firstName(user.getName().getFullName()).lastName(user.getName().getFamilyName())
				.email(user.getPrimaryEmail())
				.status(user.getSuspended() == null || user.getSuspended()  ? UserInfo.StatusEnum.INACTIVE :
						UserInfo.StatusEnum.ACTIVE)
				.build();
		return u;
	}

}
