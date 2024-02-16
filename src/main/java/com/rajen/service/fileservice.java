package com.rajen.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface fileservice {

	
	
	public String uploadfile(MultipartFile file ,String path)throws IOException;
	
	InputStream  serveimage(String path,String imagename)throws FileNotFoundException ;
}
