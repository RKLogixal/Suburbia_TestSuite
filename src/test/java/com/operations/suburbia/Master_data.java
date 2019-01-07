package com.operations.suburbia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.operations.Common.Xls_Reader;

public class Master_data {

	@DataProvider(name = "Fetch_Master_data")
	public Object passdata()  {
		Object[][]Sheet_data = null;
		Object[][]Total_data=null;
		int Final_rows=0;
		int Final_columns=0;
		String Filepath="./Testcase_Input";
		String output_dir = null;
		File Master_file =	new File("./Input_files/Master_executors/Master_executor.xlsx");
		FileInputStream Master_inputStream = null;
		Xls_Reader Readexcel = new Xls_Reader(System.getProperty("user.dir")+"./Input_files/Master_executors/Master_executor.xlsx");
		try {
			Master_inputStream = new FileInputStream(Master_file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		XSSFWorkbook Master_Workbook = null;
		try {
			Master_Workbook = new XSSFWorkbook(Master_inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//int numberOfCells = 0;

		try {


			int rows_temp=0;	
			int cols_temp=0;
			int Total_Master_sheets=Readexcel.getNumberOfSheets(Master_Workbook);
			int Total_rows = 0;
			
			for (int k = 0; k <Total_Master_sheets; k++) {

				XSSFSheet sh1 = Master_Workbook.getSheetAt(k);
				int rows_currentsheet =Readexcel.getRowCountBySheetnumber(k);
				Total_rows=rows_temp+rows_currentsheet;
				rows_temp=Total_rows;


			}
			cols_temp=Readexcel.getColumnCountBySheetnumber(0);
			Total_data=new Object[Total_rows-Total_Master_sheets][cols_temp];

			for (int k = 0; k <Total_Master_sheets; k++) {

				XSSFSheet sh1 = Master_Workbook.getSheetAt(k);
				rows_temp = Readexcel.getRowCountBySheetnumber(k);
				
				Total_rows=rows_temp+rows_temp;

				int i;
				int j = 0;

				Sheet_data = new Object[rows_temp][cols_temp];

				for (i = 1; i<= rows_temp-1; i++) {


					for (j = 0; j < cols_temp; j++) {

						Sheet_data[i][j]=sh1.getRow(i).getCell(j).getStringCellValue();
						//System.out.println(sh1.getRow(i).getCell(j).getStringCellValue());


						Total_data[Final_rows][Final_columns]=Sheet_data[i][j];
						Final_columns++;
					}

					Final_rows++;
					if(Final_columns==cols_temp){
						Final_columns=0;
					}

				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Total_data;
	}
}
