package com.rest.facturacion.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rest.facturacion.services.interfaces.IFileUploadService;

@Service
public class FileUploadService implements IFileUploadService {
	
	@Value("${application.controller.file_dir}")
	private String uploadDir;

	@Override
	public Resource loadPhoto(String filename) throws Exception {
		Path pathPhoto = Paths.get(this.uploadDir).resolve(filename).toAbsolutePath();
		Resource resource = null;
		
		try {
			resource = new UrlResource(pathPhoto.toUri());
			if(!resource.exists() || !resource.isReadable()) {
				throw new Exception("No se puede acceder al recurso");
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resource;
	}
	
	@Override
	public void writeFile(MultipartFile photo) throws Exception {
		Path path = Paths.get(uploadDir);
		String rootPath = path.toFile().getAbsolutePath();
		try {
			byte[] bytes = photo.getBytes();
			Path fullPath = Paths.get(rootPath + "//" + photo.getOriginalFilename());
			Files.write(fullPath, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void deleteFile(String filename) {
		if(Paths.get(uploadDir) == null) return;
		Path root = Paths.get(uploadDir).resolve(filename).toAbsolutePath();
		File file = root.toFile();
		
		if(file.exists() && file.canRead()) 
			file.delete();
	}

	@Override
	public void init() throws Exception {
		Path path = Paths.get(uploadDir);
		
		if(!path.toFile().exists())
			Files.createDirectories(path);
	}

}
