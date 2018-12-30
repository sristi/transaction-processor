/**
 * 
 */
package com.demo.induction.tp.rest.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import javax.xml.bind.JAXB;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import com.demo.induction.tp.demo.TransactionProcessorApp;
import com.demo.induction.tp.util.TransactionXmlWrapper;

/**
 * @author suresh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes= {TransactionProcessorApp.class})
public class TransactionProcessorEndPointIntegrationTest {

	static final String BASE_URL="/transaction-processor";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public final void testProcessXmlTransactionTransactionXmlWrapper() throws Exception{
	    File inputFile =  new File("src/test/resources/transaction_import_data.xml");
	    assertNotNull(inputFile);
	    assertTrue(inputFile.exists());
	    
	    MultiValueMap<String, Object> dataMap = new LinkedMultiValueMap<>();
		dataMap.add("file", new FileSystemResource(inputFile));
		
	    ResponseEntity<String> response = testRestTemplate.postForEntity(BASE_URL+"/process_xml_data",dataMap , String.class);
		assertNotNull(response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//corner-case: when multi-part is invalid
		dataMap = new LinkedMultiValueMap<>();
		dataMap.add("file", new FileSystemResource(new File("src/test/resources/empty_data.xml")));
		response = testRestTemplate.postForEntity(BASE_URL+"/process_xml_data",dataMap , String.class);
		assertNotNull(response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
	}

	@Test
	public final void testProcessCsvTransactionMultipartFile() throws Exception {
		File file =  new File("src/test/resources/transaction_import_data.csv");
		assertNotNull(file);
		assertTrue(file.exists());
		
		MultiValueMap<String, Object> dataMap = new LinkedMultiValueMap<>();
		dataMap.add("file", new FileSystemResource(file));

        ResponseEntity<String> response = testRestTemplate.postForEntity(BASE_URL+"/process_csv_data", dataMap , String.class);
		assertNotNull(response);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public final void testProcessCsvTransactionForFileExceedingUploadSizeLimit(){
		File file =  new File("src/test/resources/transaction_import_data_big.csv");
		assertNotNull(file);
		assertTrue(file.exists());

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", new FileSystemResource(file));

        /*ResponseEntity<String> response = testRestTemplate.postForEntity(BASE_URL+"/process_csv_data", map , String.class);
		assertNotNull(response);*/
		//assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<String> response = testRestTemplate.postForEntity(BASE_URL+"/process_csv_data_big", map , String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		/*try
		{
		}
		catch(Exception e)
		{
			assertThat(e instanceof FileSizeLimitExceededException);
			fail(e.getMessage());
		}*/
		/*assertThatExceptionOfType(RestClientException.class).isThrownBy(
				()->testRestTemplate.postForEntity(BASE_URL+"/process_csv_data", map , String.class));*/
	}
	

}
