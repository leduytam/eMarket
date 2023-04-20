package com.group05.emarket.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.MockData;
import com.group05.emarket.R;
import com.group05.emarket.firestore.ProductsFirestoreManger;
import com.group05.emarket.models.Product;
import com.group05.emarket.schemas.ProductsFirestoreSchema;
import com.group05.emarket.views.adapters.ProductAdapter;
import com.group05.emarket.views.decorations.GridGapItemDecoration;
import com.group05.emarket.views.dialogs.FilterSortingDialog;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        setContentView(R.layout.activity_product_list);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());
        topBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_filter_sort:
                    FilterSortingDialog dialog = new FilterSortingDialog();
                    dialog.show(getSupportFragmentManager(), "FilterSortingDialog");
                    return true;
            }
            return false;
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            topBar.setTitle(extras.getString("title"));
        }
        ProductsFirestoreManger productsFirestoreManger = ProductsFirestoreManger.newInstance();
        productsFirestoreManger.getProductsByCategory(extras.getString("categoryUuid")).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        task.getResult().getDocuments().forEach(documentSnapshot -> {
                            Product product = new Product.Builder()
                                    .setDocumentId(documentSnapshot.getId())
                                    .setName(documentSnapshot.getString(ProductsFirestoreSchema.NAME))
                                    .setPrice(documentSnapshot.getDouble(ProductsFirestoreSchema.PRICE).floatValue())
                                    .setDescription(documentSnapshot.getString(ProductsFirestoreSchema.DESCRIPTION))
                                    .setWeight(documentSnapshot.getDouble(ProductsFirestoreSchema.WEIGHT).floatValue())
                                    .setWeightUnit(documentSnapshot.getString(ProductsFirestoreSchema.WEIGHT_UNIT))
                                    .setCategoryUuid(documentSnapshot.getString(ProductsFirestoreSchema.CATEGORY_UUID))
                                    .setImageUrl(documentSnapshot.getString(ProductsFirestoreSchema.IMAGE_URL))
                                    .setCategoryUuid(documentSnapshot.getString(ProductsFirestoreSchema.CATEGORY_UUID))
                                    .build();
                            products.add(product);
                        });
                    }
                    RecyclerView rvProducts = findViewById(R.id.rv_products);
                    rvProducts.setAdapter(new ProductAdapter(this, products));
                    rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
                    rvProducts.addItemDecoration(new GridGapItemDecoration(3, 10, false));
                }
        );



        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setIconifiedByDefault(false);


    }
}