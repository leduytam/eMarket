package com.group05.emarket;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.group05.emarket.enums.ESortProductOption;
import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.Payment;
import com.group05.emarket.models.Product;
import com.group05.emarket.models.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockData {
    private final static List<Product> products;
    private final static List<Category> categories;
    private final static List<Review> reviews;
    private final static List<CartItem> cartItems;

    private final static List<Payment> payments;

    private final static List<Order> orders;

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>(MockData.products);
        Collections.shuffle(products);
        return products;
    }

    public static List<Product> getProducts(String query, String categoryId, float[] priceRange, ESortProductOption option) {
        List<Product> products = new ArrayList<>(MockData.products);

        products = products.stream().filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) && p.getCategory().getId().equals(categoryId) && p.getPrice() >= priceRange[0] && p.getPrice() <= priceRange[1]).collect(Collectors.toList());

        products.sort((p1, p2) -> {
            switch (option) {
                case PRICE_ASCENDING:
                    return Float.compare(p1.getPrice(), p2.getPrice());
                case PRICE_DESCENDING:
                    return Float.compare(p2.getPrice(), p1.getPrice());
                case NAME_ASCENDING:
                    return p1.getName().compareTo(p2.getName());
                case NAME_DESCENDING:
                    return p2.getName().compareTo(p1.getName());
                case HIGHEST_RATED:
                    return Float.compare(p2.getAvgRating(), p1.getAvgRating());
                case LOWEST_RATED:
                    return Float.compare(p1.getAvgRating(), p2.getAvgRating());
                default:
                    return 0;
            }
        });

        return products;
    }

    public static Product getProductById(String id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>(MockData.reviews);
        reviews.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
        return reviews;
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static List<Order> getOrders(Order.OrderStatus status) {
        return orders.stream().filter(o -> o.getStatus() == status).collect(Collectors.toList());
    }

    public static List<Product> getProductsByKeyword(String keyword) {
        return products.stream().filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }

    public static List<Payment> getPayments() {
        return payments;
    }

    static {
        products = new ArrayList<>();
        categories = new ArrayList<>();
        reviews = new ArrayList<>();
        cartItems = new ArrayList<>();
        orders = new ArrayList<>();
        payments = new ArrayList<>();
//        products = new ArrayList<>();
//        categories = new ArrayList<>();
//        reviews = new ArrayList<>();
//        cartItems = new ArrayList<>();
//        orders = new ArrayList<Order>();
//        payments = new ArrayList<Payment>();
//
        payments.add(new Payment.Builder()
                .setId("b88f672b-8135-4cf2-9c4b-0050a1304e4c")
                .setTitle("Master Card")
                .setCardNumber("4242 4242 4242 4242")
                .setImage(R.drawable.ic_master_card)
                .setPrimary(true)
                .build());

        payments.add(new Payment.Builder()
                .setId("b88f672b-8135-4cf2-9c4b-0050a1304e4c")
                .setTitle("Momo")
                .setCardNumber("0383937992")
                .setImage(R.drawable.ic_momo)
                .setPrimary(false)
                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("b88f672b-8135-4cf2-9c4b-0050a1304e4c"))
//                .setName("Vegetables")
//                .setImage(R.drawable.ic_category_vegetables)
//                .setBackground(R.color.category_vegetables)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("4dbb88c5-1394-4f33-8e2a-5c5e2d5b5c5a"))
//                .setName("Fruits")
//                .setBackground(R.color.category_fruits)
//                .setImage(R.drawable.ic_category_fruits)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("f659857d-39d5-4ba3-8c92-9b170c15b14d"))
//                .setName("Meats")
//                .setBackground(R.color.category_meats)
//                .setImage(R.drawable.ic_category_meats)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("b49c50e3-36d2-4467-82d1-04a7eb34e9c7"))
//                .setName("Eggs")
//                .setBackground(R.color.category_eggs)
//                .setImage(R.drawable.ic_category_eggs)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("6cf10f6a-b271-46f1-98d1-c84095638fb9"))
//                .setName("Fishes")
//                .setBackground(R.color.category_fishes)
//                .setImage(R.drawable.ic_category_fishes)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("c334a846-1596-4491-9b9a-90968e48f16a"))
//                .setName("Bakeries")
//                .setBackground(R.color.category_bakeries)
//                .setImage(R.drawable.ic_category_bakeries)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .setName("Snacks")
//                .setBackground(R.color.category_snacks)
//                .setImage(R.drawable.ic_category_snacks)
//                .build());
//
//        categories.add(new Category.Builder()
//                .setId(UUID.fromString("e3f3e2f3-d41e-4940-bd09-f94ef83e674e"))
//                .setName("Beverages")
//                .setBackground(R.color.category_beverages)
//                .setImage(R.drawable.ic_category_beverages)
//                .build());
//
//        products.add(new Product.Builder()
//                .setId(UUID.fromString("5e12bb3e-14d9-4c1e-853d-4a8a94ecb17c"))
//                .setName("Snack mì hương gà Enaak gói 30g")
//                .setPrice(0.26f)
//                .setImage(R.drawable.image_snack_1)
//                .setDescription("Snack có hình dáng của những sợi mì cùng hương gà, snack Enaak Gemez còn mang đến ăn cay cay hấp dẫn, kích thích vị giác và tạo một cảm giác rất lạ miệng. Snack mì hương gà Enaak gói 30g tiện lợi, ăn vặt vui và tiện lợi, an toàn.\n\nĐôi nét về thương hiệu\n\nSnack Enaak là thương hiệu bánh snack nổi tiếng đến từ Indonesia với thành phần từ 100% khoai tây thật và sợi mì được nướng không qua chiên dầu mỡ. Bánh snack Enaak mang đến cho người tiêu dùng món ăn vặt thú vị hấp dẫn thích hợp sử dụng khi đi du lịch, xem phim hoặc trong các buổi gặp mặt trò chuyện cùng bạn bè.\n\nThành phần dinh dưỡng của sản phẩm\n\nThành phần chính của sản phẩm gồm: Bột mì, dầu cọ, tinh bột sắn, bột hành, bột tỏi, sốt đậu nành, bột gia vị gà (đường, chất điều vị (E621), hương gà giống tự nhiên), chất điều vị (E621), đường, chất tạo xốp, muối, chất tạo ngọt. Ngoài ra, sản phẩm cung cấp cho cơ thể khoảng 120kcal trong 30g mì.\n\nTác dụng của sản phẩm với sức khỏe\n\nSnack mì hương gà Enaak gói 30g sẽ là một món ăn độc đáo và thơm ngon cho những ai yêu thích hương vị của sợi mì kết hợp với mùi hương thịt gà. Với hương vị thơm ngon được tạo thành từ việc phối trộn gia vị thích hợp và cách chế biến kết hợp với các sợi mì tươi ngon được thấm đều gia vị và sấy khô giòn rụm, sản phẩm mang đến cho người ăn một cảm giác giòn rụm, thơm ngon vị gà và có thể nhâm nhi cả ngày không biết chán.\n\nLưu ý khi sử dụng sản phẩm\n\nLưu ý, Snack mì hương gà Enaak gói 30g không phù hợp dùng cho trẻ dưới 1 tuổi. Để bảo quản mì, bạn cần để sản phẩm ở nơi khô ráo, thoáng mát, tránh ánh nắng trực tiếp.")
//                .setDiscount(0)
//                .setAvgRating(5)
//                .setRatingCount(13)
//                .setWeight(30)
//                .setWeightUnit("g")
//                .setCategoryId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .build());
//
//        products.add(new Product.Builder()
//                .setId(UUID.fromString("b632a614-1e49-4f70-bd6c-bbc4ba11e4c4"))
//                .setName("Snack vị cua Kinh Đô gói 32g")
//                .setPrice(0.28f)
//                .setImage(R.drawable.image_snack_2)
//                .setDescription("Snack có hình dáng ngộ nghĩnh của những chú cua kích thích vị giác. Snack vị cua Kinh Đô gói 32g giòn ngon, hấp dẫn là món ăn yêu thích không chỉ trẻ em mà còn người lớn. Snack Kinh Đô chất lượng, tiện lợi, dễ mang theo cho các buổi đi chơi, là món ăn vặt giúp bạn thư giãn.\n\nĐôi nét về thương hiệu\n\nHiện nay, thương hiệu Kinh Đô được quản lý bởi Công ty Cổ phần Mondelez Kinh Đô có trụ sở tại Mỹ. Các sản phẩm bánh Kinh Đô từ lâu đã trở nên quen thuộc với người dân Việt Nam. Hương vị bánh thơm ngon, đa dạng và hấp dẫn đã giúp các sản phẩm được ưa chuộng hơn.\n\nThành phần dinh dưỡng trong sản phẩm\n\nBánh snack được làm từ bột mì, bột gạo, dầu thực vật và các gia vị khác. Sản phẩm sẽ cung cấp năng lượng, chất đạm, chất béo cho cơ thể. Đây là loại snack quen thuộc với nhiều trẻ em bởi vị thơm giòn dễ ăn.\n\nLưu ý khi sử dụng và cách bảo quản sản phẩm\n\nSản phẩm có vị giòn, hương thơm nên khi mở gói, bạn nên sớm sử dụng để tránh không khí làm ảnh hưởng đến độ giòn thơm của bánh. Bảo quản sản phẩm ở nơi khô ráo, thoáng mát, tránh nhiệt độ cao hoặc ánh nắng trực tiếp từ mặt trời.\n\nLưu ý: Không dùng khi sản phẩm hết hạn hoặc có dấu hiệu ẩm mốc.")
//                .setDiscount(5)
//                .setAvgRating(4.8f)
//                .setRatingCount(5)
//                .setWeight(32)
//                .setWeightUnit("g")
//                .setCategoryId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .build());
//
//        products.add(new Product.Builder()
//                .setId(UUID.fromString("c8cc8d12-424e-447c-9cf9-79922d3e3d2d"))
//                .setName("Snack khoai tây vị bít tết kiểu New York Swing gói 32g")
//                .setPrice(0.30f)
//                .setImage(R.drawable.image_snack_3)
//                .setDescription("Snack khoai tây giòn tan, ăn cực đã với hương vị bò bít tết đậm đà thích thú khi ăn. Snack khoai tây vị bít tết kiểu New York Swing gói 32g tiện lợi, dễ mang theo khi đi chơi, dã ngoại. Snack Swing còn thích hợp vừa ăn vừa xem phim, đọc sách")
//                .setDiscount(2)
//                .setAvgRating(3.0f)
//                .setRatingCount(10)
//                .setWeight(32)
//                .setWeightUnit("g")
//                .setCategoryId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .build());
//
//        products.add(new Product.Builder()
//                .setId(UUID.fromString("e601862a-b2c4-4d1e-87aa-0a9e15e1c4d7"))
//                .setName("Snack khoai tây vị tảo biển Nori Lay's gói 58g")
//                .setPrice(0.49f)
//                .setImage(R.drawable.image_snack_4)
//                .setDescription("Snack thơm nức mũi luôn, khoai tây thì mỏng, giòn rụm. Vị khoai tây thật. Snack khoai tây vị tảo biển Nori Lay's gói 58g ăn rất giòn luôn, vị tảo biển Nori khá thơm, đậm đà trên từng miếng khoai tây. Sack khoai tây lay's được rất nhiều bạn trẻ đón nhận và sử dụng cho nhiều hoạt động nhé\n\nĐôi nét về thương hiệu\n\nLay's là thương hiệu khoai tây chiên lớn hàng đầu thế giới, là thương hiệu của Mỹ, thành lập vào năm 1965 khi khởi đầu chỉ là cửa hàng thức ăn nhẹ vào năm 1932, sau đó với việc mua lại nhà máy sản xuất khoai tây và thương hiệu Lay’s cũng chính thức ra đời và trở thành biểu tượng mỗi khi nhắc đến các loại khoai tây chiên thơm ngon độc đáo, có mặt ở rất nhiều các quốc gia trên toàn thế giới và được nhiều khách hàng tin dùng chọn lựa.\n\nThành phần dinh dưỡng trong sản phẩm\n\nSnack khoai tây vị tảo biển Nori Lay's gói 52g dạng snack ăn nhanh, ăn liền tiện dụng với hương vị độc đáo, đóng gói gọn nhẹ, sản phẩm được làm từ các nguyên liệu: Khoai tây, dầu thực vật, bột gia vị Tảo Biển Nori 6% (đường, maltodetrin, muối, hương tự nhiên và giống tự nhiên, chất điều vị,... đảm bảo cung cấp được các chất dinh dưỡng thiết yếu: Chất béo, natri, năng lượng, đường, chất đạm,... Theo như thông tin hãng công bố trong 100g sản phẩm sẽ chứa khoảng 170 calo.\n\nTác dụng của sản phẩm với sức khỏe\n\nSnack khoai tây vị tảo biển Nori Lay's gói 52g với thành phần khoai tây tự nhiên cũng công nghệ sản xuất chuẩn vệ sinh an toàn thực phẩm, sản phẩm dùng ngay khi mở bao bì, bạn có thể dùng thay cho các bữa ăn vặt, giúp bạn qua cơn đó cũng như có năng lượng, chứa các chất dinh dưỡng hoạt động cả ngày. Dùng để ăn vặt giúp giảm căng thẳng và tập trung hơn.")
//                .setDiscount(8)
//                .setAvgRating(4.8f)
//                .setRatingCount(9)
//                .setWeight(58)
//                .setWeightUnit("g")
//                .setCategoryId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .build());
//
//        products.add(new Product.Builder()
//                .setId(UUID.fromString("d79b40e4-7c2f-4b18-83f4-02b9e1f78a67"))
//                .setName("Snack khoai tây vị tảo biển O'Star gói 32g")
//                .setPrice(0.28f)
//                .setImage(R.drawable.image_snack_5)
//                .setDescription("Snack khoai tây giòn thơm ngon với độ dày vừa phải, khi ăn cảm giác tan trong miệng thích thú. Snack khoai tây vị tảo biển O'star gói 32g với hương vị tảo biển lạ miệng thơm ngon, tiện lợi thích hợp đọc sách, xem phim và ăn. Snack O'Star là thương hiệu Hàn Quốc\n\nCó vô vàn loại snack khác nhau và nhưng để tìm được loại snack thích hợp thì Snack khoai tây vị tảo biển O'Star gói 36g sẽ cho bạn khám phá sự khác biệt với việc phối trộn hài hòa khoai tây và rong biển. Sản phẩm là sự phối hợp đặc biệt của các loại nguyên liệu thân thuộc và tươi mát dễ gây nghiện cho những người yêu thích snack ngay lần đầu tiên thưởng thức, vị ngon và mùi vị đặc trưng của rong biển sẽ kích thích bạn không thể ngừng thưởng thức đó.\n\nKhoai tây và rong biển đã được tuyển chọn nghiêm ngặt từ khâu đầu vào cho đến khi ra sản phẩm đóng gói chuyển cho người tiêu dùng. Khoai tây tạo hình cắt mỏng phối trộn với bột rong biển tự nhiên. Trải qua quá trình chế biến và tổng hợp của các loại gia vị bổ sung cho ra miếng khoai tây giòn rụm và màu sắc tuyệt đẹp vàng đặc trưng và hương vị rong biển không lẫn vào đâu được\n\nThiết kế dạng túi dễ bảo quản và dễ mở cho mọi người. Đây là món ăn vặt không thể từ chối để cung cấp nhanh năng lượng trong một ngày làm việc mệt mỏi\n\nĐược tuyển chọn và sản xuất theo quy trình khắc khe từ Hàn Quốc Chắc chắn thương hiệu Snack O'Star bingsu dưa lưới sẽ khiến bạn không thể quên được nếu đã trót thử qua một lần.")
//                .setDiscount(0)
//                .setAvgRating(5f)
//                .setRatingCount(11)
//                .setWeight(32)
//                .setWeightUnit("g")
//                .setCategoryId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .build());
//
//        products.add(new Product.Builder()
//                .setId(UUID.fromString("15a40d0e-eed7-443c-8bbd-7f202bf1d9c9"))
//                .setName("Snack pho mát miếng Oishi gói 39g")
//                .setPrice(0.28f)
//                .setImage(R.drawable.image_snack_6)
//                .setDescription("Snack giòn ngon, thơm thơm vị phô mát kích thích vị giác vô cùng. Snack pho mát miếng Oishi gói 39g hấp dẫn, phù hợp vừa xem phim, vừa nghe nhạc vừa nhâm nhi thưởng thức. Snack Oishi tiện lợi, nhỏ gọn, dễ mang theo cho các buổi hoạt động ngoài trời.\n\nĐôi nét về thương hiệu\n\nNhắc đến các thương hiệu bánh kẹo kinh điển tại Việt Nam bạn không thể nào bỏ qua được Oishi, có nguồn gốc từ đảo quốc Philippines, là 1 trong số các thương hiệu đa quốc gia đặt chân vào Việt Nam và đã có các chiến dịch quảng bá thành công, trở thành thương hiệu quen thuộc, được rất nhiều khách hàng tin dùng và chọn lựa, chiếm lĩnh thị trường cũng như giữ vị trí cao trong sự lựa chọn của khách hàng với các sản phẩm từ bánh, kẹo ăn vặt, đậu phộng, thức uống, snack,...\n\nThành phần dinh dưỡng trong sản phẩm\n\nSnack pho mát 39g snack giòn tan, làm sẵn đóng gói tiện dụng, hơn hết là sản phẩm chay mặn đều dùng được, có vị mặn ngọt đậm đà khó cưỡng, thu hút được nhiều thực khách, làm từ các thành phần như: Bột bắp, tinh bột sắn, dầu ăn, bột phô mai (3%), sữa không kem, muối i-ốt, đường, bột ngọt, bột nổi và anti-oxydant (E320 hoặc E321),...\n\nTheo thông tin hãng công bố, trong 30g bánh sẽ cung cấp khoảng 150 calo.\n\nTác dụng của sản phẩm với sức khỏe\n\nSnack pho mát Oishi gói 39g bánh snack chay mặn đều dùng được, có vị thơm ngon khó cưỡng, thường được sử dụng ăn vặt, tụ tập bạn bè, kích thích vị giác tốt, chứa các chất dinh dưỡng chống đói, giảm căng thẳng, cho bạn tập trung và hoạt động hiệu quả hơn.")
//                .setDiscount(3)
//                .setAvgRating(4.8f)
//                .setRatingCount(5)
//                .setWeight(39)
//                .setWeightUnit("g")
//                .setCategoryId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
//                .build());

        Random rnd = new Random();
        var contents = new String[]{
                "Món ăn này thật ngon miệng, tôi thích cách nó được chế biến và gia vị hài hòa.",
                "Thức uống này có hương vị độc đáo, tôi rất thích thưởng thức.",
                "Món ăn này có vẻ không được chế biến kỹ lưỡng, tuy nhiên giá cả của nó rất hợp lý.",
                "Tôi đã thử món ăn này và cảm thấy rất hài lòng với hương vị và chất lượng của nó.",
                "Thức uống này có hương vị đặc biệt, nó thực sự là một trải nghiệm tuyệt vời.",
                "Món ăn này thật sự không đáng để tiêu tốn tiền bạc của bạn, chất lượng của nó không được tốt lắm.",
                "Tôi đã ăn thử món ăn này và cảm thấy nó thật sự ngon miệng và độc đáo.",
                "Thức uống này rất thơm ngon và giá cả của nó hợp lý.",
                "Món ăn này có chất lượng tốt và giá cả của nó rất hợp lý.",
                "Thức uống này có hương vị độc đáo, nó thực sự đáng để thử.",
                "Tôi không thực sự hài lòng về món ăn này, nó chế biến không kỹ lưỡng và còn hơi nguội.",
                "Thức uống này rất mát lạnh và phù hợp với thời tiết nóng.",
                "Món ăn này rất ngon và được chế biến rất kỹ lưỡng, tôi sẽ quay lại để thưởng thức nó lần nữa.",
                "Thức uống này có hương vị đặc biệt và giá cả của nó rất hợp lý.",
                "Món ăn này thật sự đáng tiền và chất lượng của nó rất tốt.",
                "Thức uống này có hương vị độc đáo, tôi rất thích thưởng thức nó.",
                "Món ăn này có hương vị đặc biệt và được chế biến rất kỹ lưỡng, tôi sẽ giới thiệu cho bạn bè của mình.",
                "Thức uống này rất thơm ngon và có hương vị độc đáo, tôi rất thích thưởng thức nó.",
                "Món ăn này có chất lượng tốt và giá cả của nó rất hợp lý, tôi sẽ quay lại để thưởng thức nó lần nữa."
        };
        var names = new String[]{
                "Nguyễn Thị Hằng",
                "Lê Minh Tuấn",
                "Trần Thanh Hải",
                "Phạm Thị Thuỳ Dung",
                "Vũ Văn Hưng",
                "Lâm Thị Hồng Nhung",
                "Đinh Văn Hùng",
                "Nguyễn Thị Hồng Nga",
                "Hoàng Văn Tùng",
                "Nguyễn Văn Hùng",
                "Lê Thị Mai",
                "Trần Văn Hải",
                "Phạm Thị Hồng Loan",
                "Vũ Thị Ngọc Ánh",
                "Đỗ Minh Quân",
                "Nguyễn Thị Ái Nhi",
                "Hoàng Văn Đức",
                "Nguyễn Thị Thu Thảo",
                "Lâm Văn Thành",
                "Trần Thị Thanh Mai"
        };
        var avatars = new Integer[]{
                R.drawable.image_avatar_1,
                R.drawable.image_avatar_2,
                R.drawable.image_avatar_3,
                R.drawable.image_avatar_4,
                R.drawable.image_avatar_5,
                R.drawable.image_avatar_6,
        };
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 24);
        for (int i = 0; i < 100; i++) {
            LocalDate randomDate = startDate.plusDays(new Random().nextInt((int) (endDate.toEpochDay() - startDate.toEpochDay())));

            int hour = rnd.nextInt(24);
            int minute = rnd.nextInt(60);
            int second = rnd.nextInt(60);

            LocalDateTime randomDateTime = randomDate.atTime(hour, minute, second);

            reviews.add(new Review() {
                {
                    setId(UUID.randomUUID().toString());
                    setContent(contents[rnd.nextInt(contents.length)]);
                    setRating(rnd.nextInt(5) + 1);
                    setReviewerName(names[rnd.nextInt(names.length)]);
                    setReviewerAvatar(avatars[rnd.nextInt(avatars.length)]);
                    setCreatedAt(Date.from(randomDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                    setUpdatedAt(Date.from(randomDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                }
            });
        }
//
//        cartItems.add(new CartItem(products.get(0), 1));
//        cartItems.add(new CartItem(products.get(1), 2));
//        cartItems.add(new CartItem(products.get(2), 3));
//        cartItems.add(new CartItem(products.get(3), 4));
//
//        orders.add(new Order.Builder()
//                .setId(1)
//                .setTotalPrice(100000)
//                .setName("pending order 1")
//                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
//                .setPhone("0123456789")
//                .setProducts(cartItems)
//                .setStatus(Order.OrderStatus.PENDING)
//                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
//                .build());
//
//        orders.add(new Order.Builder()
//                .setId(2)
//                .setTotalPrice(200000)
//                .setName("delivering order 2")
//                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
//                .setPhone("0123456789")
//                .setProducts(cartItems)
//                .setStatus(Order.OrderStatus.DELIVERING)
//                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
//                .build());
//
//        orders.add(new Order.Builder()
//                .setId(3)
//                .setTotalPrice(300000)
//                .setName("delivered order 3")
//                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
//                .setPhone("0123456789")
//                .setProducts(cartItems)
//                .setStatus(Order.OrderStatus.DELIVERED)
//                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
//                .build());
//
//        orders.add(new Order.Builder()
//                .setId(4)
//                .setTotalPrice(400000)
//                .setName("canceled order 4")
//                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
//                .setPhone("0123456789")
//                .setProducts(cartItems)
//                .setStatus(Order.OrderStatus.PENDING)
//                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
//                .build());
//
//        orders.add(new Order.Builder()
//                .setId(5)
//                .setTotalPrice(500000)
//                .setName("pending order 5")
//                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
//                .setPhone("0123456789")
//                .setProducts(cartItems)
//                .setStatus(Order.OrderStatus.PENDING)
//                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
//                .build());
//
//        orders.add(new Order.Builder()
//                .setId(6)
//                .setTotalPrice(600000)
//                .setName("Pending order 2")
//                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
//                .setPhone("0123456789")
//                .setProducts(cartItems)
//                .setStatus(Order.OrderStatus.PENDING)
//                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
//                .build());
//
//
    }
}
