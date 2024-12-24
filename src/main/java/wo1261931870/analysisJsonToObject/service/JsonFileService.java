package wo1261931870.analysisJsonToObject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author junw
 */
@Service
public class JsonFileService {

	public List<Map<String, Object>> readJsonFiles(String directoryPath) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Path dirPath = Paths.get(directoryPath);
		List<Map<String, Object>> result = new ArrayList<>();

		Files.list(dirPath)
				.filter(path -> path.toString().endsWith(".json"))
				.forEach(path -> {
					try {
						JsonNode rootNode = objectMapper.readTree(path.toFile());
						Map<String, Object> jsonData = traverseJson(rootNode);
						result.add(jsonData);
					} catch (IOException e) {
						throw new RuntimeException("Failed to read file: " + path, e);
					}
				});
		return result;
	}

	private Map<String, Object> traverseJson(JsonNode node) {
		Map<String, Object> dataMap = new HashMap<>();
		if (node.isObject()) {
			for (Iterator<String> it = node.fieldNames(); it.hasNext(); ) {
				String fieldName = it.next();
				JsonNode fieldValue = node.get(fieldName);
				if (fieldValue.isObject() || fieldValue.isArray()) {
					dataMap.put(fieldName, traverseJson(fieldValue));
				} else {
					dataMap.put(fieldName, fieldValue.asText());
				}
			}
		} else if (node.isArray()) {
			List<Object> arrayData = new ArrayList<>();
			for (JsonNode item : node) {
				arrayData.add(traverseJson(item));
			}
			dataMap.put("items", arrayData);
		}
		return dataMap;
	}
}
