package com.day.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
/*
 * @RestController = @Controller + @ResponseBody
 * 
 * Restful용 요청방식종류 GET - 검색 POST - 추가 "/board" PUT - 수정 DELETE - 삭제
 */
public class RestController {
	private Logger log = Logger.getLogger(RestController.class);

	@PostMapping("/board")
	public Map<String, Object> write(@RequestBody // 요청 전달 데이터가 application/json타입으로 전달됨
	Map<String, String> map) {
		log.error("/board 추가 시작 ");
		log.error("map : " + map);
		Map<String, Object> result = new HashMap<>();
		result.put("status", 1);
		result.put("msg", "게시글 추가 성공!");
		return result;
	}

	//produces = { MediaType.TEXT_PLAIN_VALUE } : 응답형식
//	@PostMapping(value = "/board/reply/{no}", produces = { MediaType.TEXT_PLAIN_VALUE })
//	public ResponseEntity<String> reply(@PathVariable int no, @RequestBody Map<String, String> map) {
//		ResponseEntity<String> entity =
//				new ResponseEntity<String>(HttpStatus.OK);
//				new ResponseEntity<>("답글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
//		log.error(entity.getBody());
//		return entity;
//	}
	
	@PostMapping("/board/reply/{no}")
	public ResponseEntity<Map<String, Object>> reply(@PathVariable int no, @RequestBody Map<String, String> map) {
		Map<String,Object> result = new HashMap<>();
		result.put("msg", "답글쓰기 실패");		
		ResponseEntity<Map<String, Object>> entity = 
//				new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
				new ResponseEntity<>(result, HttpStatus.OK);
		return entity;
	}
//	@GetMapping("/board/list")
//	public List<Map<String, Object>> list() {
//	public List<Map<String, Object>> list(@PathVariable(required = false) String word) {
	@GetMapping(value = { "/board/list", "/board/list/{word}" })
	public List<Map<String, Object>> list(@PathVariable(name = "word") Optional<String> optWord) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> result;
		String word = null;
		if (optWord.isPresent()) {
			word = optWord.get();
			log.error("/board 단어검색 시작 : word=" + word);
			result = new HashMap<>();
			result.put("no", 11);
			result.put("title", "제목1");
			result.put("content", "내용");
			resultList.add(result);
			result = new HashMap<>();
			result.put("no", 22);
			result.put("title", "제목");
			result.put("content", "내용1");
			resultList.add(result);
		} else {
			log.error("/board 전체검색 시작");
			for (int i = 0; i < 3; i++) {
				result = new HashMap<>();
				result.put("no", i + 1);
				result.put("title", "제목" + (i + 1));
				result.put("content", "내용" + (i + 1));
				resultList.add(result);
			}
		}
		return resultList;
	}

	// localhost:8888/restspring/board?no=123
	// public void info(@RequestParam(name="no") int board_no){}
	// -> board_no라는 요청전달데이터가 전달되지 않았을때는 예외 발생 따라서, required=false,
	// defaultValue="0"으로 설정해준 적이 있음
	@GetMapping("/board/{no}") // localhost:8888/restspring/board/123
	public Map<String, Object> info(@PathVariable int no) {
		// service->dao
		Map<String, Object> result = new HashMap<>();
		result.put("no", no);
		result.put("title", "제목1");
		result.put("content", "내용1");
		return result;
	}

	@PutMapping("/board/{no}")
//	public void modify(@PathVariable int no, @RequestBody Map<String, String> map) {
//		log.error(map);
//		log.error(no);
//	}
	public ResponseEntity<String> modify(@PathVariable int no, @RequestBody Map<String, String> map) {
		log.error(map);
		log.error(no);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.OK);
		// (HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}

	@DeleteMapping("/board/{no}")
	public ResponseEntity<String> remove(@PathVariable int no) {
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
//		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.OK);

		return entity;
	}
}
