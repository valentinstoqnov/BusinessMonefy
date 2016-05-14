package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.widget.Toast;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvGenerator {

    private String fileName;
    private String dir;
    private ArrayList<Transaction> data;
    private Context context;

    public CsvGenerator(String fileName, String dir, ArrayList<Transaction> data, Context context) {
        this.fileName = fileName;
        this.dir = dir;
        this.data = data;
        this.context = context;
    }

    public void generate() {
        try {
            StringBuffer buff = new StringBuffer();
            buff.append("Name, Description, Date, Money\n");
            for (Transaction transaction : data) {
                buff.append(transaction.getName());
                buff.append(", ");
                buff.append(transaction.getDescription());
                buff.append(", ");
                buff.append(transaction.getDate());
                buff.append(", ");
                buff.append(transaction.getMoney().toPlainString());
                buff.append('\n');
            }
            buff.deleteCharAt(buff.lastIndexOf("\n"));
            FileWriter writer = new FileWriter(dir + fileName + ".csv");
            writer.append(buff.toString());
            writer.flush();
            writer.close();
            Toast.makeText(context, "Database is exported to CSV", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
