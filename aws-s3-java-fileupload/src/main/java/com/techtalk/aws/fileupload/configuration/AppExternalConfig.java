/**
 * 
 */
package com.techtalk.aws.fileupload.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author techtalk
 *
 */
@Component
@PropertySource("file:${app.fileuploader.ext.prop.relativepath}${app.fileuploader.ext.prop.basename}")
@ConfigurationProperties("ext")
public class AppExternalConfig {

	private String accessKeyId;
	private String secretAccessKey;
	private String directoryPath;
	private String s3BucketName;
	
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getSecretAccessKey() {
		return secretAccessKey;
	}
	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}
	public String getDirectoryPath() {
		return directoryPath;
	}
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}
	public String getS3BucketName() {
		return s3BucketName;
	}
	public void setS3BucketName(String s3BucketName) {
		this.s3BucketName = s3BucketName;
	}
		
}
