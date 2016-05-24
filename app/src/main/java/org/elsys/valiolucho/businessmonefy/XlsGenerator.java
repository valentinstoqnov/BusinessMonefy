package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class XlsGenerator {

    private String fileName;
    private ArrayList<Transaction> data;
    private Context context;

    public XlsGenerator(String fileName, ArrayList<Transaction> data, Context context) {
        this.fileName = fileName;
        this.data = data;
        this.context = context;
    }

    private void boldRow(HSSFWorkbook wb, Row row) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        for(int i = 0; i < row.getLastCellNum(); i++){
            row.getCell(i).setCellStyle(style);
        }
    }

    public void generate() {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet");

            HSSFRow rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Name");
            rowhead.createCell(1).setCellValue("Description");
            rowhead.createCell(2).setCellValue("Date");
            rowhead.createCell(3).setCellValue("Money");

            boldRow(workbook, rowhead);

            int index = 1;
            for (Transaction transaction : data) {
                HSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(transaction.getName());
                row.createCell(1).setCellValue(transaction.getDescription());
                row.createCell(2).setCellValue(transaction.getDate());
                row.createCell(3).setCellValue(transaction.getMoney().toPlainString());
                index++;
            }

            File bmDir = new File("/storage/emulated/0/BusinessMonefy/");
            bmDir.mkdirs();
            File file = new File(bmDir, "businessMonefy.xls");
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            Toast.makeText(context, "Database is exported to Excel file", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
