import java.util.Map;
import java.util.HashMap;

public class BasicTest {
	public static void main(String[] args) {
		String apiKey = "testkey";
		String apiSecret = "testsecret";

		Screenshotscloud screenshotscloud = new Screenshotscloud(apiKey, apiSecret);

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("url", "samsung.com/us/mobile/galaxy");
		options.put("width", 1040);
		options.put("viewport_width", 1560);
		options.put("cache_time", 15);


		try {
			String result = screenshotscloud.screenshotUrl(options);
			System.out.println(result);
		} catch (Exception e) {
			throw new RuntimeException("Error", e);
		}
	}
}