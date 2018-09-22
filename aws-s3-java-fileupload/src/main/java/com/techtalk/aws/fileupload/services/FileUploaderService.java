/**
 * 
 */
package com.techtalk.aws.fileupload.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.impl.client.BasicCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.techtalk.aws.fileupload.configuration.AppExternalConfig;

/**
 * @author techtalk
 *
 */
@Component
public class FileUploaderService {

	private static final Logger log= LoggerFactory.getLogger(FileUploaderService.class);
	@Autowired
	private AppExternalConfig appExternalConfig;
	
	public boolean uploadFileToS3 (File fileObj, String fileName) throws IOException {
		byte[] bytesArray = new byte [(int) fileObj.length()]; 
		InputStream inStream = new FileInputStream(fileObj);
		AWSCredentials credentials = new BasicAWSCredentials(appExternalConfig.getAccessKeyId(), appExternalConfig.getSecretAccessKey());
		
		AmazonS3 s3Client = AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)) // ok for POC
				//.withCredentials(new EnvironmentVariableCredentialsProvider()) // recomended for actual implementation
				.withRegion(Regions.US_EAST_1) // set your region based on your s3 bucket
				.build();
		
		for(Bucket buk: s3Client.listBuckets()) {
			log.info("bucket name:"+buk.getName());
		}
		
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentDisposition("text/csv");
			metadata.setContentLength(bytesArray.length);
			s3Client.putObject(new PutObjectRequest(appExternalConfig.getS3BucketName(), fileName, inStream, metadata));
		}catch (AmazonServiceException ase) {
			ase.printStackTrace();
		}catch (SdkClientException sdkc) {
			sdkc.printStackTrace();
		}
		
		return true;
	}
	
}
