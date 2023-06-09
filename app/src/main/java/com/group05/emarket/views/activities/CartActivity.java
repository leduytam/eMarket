package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityCartBinding;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.utilities.Formatter;
import com.group05.emarket.viewmodels.AddressViewModel;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.CartListAdapter;
import com.group05.emarket.views.dialogs.CheckoutBottomSheetDialog;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CartActivity extends AppCompatActivity implements CartListAdapter.OnCartChangedListener {
    private CartViewModel cartViewModel;
    private AddressViewModel addressViewModel;

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

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        CheckoutBottomSheetDialog bottomSheetDialog = new CheckoutBottomSheetDialog(this, cartViewModel, addressViewModel);
        cartViewModel.getCartItems().observe(this, cartItems -> {
            adapter.setCartItems(cartItems);
            binding.tvTotalPrice.setText(Formatter.formatCurrency(cartViewModel.getTotalPrice()));
            binding.tvTotalLabel.setText(String.format(Locale.US, "Total (%d items)", cartItems.size()));
            bottomSheetDialog.setTotalCost(cartViewModel.getTotalPrice());

            boolean isCartEmpty = cartItems.size() == 0;

            topBar.getMenu().getItem(0).setVisible(!isCartEmpty);

            binding.rlEmptyCart.setVisibility(isCartEmpty ? View.VISIBLE : View.GONE);
            binding.rlCartItems.setVisibility(isCartEmpty ? View.GONE : View.VISIBLE);
        });

        cartViewModel.getIsLoading().observe(this, isLoading -> {
            binding.pbIsLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);

            if (isLoading) {
                binding.rlEmptyCart.setVisibility(View.GONE);
                binding.rlCartItems.setVisibility(View.GONE);
            }
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