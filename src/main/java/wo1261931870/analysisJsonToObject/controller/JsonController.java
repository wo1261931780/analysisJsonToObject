package wo1261931870.analysisJsonToObject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wo1261931870.analysisJsonToObject.service.JsonFileService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 * Project:analysisJsonToObject
 * Package:wo1261931870.analysisJsonToObject.controller
 *
 * @author liujiajun_junw
 * @Date 2024-12-09-14  星期二
 * @Description
 */
@RestController
@RequestMapping("/api")
public class JsonController {

	private final JsonFileService jsonFileService;

	public JsonController(JsonFileService jsonFileService) {
		this.jsonFileService = jsonFileService;
	}

	@GetMapping("/data")
	public List<Map<String, Object>> getData() throws IOException {
		return jsonFileService.readJsonFiles("src/main/java/wo1261931870/analysisJsonToObject/jsonObject");
	}
}
