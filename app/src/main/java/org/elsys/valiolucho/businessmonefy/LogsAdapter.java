package org.elsys.valiolucho.businessmonefy;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.elsys.valiolucho.businessmonefy.Transaction;

public class LogsAdapter extends ArrayAdapter{
    private List list = new ArrayList();

    public LogsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Transaction object) {
        list.add(object);
        super.add(object);
    }

    static class ImgHolder
    {
        ImageView IMG;
        TextView NAME;
        TextView DATE;
        TextView PRICE;
        TextView CURRENCY;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ImgHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.logs_row_layout,parent,false);
            holder = new ImgHolder();
            holder.IMG = (ImageView) row.findViewById(R.id.transaction_img);
            holder.NAME = (TextView) row.findViewById(R.id.transaction_name);
            holder.DATE = (TextView) row.findViewById(R.id.transaction_date);
            holder.PRICE = (TextView) row.findViewById(R.id.transaction_price);
            holder.CURRENCY = (TextView) row.findViewById(R.id.transaction_currency);
            row.setTag(holder);
        }
        else
        {
            holder = (ImgHolder) row.getTag();
        }

        Transaction tr = (Transaction) getItem(position);
        //holder.IMG.setImageResource(tr.getTransactionResource());
        holder.NAME.setText(tr.getName());
        holder.DATE.setText(tr.getDate());
        holder.PRICE.setText(String.valueOf(tr.getMoney()));
        holder.CURRENCY.setText("$");//get currency from log file
        return row;
    }

}