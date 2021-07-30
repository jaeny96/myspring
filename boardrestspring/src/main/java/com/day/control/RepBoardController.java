package com.day.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.day.dto.BoardFile;
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
	private ServletContext servletContext;

	@Autowired
	private RepBoardService service;

	@PostMapping("/write")
	public Map<String, Object> write(BoardFile boardFile, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		// 세션으로 변경 예정
		Customer loginInfo = (Customer) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			result.put("status", -1);
		} else {
			log.error("chk 2 "+boardFile);
			String id = loginInfo.getId();
			Customer c = new Customer();
			c.setId(id);
			boardFile.getRepBoard().setBoardC(c);
			log.error(boardFile.getRepBoard());

			// 업로드 관련
			String uploadPath = servletContext.getRealPath("upload");
			String path = System.getProperty("/boardrestspring/upload");
			log.info("업로드 실제경로:" + uploadPath);
			log.info("업로드 경로: " + path);

			// 경로 생성
			if (!new File(uploadPath).exists()) {
				log.info("업로드 실제경로생성");
				new File(uploadPath).mkdirs();
			}

			try {
				service.write(boardFile.getRepBoard());
				log.error("chk " + boardFile.getRepBoard());
				// 업로드 관련
				List<MultipartFile> etcFiles = boardFile.getEtcFiles();
				if (etcFiles != null) {
					for (MultipartFile etc : etcFiles) {
						if (!"".equals(etc.getOriginalFilename()) && etc.getSize() != 0) {
							int currVal = boardFile.getRepBoard().getBoardNo();

							String fileName = currVal + "_" + id + "_" + etc.getOriginalFilename();
							File target = new File(uploadPath, fileName);
//							// 파일 생성
							try {
								FileCopyUtils.copy(etc.getBytes(), target);
								log.info("파일 생성");
								result.put("msg", "파일업로드까지 성공!");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				result.put("status", 1);
				result.put("msg", "게시글 등록 성공!");
			} catch (AddException e) {
				e.printStackTrace();
				result.put("status", 0); // 실패
				result.put("msg", e.getMessage());
			}
		}
		return result;
	}

	@PostMapping(value = "/reply/{no}")
	public Map<String, Object> reply(@PathVariable int no, @RequestBody RepBoard repBoard, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		// 세션으로 변경예정
		Customer loginInfo = (Customer) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			result.put("status", -1);
		} else {
			String id = loginInfo.getId();
			Customer c = new Customer();
			c.setId(id);
			repBoard.setBoardC(c);
			repBoard.setParentNo(no);
			try {
				service.reply(repBoard);
				result.put("status", 1);
				result.put("msg", "답글 등록 성공!");
			} catch (AddException e) {
				e.printStackTrace();
				result.put("status", 0); // 실패
				result.put("msg", e.getMessage());
			}
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
			System.out.println(resultList);
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
		String uploadPath = servletContext.getRealPath("upload");
		File f = new File(uploadPath);

		RepBoard repBoard = new RepBoard();
		try {
			repBoard = service.detail(no);
			int thisBoardNo = repBoard.getBoardNo();
			String thisBoardC = repBoard.getBoardC().getId();
			if(f.isDirectory()) {
				File[] fList = f.listFiles();
				for(File ff : fList) {
					if(ff.getName().contains(thisBoardNo+"_"+thisBoardC)) {
						String thisFileName = ff.getName();
						System.out.println(thisFileName);
						result.put("fileName",thisFileName);
					}
				}
			}
			result.put("status", 1);
			result.put("repboard", repBoard);
		} catch (FindException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@PutMapping("/{no}")
	public Map<String, Object> modify(@PathVariable int no, @RequestBody RepBoard repBoard, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		// ->session의 loginInfo 속성으로 차후 변경
		Customer loginInfo = (Customer) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			result.put("status", -1);
		} else {
			String id = loginInfo.getId();

			Customer boardC = new Customer();
			boardC.setId(id);
			repBoard.setBoardC(boardC);
			repBoard.setBoardNo(no);
			try {
				service.modify(no, repBoard);
				result.put("status", 1);
				result.put("msg", "수정 성공!");
			} catch (ModifyException e) {
				result.put("status", 0);
				result.put("msg", e.getMessage());
			}
		}
		return result;
	}

	@DeleteMapping("/{no}")
	public Map<String, Object> remove(@PathVariable int no, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		RepBoard repBoard = new RepBoard();
		// 세션으로 변경 예정
		Customer loginInfo = (Customer) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			result.put("status", -1);
		} else {

			String id = loginInfo.getId();

			Customer boardC = new Customer();
			boardC.setId(id);
			repBoard.setBoardNo(no);
			repBoard.setBoardC(boardC);
			try {
				service.remove(repBoard);
				result.put("status", 1);
				result.put("msg", "삭제 성공!");
			} catch (RemoveException e) {
				result.put("status", 0);
				result.put("msg", e.getMessage());
			}
		}
		return result;
	}
}
