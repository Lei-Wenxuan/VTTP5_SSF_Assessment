package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type
	//
	/*
	 * Write the redis-cli command that you use in this method in the comment.
	 * For example if this method deletes a field from a hash, then write the
	 * following
	 * redis-cli command
	 * hdel myhashmap a_key
	 *
	 */

	@Autowired
	private RedisTemplate<String, Object> template;

	public Boolean insertNotices(String key, String value) {
		// redis-cli command
		// set id json_payload
		Boolean isCreated = false;

		if (!checkExists(key)) {
			template.opsForValue().set(key, value);
			isCreated = true;
		}

		return isCreated;
	}

	@SuppressWarnings("null")
	public String get(String key) {
		return template.opsForValue().get(key).toString();
	}

	public Boolean checkExists(String key) {
		return template.hasKey(key);
	}

	public Boolean delete(String key) {
		Boolean isDeleted = false;

		if (checkExists(key)) {
			isDeleted = template.delete(key);
		}

		return isDeleted;
	}

	public Boolean checkHealth() {
		// redis-cli command
		// randomkey
		Boolean isHealthy = false;
		if (null != template.randomKey())
			isHealthy = true;
		return isHealthy;
	}

}
