package com.day.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.day.dto.Customer;
import com.day.dto.RepBoard;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;
import com.day.service.RepBoardService;

@RestController
@RequestMapping("/board/*")
public class RepBoardController {
	private Logger log = Logger.getLogger(RestController.class);

	@Autowired
	private RepBoardService service;

	@PostMapping("/write")
	public Map<String, Object> write(@RequestBody RepBoard repBoard) {
		Map<String, Object> result = new HashMap<>();
		log.error(repBoard);
		try {
			service.write(repBoard);
			result.put("status", 1);
		} catch (AddException e) {
			e.printStackTrace();
			result.put("status", 0); // 실패
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@PostMapping(value = "/reply/{no}")
	public Map<String, Object> reply(@PathVariable int no, @RequestBody RepBoard repBoard) {
		Map<String, Object> result = new HashMap<>();
		repBoard.setParentNo(no);
		try {
			service.reply(repBoard);
			result.put("status", 1);
		} catch (AddException e) {
			e.printStackTrace();
			result.put("status", 0); // 실패
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@GetMapping(value = { "/list", "/list/{word}" })
	public Map<String, Object> list(@PathVariable(name = "word") Optional<String> optWord) {
		List<RepBoard> resultList = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		try {
			if (optWord.isPresent()) {
				resultList = service.list(optWord.get());
			} else {
				resultList = service.list();
			}
			result.put("status", 1);
			result.put("list", resultList);
		} catch (FindException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@GetMapping("/{no}")
	public Map<String, Object> info(@PathVariable int no) {
		Map<String, Object> result = new HashMap<>();
		RepBoard repBoard = new RepBoard();
		try {
			repBoard = service.detail(no);
			result.put("status", 1);
			result.put("repboard", repBoard);
		} catch (FindException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@PutMapping("/{no}")
	public Map<String, Object> modify(@PathVariable int no, @RequestBody RepBoard repBoard) {
		//->session의 loginInfo 속성으로 차후 변경
		Customer boardC = new Customer();
		boardC.setId("id9");
		repBoard.setBoardC(boardC);
		repBoard.setBoardNo(no);
		Map<String, Object> result = new HashMap<>();
		try {
			service.modify(no, repBoard);
			result.put("status", 1);
		} catch (ModifyException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@DeleteMapping("/{no}")
	public Map<String, Object> remove(@PathVariable int no) {
		Map<String, Object> result = new HashMap<>();
		RepBoard repBoard = new RepBoard();
		Customer boardC = new Customer();
		boardC.setId("id3");
		repBoard.setBoardNo(no);
		repBoard.setBoardC(boardC);
		try {
			service.remove(repBoard);
			result.put("status", 1);
		} catch (RemoveException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
