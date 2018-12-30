/**
 * 
 */
package com.demo.induction.tp.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suresh
 *
 */
@Component(value="fileOperations")
public class FileOperations {
	
	public File multipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException 
	{
		File targetFile = new File(System.getProperty("user.dir")+File.separator+multipartFile.getOriginalFilename());
		multipartFile.transferTo(targetFile);
		return targetFile;
	}

	public void deleteFile(File file) throws IOException, SecurityException {
		if(null!=file && file.exists())
			file.delete();		
	}
}
