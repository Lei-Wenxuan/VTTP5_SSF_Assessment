package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;

public class NoticeService {

	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type

	@Value("${publishing.server.hostname}")
	private String noticeServerUrl;

	RestTemplate restTemplate = new RestTemplate();

	public String postToNoticeServer(Notice notice) {

		

		JsonObject reqPayloadJson = Json.createObjectBuilder()
				.add("title", notice.getTitle())
				.add("poster", notice.getPoster())
				.add("postDate", notice.getPostDate())
				.add("categories", notice.getCategories())
				.add("text", notice.getText())
				.build();

		String reqPayload = reqPayloadJson.toString();

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

			if (response.getStatusCode() != HttpStatus.ACCEPTED) {
				throw new Exception("Post failed: " + response.getStatusCode() + response.getBody());
			}

			return response.getBody();

		} catch (Exception e) {
			throw new Exception("Post failed: " + e.getMessage());
		}
	}
}
