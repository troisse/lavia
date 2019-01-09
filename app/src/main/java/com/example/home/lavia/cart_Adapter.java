package com.example.home.lavia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.home.lavia.cartActivity.products;

class cart_Adapter extends RecyclerView.Adapter<cart_Adapter.cartHolder> {

    @SuppressLint("StaticFieldLeak")
    static Context context;
     RecyclerView recyclerView;
    protected ArrayList<userPurchase> cartArray;
    OnRecyclerViewItemClickedListener listen ;
//    private View delete;


    cart_Adapter(Context c, RecyclerView recyclerView,OnRecyclerViewItemClickedListener listener) {
        context = c;
        this.listen= listener;
        this.cartArray = products();
        this.recyclerView= recyclerView;
    }

    public interface OnRecyclerViewItemClickedListener {
        void OnItemDelete(userPurchase item);
    }

    @NonNull
    @Override
    public cart_Adapter.cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false);

        return new cartHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull cartHolder holder, int position) {

    final userPurchase siri=cartArray.get(position);

        holder.cartName.setText(siri.getBuyName());
        holder.cartPrice.setText(siri.getBuyPrice());
        holder.amount.setText(siri.getAmount());

        holder.toa(siri,listen);

    }

     @Override
     public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
         super.onAttachedToRecyclerView(recyclerView);
     }

    @Override
    public int getItemCount() {

        int arr =0;

        try{
            if(cartArray==null){
                Toast.makeText(context,"Please, Make Purchase",Toast.LENGTH_SHORT).show();
            }
            else{
                arr= cartArray.size();
            }

        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return arr;
}
     static class cartHolder extends RecyclerView.ViewHolder {

         TextView cartName, cartPrice, amount;
         @SuppressLint("StaticFieldLeak")
         static View view;
         ImageView delete;

         cartHolder(View itemView) {
             super(itemView);

             view = itemView;
             cartName = view.findViewById(R.id.buyName);
             cartPrice = view.findViewById(R.id.buyPrice);
             amount = view.findViewById(R.id.cartAmount);
             delete = view.findViewById(R.id.remove);
         }

         void toa(final userPurchase makosa, final OnRecyclerViewItemClickedListener listener) {
             delete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                     builder1.setMessage("Uko sure?");
                     builder1.setCancelable(true);

                     builder1.setPositiveButton(
                             "Yes",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     listener.OnItemDelete(makosa);
                                     dialog.cancel();
                                 }
                             });

                     builder1.setNegativeButton(
                             "No",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     dialog.cancel();
                                 }
                             });

                     AlertDialog alert11 = builder1.create();
                     alert11.show();
                 }
             });
         }
 }

}
