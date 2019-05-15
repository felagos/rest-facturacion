package com.rest.facturacion.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
	
	public Resource loadPhoto(String filename) throws Exception;
	
	public void writeFile(MultipartFile photo) throws Exception;
	
	public void deleteFile(String filename);
	
	public void init() throws Exception;

}
