package org.elsys.valiolucho.businessmonefy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<Transaction> arrayList = new ArrayList<>();
    private Context context;

    RecyclerAdapter(ArrayList<Transaction> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_row_layout, parent, false);
        return new RecyclerViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Transaction transaction = arrayList.get(position);
        holder.name.setText(transaction.getName());
        holder.date.setText(transaction.getDate());
        holder.price.setText(String.valueOf(transaction.getMoney()));
        holder.editPencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditRecordDialog dialog = new EditRecordDialog();
                dialog.show(((Activity) context).getFragmentManager(), "Dialog");
            }
        });
        if(transaction.getMoney() > 0) {
            holder.imgView.setImageResource(R.drawable.tr_plus);
            holder.line.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrPlus));
        }else{
            holder.imgView.setImageResource(R.drawable.tr_minus);
            holder.line.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrMinus));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, price;
        ImageView imgView;
        ImageButton editPencil;
        View line;

        RecyclerViewHolder(View view, final Context context){//CONTEXT ???
            super(view);
            name = (TextView) view.findViewById(R.id.transaction_name);
            date = (TextView) view.findViewById(R.id.transaction_date);
            price = (TextView) view.findViewById(R.id.transaction_price);
            imgView = (ImageView) view.findViewById(R.id.transaction_img);
            editPencil = (ImageButton) view.findViewById(R.id.editPencil);
            line = view.findViewById(R.id.lineSeparator);
        }
    }
}
