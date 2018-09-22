/**
 * 
 */
package com.techtalk.aws.fileupload.services;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.techtalk.aws.fileupload.configuration.AppExternalConfig;

/**
 * @author techtalk
 *
 */
@Component
public class Scheduler {

	private static final Logger log= LoggerFactory.getLogger(Scheduler.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	private AppExternalConfig appExternalConfig;
	
	@Autowired
	private FileUploaderService fileUploaderService;
	
	@Scheduled(fixedRate=60000)
	public void checkFiles() throws IOException{
		log.debug("Scheduler starting at: "+ sdf.format(new Date()));
		checkDirectory(appExternalConfig.getDirectoryPath());
	}

	private boolean checkDirectory(String directoryPath) throws IOException {
		Boolean uploadStatus=false;
		
		String fileName=null;
		File dir = new File(directoryPath);
		
		File[] dir_contents = dir.listFiles();
		if (dir_contents.length > 0) {
			for(int i=0; i<dir_contents.length; i++) {
				File fileObj = null;
				if(dir_contents[i].getName().contains(".csv")) {
				fileName = System.currentTimeMillis()+"-"+ dir_contents[i].getName();
				log.debug("file name:"+fileName);
				log.debug("Full Path:"+ dir.getAbsolutePath()+"\\"+dir_contents[i].getName());
				fileObj = new File (dir.getAbsolutePath()+"\\"+dir_contents[i].getName());
				uploadStatus=fileUploaderService.uploadFileToS3(fileObj,fileName);
				}
				
				if(uploadStatus) {
					log.info("Uploaded successfully");
				}else {
					log.error("Upload FAILED");
				}
			}
		}
		
		return uploadStatus;
	}
	
}
