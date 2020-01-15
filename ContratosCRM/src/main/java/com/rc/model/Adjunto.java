package com.rc.model;

public class Adjunto {
	//private String extn;
	//private String isPreviewAvailable;
	//private String downloadUrl;
	//private String entityId;
	private String fileSize;
	private String fileName;
	//private String fileId;
	//private String previewUrl;
	private String id;
	private String serverLocation;
	private String createdTime;
	
	Adjunto(){
		
	}
	public Adjunto(String fileName, String fileSize, String id, String serverLocation, String createdTime) {
		
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.id = id;
		this.serverLocation = serverLocation;
		this.createdTime = createdTime;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	
	public String getId() {
		return id;
	}
	public String getServerLocation() {
		return serverLocation;
	}
	public String getCreatedtime() {
		return createdTime;
	}
	
	//setters
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setServerLocation(String serverLocation) {
		this.serverLocation = serverLocation;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
}
