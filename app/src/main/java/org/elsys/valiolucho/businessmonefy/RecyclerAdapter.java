package org.elsys.valiolucho.businessmonefy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
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
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Transaction transaction = arrayList.get(position);
        holder.name.setText(transaction.getName());
        holder.date.setText(transaction.getDate());
        holder.price.setText(String.valueOf(transaction.getMoney()));
        if(transaction.getMoney().compareTo(BigDecimal.ZERO) == 1) {
            holder.imgView.setImageResource(R.drawable.tr_plus);
            holder.line.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrPlus));
        }else{
            holder.imgView.setImageResource(R.drawable.tr_minus);
            holder.line.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrMinus));
        }
        holder.editPencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(position);
            }
        });
        holder.imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PopupWindow popupWindow = new PopupWindow();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.info_dialog_layout, null);
                    TextView infoTV = (TextView) popupView.findViewById(R.id.infoBarTV);
                    TextView nameTV = (TextView) popupView.findViewById(R.id.nameTV);
                    TextView descrTV = (TextView) popupView.findViewById(R.id.descrTV);
                    TextView dateTV = (TextView) popupView.findViewById(R.id.dateTV);
                    TextView moneyTV = (TextView) popupView.findViewById(R.id.moneyTV);
                    Transaction transaction = arrayList.get(position);
                    BigDecimal money = transaction.getMoney();
                    if (money.compareTo(BigDecimal.ZERO) == 1) {
                        infoTV.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrPlus));
                        popupView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGreen));
                    } else {
                        infoTV.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrMinus));
                        popupView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightRed));
                    }
                    nameTV.setText(transaction.getName());
                    descrTV.setText(transaction.getDescription());
                    dateTV.setText(transaction.getDate());
                    moneyTV.setText(money.toPlainString());
                    popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER_VERTICAL, 0, 0);
                }else{
                    popupWindow.dismiss();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void infoCheckDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.info_dialog_layout, null);
        TextView infoTV = (TextView) view.findViewById(R.id.infoBarTV);
        TextView nameTV = (TextView) view.findViewById(R.id.nameTV);
        TextView descrTV = (TextView) view.findViewById(R.id.descrTV);
        TextView dateTV = (TextView) view.findViewById(R.id.dateTV);
        TextView moneyTV = (TextView) view.findViewById(R.id.moneyTV);
        Transaction transaction = arrayList.get(position);
        BigDecimal money = transaction.getMoney();
        if(money.compareTo(BigDecimal.ZERO) == 1) {
            infoTV.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrPlus));
        }else {
            infoTV.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTrMinus));
        }
        nameTV.setText(transaction.getName());
        descrTV.setText(transaction.getDescription());
        dateTV.setText(transaction.getDate());
        moneyTV.setText(money.toPlainString());
        Dialog dialog = builder.create();
        dialog.show();
    }

    protected void editDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog, null);
        final TextView nameTV = (TextView) view.findViewById(R.id.editTextName);
        final TextView descriptionTV = (TextView) view.findViewById(R.id.editTextDescription);
        final TextView moneyTV = (TextView) view.findViewById(R.id.editTextPrice);
        final Transaction oldTransaction = arrayList.get(position);
        nameTV.setText(oldTransaction.getName());
        descriptionTV.setText(oldTransaction.getDescription());
        moneyTV.setText(String.valueOf(oldTransaction.getMoney()));

        builder.setView(view)
                .setPositiveButton(R.string.saveED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = nameTV.getText().toString();
                        String description = descriptionTV.getText().toString();
                        BigDecimal money = new BigDecimal(moneyTV.getText().toString());
                        if (money.compareTo(BigDecimal.ZERO) == 0) {
                            Toast.makeText(context, "Money can not be 0", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        Transaction newTransaction = new Transaction(name, description, money);
                        newTransaction.setDate(oldTransaction.getDate());
                        DataBaseHelper myDbHelper = DataBaseHelper.getInstance(context);
                        myDbHelper.updateData(newTransaction);
                        myDbHelper.close();
                        Toast.makeText(context, "Record is changed!", Toast.LENGTH_SHORT).show();
                        arrayList.remove(oldTransaction);
                        arrayList.add(position, newTransaction);
                        notifyItemChanged(position);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name, date, price;
        ImageView imgView;
        ImageButton editPencil;
        View line;

        RecyclerViewHolder(View view, final Context context){
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

