package com.group05.emarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.group05.emarket.R;
import com.group05.emarket.models.Review;
import com.group05.emarket.adapters.ReviewAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        MaterialToolbar topBar = findViewById(R.id.top_bar);
        topBar.setNavigationOnClickListener(v -> finish());

        topBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_cart) {
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
            } else if (id == R.id.action_share) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "eMarket");
                startActivity(Intent.createChooser(intent, "Share"));
            } else {
                return false;
            }

            return true;
        });

        RecyclerView rvReviews = findViewById(R.id.rv_reviews);
        rvReviews.setAdapter(new ReviewAdapter(this, _getReviews()));
        rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private List<Review> _getReviews() {
        var reviews = new ArrayList<Review>();

        reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(5)
                .setContent("Mình đã ăn nhiều loại mì ăn sống nhưng loại này mình thấy ngon nhất nha")
                .setReviewerName("Bùi Thị Ngọc Hoa")
                .setReviewerAvatar(R.drawable.ic_default_avatar)
                .setCreatedAt(LocalDateTime.parse("2023-04-03T00:00:00"))
                .build());

        reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(4)
                .setContent("Sản phẩm dùng rất thích")
                .setReviewerName("Nguyễn Văn Hạnh")
                .setReviewerAvatar(R.drawable.ic_default_avatar)
                .setCreatedAt(LocalDateTime.parse("2023-04-04T00:00:00"))
                .build());

        reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(4)
                .setContent("snack này rất ngon, giòn giòn vị vừa phải k cay, giá như này mình thấy hơi cao ah")
                .setReviewerName("An Thu Thảo")
                .setReviewerAvatar(R.drawable.ic_default_avatar)
                .setCreatedAt(LocalDateTime.parse("2023-04-01T00:00:00"))
                .build());

        reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(5)
                .setContent("Bé nhà mình thích ăn loại mì này lắm. Mì giòn nhưng không bị cứng như mì bình thường. Vị mặn mặn, ngọt ngọt nữa. Mình cong thích nữa chứ đừng nói con nít.")
                .setReviewerName("Khả Khanh")
                .setReviewerAvatar(R.drawable.ic_default_avatar)
                .setCreatedAt(LocalDateTime.parse("2023-04-02T00:00:00"))
                .build());

        reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(5)
                .setContent("Vị ăn rất ngon, chống đói nhanh chóng")
                .setReviewerName("Lê Tuấn")
                .setReviewerAvatar(R.drawable.ic_default_avatar)
                .setCreatedAt(LocalDateTime.parse("2023-04-05T00:00:00"))
                .build());

        reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(3)
                .setContent("Tạm được")
                .setReviewerName("Kim Ánh")
                .setReviewerAvatar(R.drawable.ic_default_avatar)
                .setCreatedAt(LocalDateTime.parse("2023-04-01T00:00:00"))
                .build());

        return reviews;
    }
}