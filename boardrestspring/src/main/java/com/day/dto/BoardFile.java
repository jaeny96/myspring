package com.day.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardFile {
	private RepBoard repBoard;
	private List<MultipartFile> etcFiles;
	
	public RepBoard getRepBoard() {
		return repBoard;
	}
	
	public void setRepBoard(RepBoard repBoard) {
		this.repBoard = repBoard;
	}
	
	public List<MultipartFile> getEtcFiles() {
		return etcFiles;
	}
	
	public void setEtcFiles(List<MultipartFile> etcFiles) {
		this.etcFiles = etcFiles;
	}
	
	@Override
	public String toString() {
		return "BoardFile [repBoard=" + repBoard + ", etcFiles=" + etcFiles + "]";
	}
}
