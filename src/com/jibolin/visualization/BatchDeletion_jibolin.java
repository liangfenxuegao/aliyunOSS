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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.jibolin.oss.SpecificFunctions_jibolin;

public class BatchDeletion_jibolin extends JFrame{
	private static final long serialVersionUID = 1L;//序列化
	
	//构造函数
	public BatchDeletion_jibolin() {
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		//设置顶部提示文字和主窗体的x值，y值，宽，高
		setTitle("批量删除");
		setBounds(510, 200, 450, 149);
		Container cp = getContentPane();//添加一个cp容器
		cp.setLayout(null);	//设置添加的cp容器为流布局管理器
		cp.setBackground(new Color(240,248,255));//设置背景为爱丽丝蓝

		//设置左侧前缀文字
		JLabel prefix=new JLabel("输入批量删除的文件名前缀(不输入将清空所有文件)");
		prefix.setBounds(10, 20, 500, 18);
		prefix.setFont(new Font("微软雅黑", Font.BOLD, 18));
		final JTextField prefixName = new JTextField();	//输入框
		prefixName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		prefixName.setBounds(12, 67, 200, 24);	//设置用户名框的x值，y值,宽，高
		
		cp.add(prefix);
		cp.add(prefixName);
		
		//确定按钮
		JButton jb =new JButton("确定");	//添加一个确定按钮
		jb.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jb.addActionListener(new ActionListener(){		//为确定按钮添加监听事件
			public void actionPerformed(ActionEvent arg0) {
				SpecificFunctions_jibolin specificFunctions = new SpecificFunctions_jibolin();
				try {
					specificFunctions.flexibleDeletion(prefixName.getText().trim(),null);//执行删除指定前缀的文件
					specificFunctions.addAllFilesToWindow();//刷新表格
					dispose();
					JOptionPane.showMessageDialog(null, "删除成功！");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "未知的原因导致删除异常", "警告",JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		jb.setBounds(232, 65, 80, 30);	//设置确定按钮的x值，y值，宽，高
		cp.add(jb);	//将确定按钮添加到cp容器中

		//重置按钮
		final JButton button = new JButton();
		button.setText("重置");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		button.addActionListener(new ActionListener(){		//为重置按钮添加监听事件
			//同时清空name、password的数据
			public void actionPerformed(ActionEvent arg0) {
				prefixName.setText("");
			}
		});

		button.setBounds(337, 65, 80, 30);//设置重置按钮的x值，y值，宽，高
		getContentPane().add(button);
	}
}
