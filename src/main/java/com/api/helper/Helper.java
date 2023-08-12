package com.api.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.api.entity.Product;
import com.api.repo.ProductRepo;

@Component
public class Helper {

	@Autowired
    ProductRepo productRepo;
	
	ConcurrentHashMap<Integer, Boolean> duplicates = new ConcurrentHashMap<>();

	
	//check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }


  //convert excel to list of products
//    @Async
//    public CompletableFuture<List<Product>> convertExcelToListOfProduct(InputStream is) {
//        List<Product> list = new ArrayList<>();
//        
//        try {
//
//        	HashSet<Integer> ids = new HashSet<Integer>();
//            XSSFWorkbook workbook = new XSSFWorkbook(is);
//
//            XSSFSheet sheet = workbook.getSheet("Testsheet");
//
//            int rowNumber = 0;
//            Iterator<Row> iterator = sheet.iterator();
//
//            while (iterator.hasNext()) {
//                Row row = iterator.next();
//
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//
//                Iterator<Cell> cells = row.iterator();
//
//                int cid = 0;
//
//                Product p = new Product();
//
//                while (cells.hasNext()) {
//                    
//                	Cell cell = cells.next();
//                    int flag=0;
//                    switch (cid) {
//                        case 0:
//                        	if(cell.getCellType() != CellType.NUMERIC)
//                        	{
//                        		flag=1;
//                        	}
//                        	else
//                        	{	
//                        		Integer id = (int)cell.getNumericCellValue();
//                        		 
//                        		Optional<Product> prod = productRepo.findById(id);
//                                Product newProduct = prod.orElse(new Product());
//                                
//                                if(newProduct.getproductId() != null || ids.contains(id))
//                                {	
//                                	duplicates.put(rowNumber, true);
//                                	flag = 1;
//                                }
//                                else
//                                {
//                                	p.setproductId(id);
//                                	ids.add(id);
//                                }
//                              
//                            }
//                            break;
//                        case 1:
//                        	if(cell.getCellType() == CellType.STRING)
//                        		p.setproductName(cell.getStringCellValue());
//                            break;
//                        case 2:
//                        	if(cell.getCellType() == CellType.STRING)
//                        		p.setproductDesc(cell.getStringCellValue());
//                            break;
//                        case 3:
//                        	if(cell.getCellType() == CellType.NUMERIC)
//                        		p.setproductPrice((int)cell.getNumericCellValue());
//                            break;
//                        default:
//                            break;
//                    }
//                    cid++;
//                    if(flag==1)
//                    	break;
//
//                }
//                //checking if the p object is empty
//                
//                if(p.getproductId() != null)
//                list.add(p);
//                rowNumber++;
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return CompletableFuture.completedFuture(list);
//
//    }


