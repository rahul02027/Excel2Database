package com.api.helper;

import java.util.*;
import com.api.entity.NewProduct;
import java.io.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class NewHelper {
	
	public static String[] HEADERS= {
		"productId",
		"productName",
		"productDesc",
		"productPrice"
	};
	
	public static String SHEET_NAME="output_sheet";
	
	public static ByteArrayInputStream dataToExcel(List<NewProduct> list) {
		
		Workbook workbook= new XSSFWorkbook();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		
		
		try {
		    
			
			Sheet sheet = workbook.createSheet(SHEET_NAME);
			
			Row row = sheet.createRow(0);
			
			for(int i = 0; i < HEADERS.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(HEADERS[i]);
			}
			
		    
			int rowIndex=1;
			for(NewProduct np:list) {
				
				Row dataRow = sheet.createRow(rowIndex);
				
					dataRow.createCell(0).setCellValue(np.getId());
					dataRow.createCell(1).setCellValue(np.getName());
					dataRow.createCell(2).setCellValue(np.getDescription());
					//dataRow.createCell(3).setCellValue(np.getPrice());
					
					if (np.getPrice() != null) {
				        dataRow.createCell(3).setCellValue(np.getPrice().intValue());
				    } else {
				        dataRow.createCell(3).setCellValue(0); // Set a default value or handle null price case as per your requirement
				    }
				
				rowIndex++;
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("fail to import data excel");
			return null;
		}
		finally {
			try {
				workbook.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				 System.out.println("Error occurred while closing the workbook");
			}
			
		}
		
	}
}
