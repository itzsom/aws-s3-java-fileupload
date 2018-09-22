package com.techtalk.aws.fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author techtalk
 *
 */

@SpringBootApplication
@ComponentScan("com.techtalk.aws")
@EnableScheduling
public class AwsS3JavaFileuploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsS3JavaFileuploadApplication.class, args);
	}
}