    public List<Product> convertExcelToListOfProduct(InputStream is) {
        List<Product> list = new ArrayList<>();
        
        try {

        	HashSet<Integer> ids = new HashSet<Integer>();
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("Testsheet");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;

                Product p = new Product();

                while (cells.hasNext()) {
                    
                	Cell cell = cells.next();
                    int flag=0;
                    switch (cid) {
                        case 0:
                        	if(cell.getCellType() != CellType.NUMERIC)
                        	{
                        		flag=1;
                        	}
                        	else
                        	{	
                        		Integer id = (int)cell.getNumericCellValue();
                        		 
                        		Optional<Product> prod = productRepo.findById(id);
                                Product newProduct = prod.orElse(new Product());
                                
                                if(newProduct.getproductId() != null || ids.contains(id))
                                {	
                                	duplicates.put(rowNumber, true);
                                	flag = 1;
                                }
                                else
                                {
                                	p.setproductId(id);
                                	ids.add(id);
                                }
                              
                            }
                            break;
                        case 1:
                        	if(cell.getCellType() == CellType.STRING)
                        		p.setproductName(cell.getStringCellValue());
                            break;
                        case 2:
                        	if(cell.getCellType() == CellType.STRING)
                        		p.setproductDesc(cell.getStringCellValue());
                            break;
                        case 3:
                        	if(cell.getCellType() == CellType.NUMERIC)
                        		p.setproductPrice((int)cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                    if(flag==1)
                    	break;

                }
                //checking if the p object is empty
                
                if(p.getproductId() != null)
                list.add(p);
                rowNumber++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
//    @Async
//    public CompletableFuture<ByteArrayInputStream> validateExcelData(InputStream is) {
//    	
//        if(is == null)
//        	return null;
//        	
//        Workbook outputWorkbook= new XSSFWorkbook();
//            
//    	ByteArrayOutputStream out=new ByteArrayOutputStream();
//            
//    	try {
//
//
//               XSSFWorkbook inputWorkbook = new XSSFWorkbook(is);
//                
//        		
//               XSSFSheet inputSheet = inputWorkbook.getSheet("Testsheet");
//                
//               Sheet outputSheet = outputWorkbook.createSheet("REPORT_SHEET");
//               Row outRow = outputSheet.createRow(0);
//                
//               Cell outcell = outRow.createCell(0);
//    		   outcell.setCellValue("SL_NO");
//    			
//    		   Cell outcell2 = outRow.createCell(1);
//    		   outcell2.setCellValue("Error");
//
//               int rowNumber = 0;
//          
//               Iterator<Row> iterator = inputSheet.iterator();
//               int outrowIndex=1;
//               int rowsFilled=1;
//               String err;
//               
//               while (iterator.hasNext()) {
//            	   
//                   Row row = iterator.next();
//
//                   if (rowNumber == 0) {
//                        rowNumber++;
//                        continue;
//                   }
//
//                   Iterator<Cell> cells = row.iterator();
//
//                   int cid = 0;
//                    
//                   err="";
//                   
//                   Row dataRow = outputSheet.createRow(rowsFilled);
//                  
//                   while (cells.hasNext()) {
//                       Cell cell = cells.next();
//
//                       switch (cid) {
//                           case 0:
//                                if(cell.getCellType() != CellType.NUMERIC)
//                                {	
//                                	err=err.concat("Error in productId");
//                                }
//                                else if(duplicates.containsKey(outrowIndex))
//                                {
//                                	err = err.concat("Id already exist");
//                                }
//                                
//                                break;
//                            case 1:
//                            	if(cell.getCellType() != CellType.STRING)
//                                	err=err.concat("Error in productName,");
//                                break;
//                            case 2:
//                            	if(cell.getCellType() != CellType.STRING)
//                                	err=err.concat("Error in productDesc,");
//                                break;
//                            case 3:
//                                if(cell.getCellType() != CellType.NUMERIC)
//                                	err=err.concat("Error in productPrice");
//                                break;
//                            default:
//                            	break;
//                        }
//                        cid++;
//                    }
//                   if(!err.equals(""))
//                   {
//                	   dataRow.createCell(0).setCellValue(outrowIndex);
//                       dataRow.createCell(1).setCellValue(err);
//                       rowsFilled++;
//                   }
//                   else
//                   {
//                	   outputSheet.removeRow(dataRow);
//                   }
//                   outrowIndex++;
//                    
//                }
//                outputWorkbook.write(out);
//                inputWorkbook.close();
//    			return CompletableFuture.completedFuture(new ByteArrayInputStream(out.toByteArray()));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//            finally {
//    			
//            	try {
//    				outputWorkbook.close();
//    				out.close();
//    			} catch (IOException e) {
//    				e.printStackTrace();
//    				 System.out.println("Error occurred while closing the workbook");
//    			}
//            	
//    		}
//        	
//        	
//        }
    
public ByteArrayInputStream ValidateExcelData(InputStream is) {
    	
    if(is == null)
    	return null;
    	
    Workbook outputWorkbook= new XSSFWorkbook();
        
	ByteArrayOutputStream out=new ByteArrayOutputStream();
        
	try {


           XSSFWorkbook inputWorkbook = new XSSFWorkbook(is);
            
    		
           XSSFSheet inputSheet = inputWorkbook.getSheet("Testsheet");
            
           Sheet outputSheet = outputWorkbook.createSheet("REPORT_SHEET");
           Row outRow = outputSheet.createRow(0);
            
           Cell outcell = outRow.createCell(0);
		   outcell.setCellValue("SL_NO");
			
		   Cell outcell2 = outRow.createCell(1);
		   outcell2.setCellValue("Error");

           int rowNumber = 0;
      
           Iterator<Row> iterator = inputSheet.iterator();
           int outrowIndex=1;
           int rowsFilled=1;
           String err;
           
           while (iterator.hasNext()) {
        	   
               Row row = iterator.next();

               if (rowNumber == 0) {
                    rowNumber++;
                    continue;
               }

               Iterator<Cell> cells = row.iterator();

               int cid = 0;
                
               err="";
               
               Row dataRow = outputSheet.createRow(rowsFilled);
              
               while (cells.hasNext()) {
                   Cell cell = cells.next();

                   switch (cid) {
                       case 0:
                            if(cell.getCellType() != CellType.NUMERIC)
                            {	
                            	err=err.concat("Error in productId");
                            }
                            else if(duplicates.containsKey(outrowIndex))
                            {
                            	err = err.concat("Id already exist");
                            }
                            
                            break;
                        case 1:
                        	if(cell.getCellType() != CellType.STRING)
                            	err=err.concat("Error in productName,");
                            break;
                        case 2:
                        	if(cell.getCellType() != CellType.STRING)
                            	err=err.concat("Error in productDesc,");
                            break;
                        case 3:
                            if(cell.getCellType() != CellType.NUMERIC)
                            	err=err.concat("Error in productPrice");
                            break;
                        default:
                        	break;
                    }
                    cid++;
                }
               if(!err.equals(""))
               {
            	   dataRow.createCell(0).setCellValue(outrowIndex);
                   dataRow.createCell(1).setCellValue(err);
                   rowsFilled++;
               }
               else
               {
            	   outputSheet.removeRow(dataRow);
               }
               outrowIndex++;
                
            }
            outputWorkbook.write(out);
            inputWorkbook.close();
			return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
			
        	try {
				outputWorkbook.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				 System.out.println("Error occurred while closing the workbook");
			}
        	
		}
    	
    	
    }


}
