//可视化Interactive，便于普通用户操作软件。
//警告！！！本GUI实现有兼容问题，如果要实现BeautyEye的外观Java版本最好不要超过1.8
//如果软件运行出错请删除main函数的BeautyEye调用代码
package com.jibolin.visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.jibolin.oss.CommonOperation_jibolin;
import com.jibolin.oss.SpecificFunctions_jibolin;

public class GUI_jibolin extends JFrame{
	private static final long serialVersionUID = 1L;//序列化(持久化)
	
	//创建JFrame窗体
	private void CreateJFrame(String title) {
		JFrame frame=new JFrame("阿里云OSS浏览器");//创建Frame窗口
		JPanel jpanel = new JPanel();//创建JPanel对象，JPanel可以放在JFrame中

        JButton loginButton = new JButton("登录OSS管理账号");
        JLabel choiceSpace = new JLabel("          登录后请选择登录空间(否则程序无法正常运行)：");//创建JLabel对象
        JComboBox<String> jc = new JComboBox<>(new MyComboBox_jibolin());//下拉列表
        JLabel fileBrowseWindow = new JLabel("文件浏览视窗");//创建JLabel对象
        JTable table = FileWindow_jibolin.createFileWindow();//添加文件浏览视窗表格
        JButton refresh = new JButton("刷新列表");
        JButton uploadFiles = new JButton("上传文件");
        JButton deleteSelected = new JButton("删除所选");
        JButton batchDeletion = new JButton("批量删除");
        JButton downloadSelected = new JButton("下载所选");
        JButton more = new JButton("更多功能");
        
        
        loginButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        choiceSpace.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        fileBrowseWindow.setFont(new Font("微软雅黑", Font.BOLD, 18));
        jc.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        more.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        refresh.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        deleteSelected.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        downloadSelected.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        uploadFiles.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        batchDeletion.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        
        JScrollPane scrollPane = new JScrollPane(table);//把表格放到滚动面板中（表头将自动添加到滚动面板顶部）
        
       
        
        jpanel.add(loginButton);
        jpanel.add(choiceSpace);
        jpanel.add(jc);//添加下拉列表
        jpanel.add(fileBrowseWindow);
        jpanel.add(scrollPane);//添加文件视窗滚动面板
        jpanel.add(refresh);
        jpanel.add(uploadFiles);
        jpanel.add(deleteSelected);
        jpanel.add(batchDeletion);
        jpanel.add(downloadSelected);
        jpanel.add(more);
        jpanel.setBackground(new Color(255,250,240));//设置背景为极淡黄
        
        //当登录按钮被按下后，显示登录对话框
  		loginButton.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				new LoginJDialog_jibolin();//显示登录对话框
  			}
  		});
  		//刷新文件列表
  		refresh.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				SpecificFunctions_jibolin specificFunctions = new SpecificFunctions_jibolin();
  				specificFunctions.addAllFilesToWindow();
  				System.out.println("通知：文件列表被刷新");
  			}
  		});
  		//上传文件
  		uploadFiles.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				CommonOperation_jibolin commonOperation = new CommonOperation_jibolin();//实例化基础操作对象
  				JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
  				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//文件和文件夹都可以选
  				fileChooser.setMultiSelectionEnabled(true);//是否允许多选
  				//判断用户点击的是否为打开按钮
  				int i = fileChooser.showOpenDialog(getContentPane());
  				if(i == JFileChooser.APPROVE_OPTION) {
  					File[] selectedFile = fileChooser.getSelectedFiles();//获得选中的文件对象到File数组
  					for(File f : selectedFile) {
  						commonOperation.uploadFile(f.getName(),f.getAbsolutePath());//上传文件名和指定文件所在路径
  						//设置文件名及其信息
  						SpecificFunctions_jibolin.updateFilename = f.getName();//文件名
  						commonOperation.fileInformation(f.getName());//更新文件信息
  						FileWindow_jibolin.addRow();//添加表格行
  					}
  				}
  			}
  		});
  		//删除所选文件
  		deleteSelected.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				FileWindow_jibolin.deleteSelected();
  			}
  		});
  		//当登录按钮被按下后，显示批量删除对话框
  		batchDeletion.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				new BatchDeletion_jibolin();//显示批量删除对话框
  			}
  		});
  		//下载所选
  		downloadSelected.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				CommonOperation_jibolin commonOperation = new CommonOperation_jibolin();//实例化基础操作对象
  		        JFileChooser fileChooser = new JFileChooser();// 创建一个默认的文件选取器
  		        fileChooser.setSelectedFile(new File(FileWindow_jibolin.returnFileName()));//设置打开文件选择框后默认输入的文件名
  		        int result = fileChooser.showSaveDialog(frame);// 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
  		        //如果点击了"保存"则
  		        if (result == JFileChooser.APPROVE_OPTION) {
  		            File file = fileChooser.getSelectedFile();// 获取选择的保存路径
  		            commonOperation.downloadFile(FileWindow_jibolin.returnFileName(),file.getAbsolutePath());
  		            System.out.println("文件保存到: " + file.getAbsolutePath());
  		        }
  			}
  		});
  		//显示更多功能
  		more.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				new More_jibolin();//显示更多功能对话框
  			}
  		});
        
  		frame.add(jpanel);//将jpanel添加到frame容器内
  		
  		
		//窗口设置
		frame.setVisible(true);//设为可见
        frame.setBounds(300, 200, 800, 480);//设置位置和大小
        frame.setResizable(false);//不允许调节窗口大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口时关闭进程
	}
	
	public static void main(String[] args) {
		GUI_jibolin gui = new GUI_jibolin();
		
		//尝试将Java程序界面更换成BeautyEye的外观，如果出现兼容问题请删除(或注释)本try{}catch{}语句块！！！！！！
		try{
			//设置本属性将改变窗口边框样式定义
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
	        UIManager.put("RootPane.setupButtonVisible", false);
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	    }
	    catch(Exception e){
	        System.out.println("切换失败");
	    }
		
		//为事件分发线程预订一个工作:创建并显示本程序的GUI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui.CreateJFrame("阿里云OSS浏览器");
			}
		});
		
	}
}
