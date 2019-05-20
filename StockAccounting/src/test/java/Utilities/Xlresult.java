package Utilities;

public class Xlresult {

	public static void main(String[] args) throws Throwable
	{
		ExcelfileUtil xl=new ExcelfileUtil();
		int bb=xl.rowCount("Sheet1");
		System.out.println(bb);
		
		int xx=xl.colCount("Sheet1", 1);
		System.out.println(xx);
		

	}

}
