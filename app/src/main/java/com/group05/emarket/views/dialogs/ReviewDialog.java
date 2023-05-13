package com.group05.emarket.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.group05.emarket.R;
import com.group05.emarket.viewmodels.OrderDetailViewModel;
import com.group05.emarket.viewmodels.OrderViewModel;
import com.group05.emarket.views.activities.OrderDetailActivity;

public class ReviewDialog extends DialogFragment {
    private final OrderDetailViewModel orderViewModel;
    private String orderId;
    private ReviewDialogListener listener;
    public ReviewDialog(OrderDetailViewModel orderViewModel, String orderId) {
        this.orderViewModel = orderViewModel;
        this.orderId = orderId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ReviewDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReviewDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = getLayoutInflater().inflate(R.layout.dialog_rating, null);
        dialog.setContentView(view);

        Button btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> dismiss());
        Button btnSubmit = view.findViewById(R.id.btn_review);
        btnSubmit.setOnClickListener(v -> {
            EditText txtRating = view.findViewById(R.id.ratingContent);
            RatingBar ratingBar = view.findViewById(R.id.ratingBar);
            if (txtRating.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter your review", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ratingBar.getRating() == 0) {
                Toast.makeText(getContext(), "Please enter your rating", Toast.LENGTH_SHORT).show();
                return;
            }
            orderViewModel.submitReview(orderId, txtRating.getText().toString(), ratingBar.getRating());
            Toast.makeText(getContext(), "Submit review successfully", Toast.LENGTH_SHORT).show();
            listener.onReviewSubmit();
            dismiss();
        });

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface ReviewDialogListener {
        void onReviewSubmit();
    }
}
