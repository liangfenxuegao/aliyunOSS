package com.jibolin.visualization;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class MyComboBox_jibolin extends AbstractListModel<String> implements ComboBoxModel<String>{
	private static final long serialVersionUID = 1L;
	
	public static String selectedanItem = null;
	public static String[] bucketsArray =  {"                         "};//初始化一群空格，防报错
	
	//获得数组大小
	@Override
	public int getSize() {
		return bucketsArray.length;
	}

	//使用索引获得值
	@Override
	public String getElementAt(int index) {
		return bucketsArray[index];
	}

	//设置当前选项值
	@Override
	public void setSelectedItem(Object anItem) {
		selectedanItem = (String)anItem;
	}

	//得到当前选项值
	@Override
	public Object getSelectedItem() {
		return selectedanItem;
	}
	
}
