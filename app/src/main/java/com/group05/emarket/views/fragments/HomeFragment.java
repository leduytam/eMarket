package com.group05.emarket.views.fragments;

import static com.group05.emarket.schemas.ProductsFirestoreSchema.COLLECTION_NAME;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.databinding.FragmentHomeBinding;
import com.group05.emarket.models.BannerItem;
import com.group05.emarket.models.Product;
import com.group05.emarket.schemas.ProductsFirestoreSchema;
import com.group05.emarket.viewmodels.CartViewModel;
import com.group05.emarket.views.adapters.BannerPagerAdapter;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.activities.CartActivity;
import com.group05.emarket.views.activities.NotificationActivity;
import com.group05.emarket.views.adapters.CategoryAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.AllCategoriesDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExperimentalBadgeUtils
public class HomeFragment extends Fragment {

    private List<Product> products;
    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.group05.emarket.databinding.FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        Context context = binding.getRoot().getContext();

        var gridGapItemDecoration = new GridGapItemDecoration(3, 20, true);

        CollectionReference productsDocument = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
        productsDocument.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                products = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Product product = new Product.Builder()
                            .setDocumentId(document.getId())
                            .setName(document.getString(ProductsFirestoreSchema.NAME))
                            .setPrice(document.getDouble(ProductsFirestoreSchema.PRICE).floatValue())
                            .setDiscount(document.getDouble(ProductsFirestoreSchema.DISCOUNT).intValue())
                            .setDescription(document.getString(ProductsFirestoreSchema.DESCRIPTION))
                            .setWeight(document.getDouble(ProductsFirestoreSchema.WEIGHT).floatValue())
                            .setWeightUnit(document.getString(ProductsFirestoreSchema.WEIGHT_UNIT))
                            .setImageUrl(document.getString(ProductsFirestoreSchema.IMAGE_URL))
                            .setCategoryUuid(document.getString(ProductsFirestoreSchema.CATEGORY_UUID))
                            .build();

                    products.add(product);
                }

                binding.rvPopularProducts.setAdapter(new ProductAdapter(context, products.subList(4, 7)));
                binding.rvPopularProducts.addItemDecoration(gridGapItemDecoration);
                binding.rvNewestProducts.setAdapter(new ProductAdapter(context, products.subList(0, 3)));
                binding.rvNewestProducts.addItemDecoration(gridGapItemDecoration);

                binding.rvDiscountProducts.setAdapter(new ProductAdapter(context, products.subList(5, 8)));
                binding.rvDiscountProducts.addItemDecoration(gridGapItemDecoration);

            }
        });

        binding.rvCategories.setAdapter(new CategoryAdapter(context, MockData.getCategories()));



        binding.tvSeeAllCategories.setOnClickListener(v -> {
            AllCategoriesDialog dialog = new AllCategoriesDialog();
            dialog.show(getActivity().getSupportFragmentManager(), "all_categories_dialog");
        });


        MaterialToolbar topBar = binding.topBar;
        topBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            Intent intent;

            if (id == R.id.action_notification) {
                intent = new Intent(context, NotificationActivity.class);
            } else if (id == R.id.action_cart) {
                intent = new Intent(context, CartActivity.class);
            } else {
                return false;
            }

            startActivity(intent);

            return true;
        });

        BadgeDrawable badgeNotification = BadgeDrawable.create(context);
        badgeNotification.clearNumber();

        BadgeDrawable cartBadge = BadgeDrawable.create(context);

        BadgeUtils.attachBadgeDrawable(badgeNotification, topBar, topBar.getMenu().findItem(R.id.action_notification).getItemId());
        BadgeUtils.attachBadgeDrawable(cartBadge, topBar, topBar.getMenu().findItem(R.id.action_cart).getItemId());

        CartViewModel cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartBadge.setNumber(cartItems.size());
            cartBadge.setVisible(cartItems.size() != 0);
        });

        List<BannerItem> bannerItems = new ArrayList<>();
        var banner1 = new BannerItem(R.drawable.home_banner_1, 0, 20);
        banner1.setPaddingHorizontal(50);
        bannerItems.add(banner1);
        var banner2 = new BannerItem(R.drawable.home_banner_2, 0, 20);
        banner2.setPaddingHorizontal(50);
        bannerItems.add(banner2);
        BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(getActivity(), bannerItems);
        ViewPager viewPager = binding.getRoot().findViewById(R.id.banner_viewpager);
        viewPager.setAdapter(pagerAdapter);

        return binding.getRoot();
    }
}