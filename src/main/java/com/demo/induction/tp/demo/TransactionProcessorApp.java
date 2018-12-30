/**
 * 
 */
package com.demo.induction.tp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author suresh
 *
 */
@SpringBootApplication
@ComponentScan(basePackages= {"com.demo.induction.tp","com.demo.induction.tp.util"})
public class TransactionProcessorApp {

	/**
	 * 
	 */
	public TransactionProcessorApp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TransactionProcessorApp.class, args);

	}

}
