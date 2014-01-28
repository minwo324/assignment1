package one.main;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class JSONHelper {

	// METHODS
	public static String toJSON(Map<String,Object> map) {
		return JSONValue.toJSONString(map);
	}
	@SuppressWarnings({ "unchecked" })
	public static Map<String,Object> fromJSON(String json) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			JSONParser parser = new JSONParser();
			Map<String,Object> map = (Map<String,Object>) parser.parse(json);
			for (Map.Entry<String,Object> entry: map.entrySet()) {
				result.put((String) entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return result;
	}

}
