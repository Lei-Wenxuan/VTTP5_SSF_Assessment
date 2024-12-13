package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type

	@Autowired
	NoticeRepository noticeRepository;

	// @Value("${publishing.server.hostname}")
	// private String noticeServerUrl;

	RestTemplate restTemplate = new RestTemplate();

	public void postToNoticeServer(String reqPayload, String noticeServerUrl) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "application/json");

		RequestEntity<String> req = RequestEntity.post(noticeServerUrl)
				.headers(headers)
				.body(reqPayload);

		try {
			ResponseEntity<String> response = restTemplate.exchange(req, String.class);

			// System.out.println(response.getStatusCode());
			// System.out.println(response.getHeaders());
			// System.out.println(response.getBody());

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new Exception("Post failed: " + response.getStatusCode() + response.getBody());
			}

			// String responseBody = response.getBody();

			// return insertNotices(responseBody);

		} catch (Exception e) {
			throw new Exception("Post failed (exception): " + e.getMessage());
		}

	}

	public String insertNotices(String responseBody) {
		JsonObject jObject = Json.createReader(new StringReader(responseBody)).readObject();

		String id = jObject.getString("id");

		System.out.println(jObject);
		System.out.println(id);

		noticeRepository.insertNotices(id, responseBody);

		return id;
	}

	
}
