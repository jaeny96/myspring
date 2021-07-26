package com.day.control;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
/*
 * @RestController = @Controller + @ResponseBody
 * 
 * Restful용 요청방식종류
 * GET - 검색
 * POST - 추가 "/board"
 * PUT - 수정
 * DELETE - 삭제
 */
public class RestController {
	private Logger log = Logger.getLogger(RestController.class);
	
	@PostMapping("/board")
	public String add(@RequestBody Map<String, String> map) {
		log.error("/board 추가 시작 ");
		log.error("map : " + map);
		return "게시글 추가 성공";
	}
}
