package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type

	@Autowired
	NoticeRepository noticeRepository;

	@Value("${publishing.server.hostname}")
	private String noticeServerUrl;

	RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<String> postToNoticeServer(Notice notice) throws Exception {

		String reqPayload = notice.toJson(notice);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "application/json");

		RequestEntity<String> req = RequestEntity.post(noticeServerUrl)
				.headers(headers)
				.body(reqPayload);

		try {
			ResponseEntity<String> response = restTemplate.exchange(req, String.class);

			return response;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	public void insertNotices(ResponseEntity<String> response) {
		String id = responseItem(response, "id");
		noticeRepository.insertNotices(id, response.getBody());
	}

	public String responseItem(ResponseEntity<String> response, String itemName) {
		JsonObject jObject = Json.createReader(new StringReader(response.getBody())).readObject();
		String item = jObject.getString(itemName);
		return item;
	}

	public Boolean checkHealth() {
		return noticeRepository.checkHealth();
	}

}
