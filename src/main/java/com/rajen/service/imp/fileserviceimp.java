package com.rajen.service.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rajen.exception.badapirequest;
import com.rajen.service.fileservice;


@Service
public class fileserviceimp  implements fileservice{

	@Override
	public String uploadfile(MultipartFile file, String path) throws IOException {
		
		
		       String originalFilename = file.getOriginalFilename();
		       
		       String randomid = UUID.randomUUID().toString();
		       
    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    
    String filenamewithextension = randomid+extension;
    String fullpathname=path+filenamewithextension;
    
    System.out.println(fullpathname);
    
    if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")) {
    	
    	
    	File folder = new File(path);
    	
    	if(!folder.exists()) {
    		folder.mkdirs();
    	}
    	Files.copy(file.getInputStream(), Paths.get(fullpathname));
    	
    }else {
    	throw new badapirequest("file extension " + extension + "is not exist");
    }
		
		return filenamewithextension;
	}

	@Override
	public InputStream serveimage(String path, String imagename) throws FileNotFoundException {
		
		
		   String fullpath= path+File.separator+imagename;
		   
		   InputStream inputStream= new FileInputStream(fullpath);
		   
		return inputStream;
	}
	
	


}
