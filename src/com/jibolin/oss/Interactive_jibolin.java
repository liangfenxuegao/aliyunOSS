//设计失误导致的残废类，有时间应当移除这个类承载的所有功能然后删除此类，当然，这个类还是能实现一些CLI命令的
package com.jibolin.oss;

import com.jibolin.visualization.MyComboBox_jibolin;

public class Interactive_jibolin {
	static CommonOperation_jibolin commonOperation = new CommonOperation_jibolin();
	static SpecificFunctions_jibolin specificFunctions = new SpecificFunctions_jibolin();
	
	//设置登录信息
	public static String endpoint = "";//地域
	public static String accessKeyId = "";//key
	public static String accessKeySecret = "";//密钥
	public static String myBucket = "";//储存空间名
	
	//验证是否登录，若登录则更改MyComboBox_jibolin内的bucketsArray
	//这个方法已经不被调用
	public static void updateIfLoggedIn() {
		MyComboBox_jibolin.bucketsArray = specificFunctions.returnToAllSpaces();//获得当前登录用户下所有的储存空间名
	}
	
//	public static void main(String[] args) {
//		CommonOperation_jibolin commonOperation = new CommonOperation_jibolin();
//		SpecificFunctions_jibolin specificFunctions = new SpecificFunctions_jibolin();
//		
//		commonOperation.loginInformation();//显示登陆信息
//		commonOperation.enumeratedSpace();//列举储存空间名
//		System.out.println("请输入整数数字：");
//		Scanner in = new Scanner(System.in);
//		int command = in.nextInt();
//		switch (command) {
//			case 1:
//				commonOperation.uploadString("你好呀.txt","哈哈哈哈哈哈哈，嘿嘿嘿");break;//指定您要上传时的对象名和其内容
//			case 2:
//				commonOperation.streamingDownload("hello.txt");break;//参数用于指定要读取的对象，用String流解析文件内容
//			case 3:
//				commonOperation.downloadFile("你好呀.txt","D:\\cache\\你好呀.txt");break;//下载文件到本地，参数使用方法（目标文件名，本地路径+文件名），若存在同名文件则覆盖
//			case 4:
//				commonOperation.fileInformation("你好呀.txt");break;//查询目标文件的相关信息
//			case 5:
//				specificFunctions.upload_100_files("jibolin");break;//上传并生成100个文件，每个文件的名字为: xxxxxx001,至xxxxxxx100，xx是您填写的name参数
//			case 6:
//				specificFunctions.readAndOutputName();break;//列举当前存储空间下所有的文件并打印到控制台
//			case 7:
//				specificFunctions.deleteAllFiles();break;//删除你的BUCKET上的所有文件
//			case 8:
//				//删除部分文件，即您在deleteSomeFiles()内指定的文件
//				deleteSomeFiles();
//				specificFunctions.deleteSomeFiles();//执行删除命令
//				break;
//			case 9:
//				//指定删除文件的前缀和开始删除位置，可以配合使用。例如有jibolin1-100个文件，指定前缀jibolin，开始删除位置jibolin20，就能删除在jibolin20-100的所有文件
//				specificFunctions.flexibleDeletion("jibolin","jibolin20");break;
//			case 10:
//				commonOperation.uploadFile("2233娘是姐妹呢.jpeg","D:\\cache\\2233娘.jpeg");break;//上传文件到云，您需要命名上传后的对象名和提供上传文件的本地路径
//			default:
//				System.out.println("无效命令！");break;
//		}
//		commonOperation.shutdownOssClient();//关闭ossClient对话
//	}

}
