package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelfileUtil 
{
   Workbook wb;
   
   public ExcelfileUtil() throws Throwable
   {
	   FileInputStream fis=new FileInputStream("C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\TestInputs\\InputSheet.xlsx");
	   wb=WorkbookFactory.create(fis);
   }  
	   public int rowCount(String Sheetname)
	   {
		return wb.getSheet(Sheetname).getLastRowNum();
		
	   }
	   
	   public int colCount(String Sheetname,int row)
	   {
		   return wb.getSheet(Sheetname).getRow(row).getLastCellNum();
	   }
	   
	   public String getData(String Sheetname,int row,int column)
	   {
		   String data="";
		   if(wb.getSheet(Sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		   {
			   wb.getSheet(Sheetname).getRow(row).getCell(column).getNumericCellValue();
			   int celldata=(int)wb.getSheet(Sheetname).getRow(row).getCell(column).getNumericCellValue();
			   data=String.valueOf(celldata);
			   			   
		   }
		   else
		   {
			   data=wb.getSheet(Sheetname).getRow(row).getCell(column).getStringCellValue();
		   }
		   return data;
	   }
	  
       public void setData(String Sheetname,int row,int column,String status) throws Throwable
       {
    	   Sheet sh=wb.getSheet(Sheetname);
    	   Row rownum=sh.getRow(row);
    	   Cell cell=rownum.createCell(column);
    	   cell.setCellValue(status);
    	   
    	   if (status.equalsIgnoreCase("pass"))
    	   {
			    
    		   CellStyle style=wb.createCellStyle();
    		   
    		   Font font=wb.createFont();
    		   font.setColor(IndexedColors.GREEN.getIndex());
    		   font.setBold(true);
    		   
    		   style.setFont(font);
    		   rownum.getCell(column).setCellStyle(style);
    		   
    		   
		   }
    	   else
    		   if (status.equalsIgnoreCase("fail")) 
    		   {
			        
    			   CellStyle style=wb.createCellStyle();
        		   
        		   Font font=wb.createFont();
        		   font.setColor(IndexedColors.RED.getIndex());
        		   font.setBold(true);
        		   
        		   style.setFont(font);
        		   rownum.getCell(column).setCellStyle(style);
        		      			   
		       }
    	   else 
    		   if (status.equalsIgnoreCase("Not Executed")) 
    		   {
			
    			   CellStyle style=wb.createCellStyle();
        		   
        		   Font font=wb.createFont();
        		   font.setColor(IndexedColors.BLUE.getIndex());
        		   font.setBold(true);
        		   
        		   style.setFont(font);
        		   rownum.getCell(column).setCellStyle(style);
        		       			   	   
		       }
    	   
    	   FileOutputStream fos=new FileOutputStream("C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\TestOutputs\\OutputSheet.xlsx");
    	   wb.write(fos);
    	   fos.close();
       }
     
}
