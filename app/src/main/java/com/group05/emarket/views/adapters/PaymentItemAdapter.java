package com.group05.emarket.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group05.emarket.R;
import com.group05.emarket.models.Payment;

import java.util.List;

public class PaymentItemAdapter extends RecyclerView.Adapter<PaymentItemAdapter.ViewHolder> {
    private final Context context;
    private final List<Payment> payments;

    public PaymentItemAdapter(Context context, List<Payment> payments) {
        this.context = context;
        this.payments = payments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_payment_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = payments.get(position);
        var paymentImage = payment.getImage();
        var paymentTitle = payment.getTitle();
        var paymentCardNumber = payment.getCardNumber();
        var paymentIsPrimary = payment.isPrimary();

        holder._ivOrder.setImageResource(paymentImage);
        holder._tvTitle.setText(paymentTitle);
        holder._tvCardNumber.setText(paymentCardNumber);

        if (paymentIsPrimary) {
            holder._ivCheckSquare.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView _ivOrder;

        private final TextView _tvTitle;

        private final TextView _tvCardNumber;

        private final ImageView _ivCheckSquare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _ivOrder = itemView.findViewById(R.id.iv_payment_image);
            _tvTitle = itemView.findViewById(R.id.tv_payment_title);
            _tvCardNumber = itemView.findViewById(R.id.tv_payment_card_number);
            _ivCheckSquare = itemView.findViewById(R.id.iv_payment_is_primary);
        }
    }
}
