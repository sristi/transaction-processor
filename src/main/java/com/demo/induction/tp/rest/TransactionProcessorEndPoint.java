/**
 * 
 */
package com.demo.induction.tp.rest;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.induction.tp.TransactionProcessor;
import com.demo.induction.tp.util.FileOperations;

/**
 * @author suresh
 *
 */
@RestController
@RequestMapping("/transaction-processor")
public class TransactionProcessorEndPoint {

	@Autowired @Qualifier("txProcessorForCsvData")
	private TransactionProcessor txProcessorForCsvData;
	@Autowired @Qualifier("txProcessorForXmlData")
	private TransactionProcessor txProcessorForXmlData;
	
	@Autowired 
	private FileOperations fileOperations;

	@PostMapping(value="/process_xml_data", consumes= {"multipart/form-data"})
	public ResponseEntity<String> processXmlTransaction(@RequestParam("file") MultipartFile multipart )
	{
		try {
			File csvFile = fileOperations.multipartToFile(multipart);
			txProcessorForXmlData.importTransactions(new FileInputStream(csvFile));
			fileOperations.deleteFile(csvFile);

			return ResponseEntity.status(HttpStatus.OK).body("XML Transaction list has been imported to server Successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("XML transaction processor encountered problem:"+e.getMessage());
		}

	}

	@PostMapping(value="/process_csv_data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> processCsvTransaction(@RequestParam("file") MultipartFile file )
	{
		try {
			File csvFile = fileOperations.multipartToFile(file);
			txProcessorForCsvData.importTransactions(new FileInputStream(csvFile));
			fileOperations.deleteFile(csvFile);

			return ResponseEntity.status(HttpStatus.OK).body("CSV Transaction list has been imported to server Successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("CSV transaction processor encountered problem:"+e.getMessage());
		}
	}

}
