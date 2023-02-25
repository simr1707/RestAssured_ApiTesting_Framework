package com.employeeapi.utilities;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	public class Xls_Reader {
		public  String path;
		public  FileInputStream fis = null;
		public  FileOutputStream fileOut =null;
		private XSSFWorkbook workbook = null;
		private XSSFSheet sheet = null;
		private XSSFRow row   =null;
		private XSSFCell cell = null;
		
		public Xls_Reader(String path) {
			
			this.path=path;
			try {
				fis = new FileInputStream(path);
				workbook = new XSSFWorkbook(fis);
				sheet = workbook.getSheetAt(0);
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		// returns the row count in a sheet**
		public int getRowCount(String sheetName){
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return 0;
			else{
				sheet = workbook.getSheetAt(index);
				int number=sheet.getLastRowNum()+1;
				return number;
			}
		}
		
		// returns number of columns in a sheet	**
		public int getColumnCount(String sheetName){
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);

			if(row==null)
				return -1;

			return row.getLastCellNum();

		}
		
		// returns the data from a cell**
		public String getCellData(String sheetName, String colName, int rowNum) {
		    try {
		        if (rowNum <= 0)
		            return "";

		        int index = workbook.getSheetIndex(sheetName);
		        int colNum = -1;
		        if (index == -1)
		            return "";

		        sheet = workbook.getSheetAt(index);
		        Row headerRow = sheet.getRow(0);
		        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
		            Cell headerCell = headerRow.getCell(i);
		            if (headerCell.getStringCellValue().trim().equalsIgnoreCase(colName.trim())) {
		                colNum = i;
		                break;
		            }
		        }
		        if (colNum == -1)
		            return "";

		        sheet = workbook.getSheetAt(index);
		        Row dataRow = sheet.getRow(rowNum - 1);
		        if (dataRow == null)
		            return "";

		        Cell dataCell = dataRow.getCell(colNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		        if (dataCell == null)
		            return "";

		        if (dataCell.getCellType() == CellType.STRING)
		            return dataCell.getStringCellValue();
		        else if (dataCell.getCellType() == CellType.NUMERIC || dataCell.getCellType() == CellType.FORMULA) {
		            if (DateUtil.isCellDateFormatted(dataCell)) {
		                Date date = dataCell.getDateCellValue();
		                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		                return dateFormat.format(date);
		            } else {
		                return String.valueOf(dataCell.getNumericCellValue());
		            }
		        } else if (dataCell.getCellType() == CellType.BLANK)
		            return "";
		        else
		            return String.valueOf(dataCell.getBooleanCellValue());
		    } catch (Exception e) {
		        e.printStackTrace();
		        return "row " + rowNum + " or column " + colName + " does not exist in xls";
		    }
		}

		
		// returns the data from a cell**
		public String getCellData(String sheetName, int colNum, int rowNum) {
		    try {
		        if (rowNum <= 0) {
		            return "";
		        }

		        int index = workbook.getSheetIndex(sheetName);

		        if (index == -1) {
		            return "";
		        }

		        sheet = workbook.getSheetAt(index);
		        row = sheet.getRow(rowNum - 1);
		        if (row == null) {
		            return "";
		        }
		        cell = row.getCell(colNum);
		        if (cell == null) {
		            return "";
		        }

		        CellType cellType = cell.getCellType();
		        if (cellType == CellType.STRING) {
		            return cell.getStringCellValue();
		        } else if (cellType == CellType.NUMERIC || cellType == CellType.FORMULA) {

		            String cellText = String.valueOf(cell.getNumericCellValue());

		            return cellText;
		        } else if (cellType == CellType.BLANK) {
		            return "";
		        } else {
		            return String.valueOf(cell.getBooleanCellValue());
		        }
		    } catch (Exception e) {

		        e.printStackTrace();
		        return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		    }
		}

		
		// returns true if data is set successfully else false
		public boolean setCellData(String sheetName,String colName,int rowNum, String data){
			try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(index);
			

			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);

		    // cell style
		    CellStyle cs = workbook.createCellStyle();
		    cs.setWrapText(true);
		    cell.setCellStyle(cs);
		    cell.setCellValue(data);

		    fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		
		// returns true if data is set successfully else false
		public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
			//System.out.println("setCellData setCellData******************");
			try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(index);
			//System.out.println("A");
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum=i;
			}
			
			if(colNum==-1)
				return false;
			sheet.autoSizeColumn(colNum); //simran
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);
				
		    cell.setCellValue(data);
		    XSSFCreationHelper createHelper = workbook.getCreationHelper();

		    //cell style for hyperlinks
		    //by default hypelrinks are blue and underlined
		    CellStyle hlink_style = workbook.createCellStyle();
		    XSSFFont hlink_font = workbook.createFont();
		    hlink_font.setUnderline(XSSFFont.U_SINGLE);
		    hlink_font.setColor(IndexedColors.BLUE.getIndex());
		    hlink_style.setFont(hlink_font);
		    //hlink_style.setWrapText(true);

		    XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
		    link.setAddress(url);
		    cell.setHyperlink(link);
		    cell.setCellStyle(hlink_style);
		      
		    fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		
		
		
		// returns true if sheet is created successfully else false
		public boolean addSheet(String  sheetname){		
			
			FileOutputStream fileOut;
			try {
				 workbook.createSheet(sheetname);	
				 fileOut = new FileOutputStream(path);
				 workbook.write(fileOut);
			     fileOut.close();		    
			} catch (Exception e) {			
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// returns true if sheet is removed successfully else false if sheet does not exist
		public boolean removeSheet(String sheetName){		
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			
			FileOutputStream fileOut;
			try {
				workbook.removeSheetAt(index);
				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
			    fileOut.close();		    
			} catch (Exception e) {			
				e.printStackTrace();
				return false;
			}
			return true;
		}
		// returns true if column is created successfully
		public boolean addColumn(String sheetName,String colName){
			//System.out.println("**************addColumn*********************");
			
			try{				
				fis = new FileInputStream(path); 
				workbook = new XSSFWorkbook(fis);
				int index = workbook.getSheetIndex(sheetName);
				if(index==-1)
					return false;
				
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			sheet=workbook.getSheetAt(index);
			
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			
			//cell = row.getCell();	
			//if (cell == null)
			//System.out.println(row.getLastCellNum());
			if(row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());
		        
		        cell.setCellValue(colName);
		        cell.setCellStyle(style);
		        
		        fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
			    fileOut.close();		    

			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			
			return true;
			
			
		}
		// removes a column and all the contents
		public boolean removeColumn(String sheetName, int colNum) {
			try{
			if(!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			sheet=workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			style.setFillPattern(FillPatternType.NO_FILL);
			
		    
		
			for(int i =0;i<getRowCount(sheetName);i++){
				row=sheet.getRow(i);	
				if(row!=null){
					cell=row.getCell(colNum);
					if(cell!=null){
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
	  // find whether sheets exists	
		public boolean isSheetExist(String sheetName){
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1){
				index=workbook.getSheetIndex(sheetName.toUpperCase());
					if(index==-1)
						return false;
					else
						return true;
			}
			else
				return true;
		}
		
		//String sheetName, String testCaseName,String keyword ,String URL,String message
		public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
			//System.out.println("ADDING addHyperLink******************");
			
			url=url.replace('\\', '/');
			if(!isSheetExist(sheetName))
				 return false;
			
		    sheet = workbook.getSheet(sheetName);
		    
		    for(int i=2;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
		    		//System.out.println("**caught "+(i+index));
		    		setCellData(sheetName, screenShotColName, i+index, message,url);
		    		break;
		    	}
		    }


			return true; 
		}
		public int getCellRowNum(String sheetName,String colName,String cellValue){
			
			for(int i=2;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
		    		return i;
		    	}
		    }
			return -1;
			
		}
			
	}
