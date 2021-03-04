//用于登录阿里云OSS账号的对话框
package com.jibolin.visualization;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.jibolin.oss.Interactive_jibolin;

public class LoginJDialog_jibolin extends JFrame{
	private static final long serialVersionUID = 1L;//序列化

	public static String endpoint;
	public static String accessKeyId;
	public static String accessKeySecret;
	
	public static boolean success = false;//返回是否登录成功
	
	//构造函数
	public LoginJDialog_jibolin() {
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		//设置顶部提示文字和主窗体的x值，y值，宽，高
		setTitle("请登录");
		setBounds(510, 200, 380, 250);
		Container cp = getContentPane();	//添加一个cp容器
		cp.setLayout(null);	//设置添加的cp容器为流布局管理器
		cp.setBackground(new Color(240,255,240));//设置背景为极淡绿
		
		//设置左侧外网访问文字
		JLabel endPoint = new JLabel("外网访问域名：");
		endPoint.setBounds(10, 13, 200, 18);
		endPoint.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		final JTextField endPointName = new JTextField();//外网访问域名框
		endPointName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		endPointName.setBounds(150, 10, 200, 24);//设置外网访问域名框的x值，y值,宽，高

		//设置左侧用户名文字
		JLabel jl=new JLabel("AccessKeyId：");
		jl.setBounds(10, 53, 200, 18);
		jl.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		final JTextField name = new JTextField();	//用户名框
		name.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		name.setBounds(150, 50, 200, 24);	//设置用户名框的x值，y值,宽，高

		//设置左侧密码文字
		JLabel jl2=new JLabel("AccessKeySecret：");
		jl2.setBounds(10, 93, 200, 18);
		jl2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		final JPasswordField password=new JPasswordField();	//密码框：为加密的***
		password.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		password.setBounds(150, 90, 200, 24);	//设置密码框的x值，y值,宽，高
		//将jl、name、endPoint、jl2、password、endPointName添加到容器cp中
		cp.add(jl);
		cp.add(name);
		cp.add(endPoint);
		cp.add(jl2);
		cp.add(password);
		cp.add(endPointName);
		
		//确定按钮
		JButton jb =new JButton("确定登录");	//添加一个确定按钮
		jb.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jb.addActionListener(new ActionListener(){		//为确定按钮添加监听事件
			public void actionPerformed(ActionEvent arg0) {
				if(endPointName.getText().trim().length()==0 || name.getText().trim().length()==0 || new String(password.getPassword()).trim().length()==0){
					JOptionPane.showMessageDialog(null, "登录值不允许为空");
					return;
				}else {
					endpoint = endPointName.getText().trim();//填入域名
					accessKeyId = name.getText().trim();//填入AccessKeyId
					accessKeySecret = new String(password.getPassword()).trim();//填入accessKeySecret				
					try {
						Interactive_jibolin.updateIfLoggedIn();//尝试获取当前登录用户的所有储存空间名
						success = true;//返回登录成功
						dispose();
						JOptionPane.showMessageDialog(null, "登录成功！");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "登录异常，可能您输入的信息有误", "警告",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		jb.setBounds(10, 160, 150, 30);	//设置确定按钮的x值，y值，宽，高
		cp.add(jb);	//将确定按钮添加到cp容器中

		//重置按钮
		final JButton button = new JButton();
		button.setText("重置信息");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		button.addActionListener(new ActionListener(){		//为重置按钮添加监听事件
			//同时清空name、password的数据
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成方法存根
				endPointName.setText("");
				name.setText("");
				password.setText("");
			}
		});

		button.setBounds(200, 160, 150, 30);//设置重置按钮的x值，y值，宽，高
		getContentPane().add(button);
	}
}
