package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.widget.Toast;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class XlsGenerator {

    private String fileName;
    private String dir;
    private ArrayList<Transaction> data;
    private Context context;

    public XlsGenerator(String fileName, String dir, ArrayList<Transaction> data, Context context) {
        this.fileName = fileName;
        this.dir = dir;
        this.data = data;
        this.context = context;
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

            int index = 0;
            for (Transaction transaction : data) {
                HSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(transaction.getName());
                row.createCell(1).setCellValue(transaction.getDescription());
                row.createCell(2).setCellValue(transaction.getDate());
                row.createCell(3).setCellValue(transaction.getMoney().toPlainString());
                index++;
            }

            FileOutputStream fileOut = new FileOutputStream(dir + fileName + ".xls");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        Toast.makeText(context, "Database is exported to CSV", Toast.LENGTH_SHORT).show();
    }
}
