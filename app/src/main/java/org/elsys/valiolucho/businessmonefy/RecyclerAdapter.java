package org.elsys.valiolucho.businessmonefy;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
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
        holder.imgView.setImageResource(transaction.getTransactionResource());
        holder.name.setText(transaction.getName());
        holder.date.setText(transaction.getDate());//normalize date!!!
        holder.price.setText(transaction.getMoney());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, price;
        ImageView imgView;
        ImageButton editPencil;

        RecyclerViewHolder(View view, final Context context){
            super(view);
            name = (TextView) view.findViewById(R.id.transaction_name);
            date = (TextView) view.findViewById(R.id.transaction_date);
            price = (TextView) view.findViewById(R.id.transaction_price);
            imgView = (ImageView) view.findViewById(R.id.transaction_img);
            editPencil = (ImageButton) view.findViewById(R.id.editPencil);
            editPencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditRecordDialog dialog = new EditRecordDialog();
                    dialog.show(((Activity) context).getFragmentManager(), "Dialog");
                }
            });
        }
    }
}
