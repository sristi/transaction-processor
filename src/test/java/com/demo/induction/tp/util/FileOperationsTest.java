/**
 * 
 */
package com.demo.induction.tp.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;

import static org.assertj.core.api.Assertions.*;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suresh
 *
 */
public class FileOperationsTest {

	private static final String HOME_DIR=System.getProperty("user.dir");
	
	private File testFile;
	private FileOperations fileOperations;
	
	@Before
	public void setUp() throws Exception {
		fileOperations = new FileOperations();
		testFile = new File(HOME_DIR+File.separator+"dummy_file.txt");
		if(!testFile.exists())
			testFile.createNewFile();
	}


	@Test
	public final void testMultipartToFile() throws Exception{
		assertThat(testFile).isNotNull();
	    FileInputStream input = new FileInputStream(testFile);
	    assertThat(input).isNotNull();
	    
	    MultipartFile multipartFile = new MockMultipartFile("file","multipart.txt","text/xml",input);
	    assertThat(multipartFile).isNotNull();
	    File converted = fileOperations.multipartToFile(multipartFile);
	    assertThat(converted).isNotNull();
	    assertThat(converted.exists());

	    //corner-case: originalname of file is null
	    MultipartFile multipartFileWithError = new MockMultipartFile("file","HELLO".getBytes());
	    Throwable throwable = catchThrowable(()-> fileOperations.multipartToFile(multipartFileWithError));
	    assertTrue(throwable instanceof FileSystemException);
	    
		}

	/**
	 * 
	 */
	@Test
	public final void testDeleteFile() throws Exception {
		assertThat(testFile).isNotNull();
		assertTrue(testFile.exists());
		fileOperations.deleteFile(testFile);
		assertFalse(testFile.exists());//indicates file no longer exists
		
	}

}
