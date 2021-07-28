package com.day.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.day.dto.RepBoard;
import com.day.dto.RepBoardFile;

import net.coobird.thumbnailator.Thumbnailator;

@RestController
public class UploadController {
	@Autowired
	private ServletContext servletContext;

	private Logger log = Logger.getLogger(UploadController.class);

	@PostMapping("/uploadajax")
	// 매개 변수의 개수가 많아지면 코드가 지저분 해짐 String boardTitle, String boardContent-> dto타입의
	// RepBoard로 변경!
	public void ajax(@RequestPart List<MultipartFile> foodFiles, @RequestPart MultipartFile drinkFile,
			RepBoard repBoard) {
		log.info("요청전달 데이터 title : " + repBoard.getBoardTitle() + ", content : " + repBoard.getBoardContent());
		log.info("요청전달데이터 =" + repBoard.getBoardC().getId());
		log.info("foodFiles.size() : " + foodFiles.size());
		log.info("drinkFile.getSize() : " + drinkFile.getSize());

		String uploadPath = servletContext.getRealPath("upload");
		log.info("업로드 실제 경로  : " + uploadPath);

		if (!new File(uploadPath).exists()) {
			log.info("업로드 실제 경로 생성");
			new File(uploadPath).mkdirs();
		}

		// foodFiles 저장
		if (foodFiles != null) {
			for (MultipartFile foodFile : foodFiles) {
				String foodFileName = foodFile.getOriginalFilename();
				if (!"".equals(foodFileName) && foodFile.getSize() != 0) {
					System.out.println("파일크기 : " + foodFile.getSize() + ", 파일 이름 : " + foodFileName);
					// 업로드 된 파일을 해당 디렉토리에 저장
					// uploadPath에다가 해당 파일 이름으로 저장을 할거고
					// UUID : Universal Unique Identifier
					String fileName = UUID.randomUUID() + "_" + foodFileName;
					File file = new File(uploadPath, fileName);
					try {
						// foodFile의 바이트 내용을 복사해서 해당 file 객체를 채우겠다
						FileCopyUtils.copy(foodFile.getBytes(), file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// drinkFile 저장
		String drinkFileName = drinkFile.getOriginalFilename();
		if (!"".equals(drinkFileName) && drinkFile.getSize() != 0) {
			// UUID : Universal Unique Identifier
			String fileName = UUID.randomUUID() + "_" + drinkFileName;
			File file = new File(uploadPath, fileName);
			try {
				FileCopyUtils.copy(drinkFile.getBytes(), file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@PostMapping("/uploaddto")
	public Map<String, String> dto(RepBoardFile repBoardFile) {
		Map<String, String> result = new HashMap<>();
		// ServletUriComponentsBuilder.fromCurrentContextPath().path("upload");
		log.info(repBoardFile.getRepBoard());
		log.info(repBoardFile.getProducts());
		log.info(repBoardFile.getFoodFiles());
		log.info(repBoardFile.getDrinkFile());

		String uploadPath = servletContext.getRealPath("upload");
		log.info("업로드 실제경로:" + uploadPath);

		// 경로 생성
		if (!new File(uploadPath).exists()) {
			log.info("업로드 실제경로생성");
			new File(uploadPath).mkdirs();
		}

		List<MultipartFile> foodFiles = repBoardFile.getFoodFiles();
		if (foodFiles != null) {
			for (MultipartFile food : foodFiles) {
				if (!"".equals(food.getOriginalFilename()) && food.getSize() != 0) {
					log.info("FOOD 파일이름:" + food.getOriginalFilename() + ", 크기:" + food.getSize());

					// Universal Unique Identifier
					String fileName = UUID.randomUUID() + "_" + food.getOriginalFilename();
					File target = new File(uploadPath, fileName);
					// 파일 생성
					try {
						FileCopyUtils.copy(food.getBytes(), target);
						log.info("파일 생성");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		MultipartFile drinkFile = repBoardFile.getDrinkFile();
		if (drinkFile != null && !"".equals(drinkFile.getOriginalFilename()) && drinkFile.getSize() != 0) {
			String fileName = UUID.randomUUID() + "_" +drinkFile.getOriginalFilename();
			File target = new File(uploadPath, fileName);
			try {
				FileCopyUtils.copy(drinkFile.getBytes(), target);
				log.info("파일 생성");
				// 이미지 파일인 경우 섬네일 파일을 만듦
				String contentType = drinkFile.getContentType();// 파일 형식
				log.info("DRINK 파일형식:" + contentType);

				if (contentType.startsWith("image")) { // 이미지 파일인 경우
					String thumbnailName = "s_" + fileName; // s_파일 이름
					File thumbnailFile = new File(uploadPath, thumbnailName);
					FileOutputStream thumbnail = new FileOutputStream(thumbnailFile);
					InputStream drinkFileIS = drinkFile.getInputStream();
					int width = 100;
					int height = 100;

					// 썸네일 파일 만들기
					Thumbnailator.createThumbnail(drinkFileIS, thumbnail, width, height);
					
					result.put("drinkFileName",thumbnailName);
					return result;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

}
