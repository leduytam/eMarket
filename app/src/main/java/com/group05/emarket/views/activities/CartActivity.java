package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCartBinding;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.utilities.Formatter;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.CartListAdapter;
import com.group05.emarket.views.dialogs.CheckoutBottomSheetDialog;

import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartListAdapter.OnCartChangedListener {
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCartBinding binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialToolbar topBar = binding.topBar;
        topBar.setNavigationOnClickListener(v -> finish());

        topBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_clear_cart) {
                cartViewModel.clearCart();
                return true;
            }

            return false;
        });

        CartListAdapter adapter = new CartListAdapter(this);
        binding.rvCartItems.setAdapter(adapter);

        var swipeToDeleteOrderItemCallback = new CartListAdapter.SwipeToDeleteOrderItemCallback(adapter);
        var itemTouchHelper = new ItemTouchHelper(swipeToDeleteOrderItemCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvCartItems);
        CheckoutBottomSheetDialog bottomSheetDialog = new CheckoutBottomSheetDialog(this);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(this, cartItems -> {
            adapter.setCartItems(cartItems);
            binding.tvTotalPrice.setText(Formatter.formatCurrency(cartViewModel.getTotalPrice()));
            binding.tvTotalLabel.setText(String.format(Locale.US, "Total (%d items)", cartItems.size()));
            bottomSheetDialog.setTotalCost(cartViewModel.getTotalPrice());

            boolean isCartEmpty = cartItems.size() == 0;

            topBar.getMenu().getItem(0).setVisible(!isCartEmpty);
            binding.rlEmptyCart.setVisibility(isCartEmpty ? android.view.View.VISIBLE : android.view.View.GONE);
            binding.rlCartItems.setVisibility(isCartEmpty ? android.view.View.GONE : android.view.View.VISIBLE);
        });


        binding.btnCheckout.setOnClickListener(v -> {
            bottomSheetDialog.show();
        });

        binding.btnShopNow.setOnClickListener(v -> {
            Intent intent = new Intent(this, LayoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void onCartItemRemoved(CartItem item) {
        cartViewModel.removeItemFromCart(item);
    }

    @Override
    public void onCartItemQuantityChanged(CartItem item, int quantity) {
        cartViewModel.changeCartItemQuantity(item, quantity);
    }
}