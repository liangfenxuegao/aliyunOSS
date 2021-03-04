//该类提供丰富的基础操作实现方法
package com.jibolin.oss;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.jibolin.oss.model.UserModel_jibolin;
import com.jibolin.oss.util.GetObjectProgressListener;
import com.jibolin.oss.util.PutObjectProgressListener;
import com.jibolin.visualization.LoginJDialog_jibolin;
import com.jibolin.visualization.MyComboBox_jibolin;

public class CommonOperation_jibolin {
	
	public static long fileSize;//文件大小
	public static Date modificationDate;//文件最后修改日期
	
	//填入信息
	UserModel_jibolin userModel = new UserModel_jibolin(
			LoginJDialog_jibolin.endpoint,
			LoginJDialog_jibolin.accessKeyId,
			LoginJDialog_jibolin.accessKeySecret,
			MyComboBox_jibolin.selectedanItem
	);
	OSS ossClient = userModel.establishOSSClient();//创建OSSClient实例
	
	//关闭OSSClient
	public void shutdownOssClient() {
		ossClient.shutdown();
	}
	//显示登录信息
	public void loginInformation() {
		AccessControlList acl = ossClient.getBucketAcl(userModel.getMyBucket());//获取当前登录用户的访问权限等级
		String location = ossClient.getBucketLocation(userModel.getMyBucket());//获取当前存储空间的地域
		System.out.println("您当前的访问地域为："+location);//输出当前的访问地域
		System.out.println("您当前的访问权限为："+acl.toString());//登录时就通知当前登录用户的访问权限
	}
	//列举存储空间，返回buckets列表，并将其名输出到控制台
	public List<Bucket> enumeratedSpace() {
		System.out.println("当前登录用户名下全部的Buckets：");
		// 列举存储空间。
		List<Bucket> buckets = ossClient.listBuckets();
		for (Bucket bucket : buckets) {
		    System.out.println(" - " + bucket.getName());
		}
		return buckets;
	}

	//获取文件的部分元信息
	public void fileInformation(String objectName) {
		SimplifiedObjectMeta objectMeta = ossClient.getSimplifiedObjectMeta(userModel.getMyBucket(), objectName);
		CommonOperation_jibolin.fileSize = objectMeta.getSize();
		CommonOperation_jibolin.modificationDate = objectMeta.getLastModified();
		System.out.println("文件大小："+objectMeta.getSize());
		System.out.println("最后修改时间："+objectMeta.getLastModified());
	}
	//流式上传字符串，myText指定目标对象，content用于输入字符串
	public void uploadString(String myText,String content) {
		//带进度条的上传
		try {            
			ossClient.putObject(new PutObjectRequest(userModel.getMyBucket(), myText, new ByteArrayInputStream(content.getBytes())));
			System.out.println("上传字符串完毕！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传字符串失败！");
		}
	}
	//上传文件到云，您需要命名上传后的对象名和提供上传文件的本地路径
	public void uploadFile(String objectName,String localDirectory) {
        // 带进度条的上传。
		 try {
			// 带进度条的上传。
            ossClient.putObject(new PutObjectRequest(userModel.getMyBucket(), objectName, new File(localDirectory)).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener()));
		 } catch (Exception e) {
	         e.printStackTrace();
	         System.out.println("上传文件失败！");
	     }
	}
	
	//流式下载，objectName用于指定要读取的对象
	public void streamingDownload(String objectName) {
		OSSObject ossObject = ossClient.getObject(userModel.getMyBucket(), objectName);//ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流
		// 读取文件内容
		System.out.println("对象内容：");
		BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
		while (true) {
		    String line;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取意外中断！");
				line = null;
			}
		    if (line == null) break;

		    System.out.println("\n" + line);
		}
		//数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作
		while(true) {
			try {
				reader.close();
				break;//未出现异常该语句将执行
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("关闭读取失败，正在重试……");
			}
		}
	}
	//下载文件到本地，使用时需指定要下载的对象和下载文件所放的本地路径
	public void downloadFile(String downloadObject,String localDirectory) {
		//如果指定的本地路径文件存在同名文件则会覆盖该文件
		try {            
            // 带进度条的下载
            ossClient.getObject(new GetObjectRequest(userModel.getMyBucket(), downloadObject).
                    <GetObjectRequest>withProgressListener(new GetObjectProgressListener()),new File(localDirectory));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("下载失败！");
		}
	}
	
	
	//可以实现分页显示所有文件功能，可以指定前缀，以及从指定位置开始的参数。获取的信息添加均至sums
	public List<OSSObjectSummary> pagingDisplay(final String keyPrefix,String nextMarker) { 
		// 指定每页200个文件，每页最高1000
		final int maxKeys = 200;
		ObjectListing objectListing;

		do {
		    objectListing = ossClient.listObjects(new ListObjectsRequest(userModel.getMyBucket()).
		            withPrefix(keyPrefix).withMarker(nextMarker).withMaxKeys(maxKeys));

		    List<OSSObjectSummary> sums = objectListing.getObjectSummaries();

		    nextMarker = objectListing.getNextMarker();
		    
		    return sums;
		} while (objectListing.isTruncated());
							
	}
	//删除指定的文件，可以指定前缀，以及从指定位置开始删除的参数
	public List<String> deleteSpecifiedFile(boolean flexibleDeletion,final String keyPrefix,String nextMarker) {
		List<String> keys = new ArrayList<String>();
		
		if(flexibleDeletion == true) {
			List<OSSObjectSummary> sums	 = pagingDisplay(keyPrefix,nextMarker);//列举所有文件，并返回sums数组
			for (OSSObjectSummary s : sums) {
				keys.add(s.getKey());//将所有列举到的文件名都添加到keys容器内
			}
		}else {
			HashSet<String> set = SpecificFunctions_jibolin.deleteSelectedFile();//获得添加的文件名
			for (String s : set) {
				keys.add(s);//将所有列举到的文件名都添加到keys容器内
			}
		}
		//如果keys已经有文件名，就会执行删除
		DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(userModel.getMyBucket()).withKeys(keys));
		List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
		for(String d : deletedObjects) {
			System.out.println("您成功删除以下文件（或不存在）：");
			System.out.println("\t" + d);
		}
		
		return keys;
	}

}
