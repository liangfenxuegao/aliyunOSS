//显示储存空间中所有的对象
package com.jibolin.visualization;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.jibolin.oss.CommonOperation_jibolin;
import com.jibolin.oss.SpecificFunctions_jibolin;

import java.awt.*;

public class FileWindow_jibolin {
	
    // 表格所有行数据
	public static Object[][] tableValues = {};
	
    static String[] columnNames = {"对象名称", "字节大小", "修改日期"};//表头（列名）
    static JTable table = new JTable(tableValues, columnNames);//创建一个表格，指定所有行数据和表头
	
	private static DefaultTableModel model;//表格模型
	
	//清空表格内容
	public static void empty() {
		while(model.getRowCount()>0){
			model.removeRow(model.getRowCount()-1);
		}
	}
	//用于刷新列表的添加方法
	public static void addRow() {
		Object rowData[] = {SpecificFunctions_jibolin.updateFilename,CommonOperation_jibolin.fileSize,CommonOperation_jibolin.modificationDate};
		model.addRow(rowData);//在表格模型中增加一行内容(文本框内容)
	}
	//删除所选
	public static String deleteSelected() {
		SpecificFunctions_jibolin specificFunctions = new SpecificFunctions_jibolin();//实例化
		
		int selectedRow = table.getSelectedRow();//获取被选中行的索引
		String getName = table.getValueAt(selectedRow, 0).toString();//读取你获取行号的某一列的值（也就是字段）
		specificFunctions.deleteSingleFile(getName);//删除云上的文件
		
		System.out.println("删除对象："+getName);
		//删除表格中选中的行
		if (selectedRow != -1) {
			model.removeRow(selectedRow);
		}
		return getName;
	}
	//返回所选文件名
	public static String returnFileName() {
		int selectedRow = table.getSelectedRow();//获取被选中行的索引
		String getName = table.getValueAt(selectedRow, 0).toString();//读取你获取行号的某一列的值（也就是字段）
		return getName;
	}

	//创建文件浏览视窗，返回表格对象
	public static JTable createFileWindow() {
		model = new DefaultTableModel(tableValues, columnNames);//设置模型
        table = new JTable(model);//引用模型，或table.setModel(model);

        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 16));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(new Color(218,165,32));                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(true);               // 设置允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(true);             // 设置允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 第一列列宽设置为40
        table.getColumnModel().getColumn(0).setPreferredWidth(75);

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(700, 260));

        return table;
	}
}
