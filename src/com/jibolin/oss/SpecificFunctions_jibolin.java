//通过对CommonOperation的调用实现进阶功能
package com.jibolin.oss;

import java.util.HashSet;
import java.util.List;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObjectSummary;
import com.jibolin.visualization.FileWindow_jibolin;

public class SpecificFunctions_jibolin {
	public static String updateFilename;//刷新文件表格时用到的变量
	
	CommonOperation_jibolin commonOperation = new CommonOperation_jibolin();//实例化基础操作对象

//完成老师的大数据作业
	//功能1：上传并生成100个文件
	public void upload_100_files(String name) {
		for(int i=1;i<101;i++) {
			commonOperation.uploadString(name+i+".txt","做人是有极限的，所以我不做人了！鲁迅如是言道。");//上传字符串的方法
		}
	}
	//功能2：读取你的BUCKET上的所有文件的名字，并在控制台中打印输出这些文件名
	public void readAndOutputName() {
		List<OSSObjectSummary> sums	 = commonOperation.pagingDisplay(null,null);//列举所有文件，并返回数组
		for (OSSObjectSummary s : sums) {
	        System.out.println("\t" + s.getKey());
	    }
	}
	//功能3：删除你的BUCKET上的所有文件
	public void deleteAllFiles() {
		commonOperation.deleteSpecifiedFile(true,null,null);//true表示切换为灵活删除方式
	}
	
//其他进阶功能
	//更加灵活的删除方法，可以删除指定前缀的文件及指定文件名后的所有文件
	public void flexibleDeletion(final String keyPrefix,String nextMarker) {
		commonOperation.deleteSpecifiedFile(true,keyPrefix,nextMarker);//true表示切换为灵活删除方式
	}
	//获得当前登录用户下的储存空间，并以String[]的方式返回
	public String[] returnToAllSpaces() {
		List<Bucket> buckets = commonOperation.enumeratedSpace();//获得buckets列表，里面储存有当前登录用户下所有的储存空间名
		String[] bucketsArray = new String[buckets.size()];//初始化数组大小
		//将buckets内的数据添加到bucketsArray内
		int i=0;
		for (Bucket bk : buckets) {
			i++;
			//经过长时间的排查，可能是阿里云提供的JDK有问题，或者是for-each循环的问题，第一次循环会获得空值到bucketsArray[0]，这里通过if语句解决
			if(bk.getName() != null) {
				bucketsArray[i-1] = bk.getName();
			}
		}
		return bucketsArray;
	}
	//刷新文件浏览视窗的所有文件名及其信息
	public void addAllFilesToWindow() {
		FileWindow_jibolin.empty();//清空表格内容
		List<OSSObjectSummary> sums	 = commonOperation.pagingDisplay(null,null);//列举所有文件，并返回列表
		//将数据添加到rowData
		for (OSSObjectSummary s : sums) {
			if(s.getKey() != null) {
				commonOperation.fileInformation(s.getKey());//更新文件信息
				updateFilename = s.getKey();//更新文件名
				FileWindow_jibolin.addRow();//调用添加信息方法
			}
	    }
	}
	
	static String singleFileName;//单选到的文件名
	
	//删除用户的单选文件,用于执行删除命令
	public void deleteSingleFile(String getName) {
		singleFileName = getName;//输入文件名
		deleteSelectedFile();//执行添加文件名到set
		commonOperation.deleteSpecifiedFile(false,null,null);//非灵活方式删除，即删除set内的所有文件名
	}
	//删除选中文件，由于是集合方式，所以能拓展为多选删除模式
	public static HashSet<String> deleteSelectedFile() {
		HashSet<String> set= new HashSet<String>();
		//您可以自由的选择想要删除的文件，按照下面的模板set.add("");就可以自由指定文件名
		set.add(singleFileName);
		
		return set;
	}
}
