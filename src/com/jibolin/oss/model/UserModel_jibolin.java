//用户登录信息
package com.jibolin.oss.model;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class UserModel_jibolin {
	String endpoint;//地域
	String accessKeyId;//key
	String accessKeySecret;//密钥
	String myBucket;//包名

	public UserModel_jibolin(String endpoint, String accessKeyId, String accessKeySecret, String myBucket) {
		super();
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.myBucket = myBucket;
	}
	//创建ossClient实例
	public OSS establishOSSClient() {
		OSS ossClient = new OSSClientBuilder().build(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
		return ossClient;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getMyBucket() {
		return myBucket;
	}
	public void setMyBucket(String myBucket) {
		this.myBucket = myBucket;
	}
	public UserModel_jibolin(String myBucket) {
		super();
		this.myBucket = myBucket;
	}

}
