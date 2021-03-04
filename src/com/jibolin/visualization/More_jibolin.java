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

public class More_jibolin extends JFrame{
	private static final long serialVersionUID = 1L;

	public More_jibolin() {
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		//设置顶部提示文字和主窗体的x值，y值，宽，高
		setTitle("上传100个序列文件");
		setBounds(510, 200, 290, 149);
		Container cp = getContentPane();//添加一个cp容器
		cp.setLayout(null);	//设置添加的cp容器为流布局管理器
		cp.setBackground(new Color(240,248,255));//设置背景为爱丽丝蓝

		//设置左侧前缀文字
		JLabel text = new JLabel("输入批量要批量生成的文件序列");
		text.setBounds(10, 20, 500, 18);
		text.setFont(new Font("微软雅黑", Font.BOLD, 18));
		final JTextField name = new JTextField();	//输入框
		name.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		name.setBounds(12, 67, 150, 24);	//设置用户名框的x值，y值,宽，高
		
		cp.add(text);
		cp.add(name);
		
		//确定按钮
		JButton jb =new JButton("确定");	//添加一个确定按钮
		jb.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jb.addActionListener(new ActionListener(){		//为确定按钮添加监听事件
			public void actionPerformed(ActionEvent arg0) {
				SpecificFunctions_jibolin specificFunctions = new SpecificFunctions_jibolin();
				try {
					specificFunctions.upload_100_files(name.getText().trim());//填入要生成的文件名
					specificFunctions.addAllFilesToWindow();//刷新表格
					dispose();
					JOptionPane.showMessageDialog(null, "生成完毕！");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "未知的原因导致异常", "警告",JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		jb.setBounds(180, 63, 80, 30);	//设置确定按钮的x值，y值，宽，高
		cp.add(jb);	//将确定按钮添加到cp容器中
	}
}
