package com.group05.emarket;

import com.group05.emarket.models.CartItem;
import com.group05.emarket.models.Category;
import com.group05.emarket.models.Order;
import com.group05.emarket.models.Payment;
import com.group05.emarket.models.Product;
import com.group05.emarket.models.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockData {
    private final static List<Product> _products;
    private final static List<Category> _categories;
    private final static List<Review> _reviews;
    private final static List<CartItem> _cartItems;

    private final static List<Payment> _payments;

    private final static List<Order> _orders;

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>(_products);
        Collections.shuffle(products);
        return products;
    }

    public static Product getProductById(UUID id) {
        return _products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Category> getCategories() {
        return _categories;
    }

    public static List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>(_reviews);
        Collections.shuffle(reviews);
        return reviews;
    }

    public static List<CartItem> getCartItems() {
        return _cartItems;
    }

    public static List<Order> getOrders() {
        return _orders;
    }

    public static List<Order> getOrders(Order.OrderStatus status) {
        return _orders.stream().filter(o -> o.getStatus() == status).collect(Collectors.toList());
    }

    public static List<Payment> getPayments() {
        return _payments;
    }

    static {
        _products = new ArrayList<>();
        _categories = new ArrayList<>();
        _reviews = new ArrayList<>();
        _cartItems = new ArrayList<>();
        _orders = new ArrayList<Order>();
        _payments = new ArrayList<Payment>();

        _payments.add(new Payment.Builder()
                .setId(UUID.randomUUID())
                .setTitle("Master Card")
                .setCardNumber("4242 4242 4242 4242")
                .setImage(R.drawable.ic_master_card)
                .setPrimary(true)
                .build());

        _payments.add(new Payment.Builder()
                .setId(UUID.randomUUID())
                .setTitle("Momo")
                .setCardNumber("0383937992")
                .setImage(R.drawable.ic_momo)
                .setPrimary(false)
                .build());

//        _payments.add(new Payment.Builder()
//                .setId(UUID.randomUUID())
//                .setTitle("Paypal")
//                .setCardNumber("4242 4242 4242 4242")
//                .setImage(R.drawable.ic_paypal)
//                .setPrimary(false)
//                .build());


        _categories.add(new Category.Builder()
                .setId(UUID.fromString("b88f672b-8135-4cf2-9c4b-0050a1304e4c"))
                .setName("Vegetables")
                .setImage(R.drawable.ic_category_vegetables)
                .setBackground(R.color.category_vegetables)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("4dbb88c5-1394-4f33-8e2a-5c5e2d5b5c5a"))
                .setName("Fruits")
                .setBackground(R.color.category_fruits)
                .setImage(R.drawable.ic_category_fruits)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("f659857d-39d5-4ba3-8c92-9b170c15b14d"))
                .setName("Meats")
                .setBackground(R.color.category_meats)
                .setImage(R.drawable.ic_category_meats)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("b49c50e3-36d2-4467-82d1-04a7eb34e9c7"))
                .setName("Eggs")
                .setBackground(R.color.category_eggs)
                .setImage(R.drawable.ic_category_eggs)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("6cf10f6a-b271-46f1-98d1-c84095638fb9"))
                .setName("Fishes")
                .setBackground(R.color.category_fishes)
                .setImage(R.drawable.ic_category_fishes)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("c334a846-1596-4491-9b9a-90968e48f16a"))
                .setName("Bakeries")
                .setBackground(R.color.category_bakeries)
                .setImage(R.drawable.ic_category_bakeries)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("cbea9f9c-04c3-418f-8b3f-0e2167d96f1e"))
                .setName("Snacks")
                .setBackground(R.color.category_snacks)
                .setImage(R.drawable.ic_category_snacks)
                .build());

        _categories.add(new Category.Builder()
                .setId(UUID.fromString("e3f3e2f3-d41e-4940-bd09-f94ef83e674e"))
                .setName("Beverages")
                .setBackground(R.color.category_beverages)
                .setImage(R.drawable.ic_category_beverages)
                .build());

        _products.add(new Product.Builder()
                .setId(UUID.fromString("5e12bb3e-14d9-4c1e-853d-4a8a94ecb17c"))
                .setName("Snack mì hương gà Enaak gói 30g")
                .setPrice(0.26f)
                .setImage(R.drawable.image_snack_1)
                .setDescription("Snack có hình dáng của những sợi mì cùng hương gà, snack Enaak Gemez còn mang đến ăn cay cay hấp dẫn, kích thích vị giác và tạo một cảm giác rất lạ miệng. Snack mì hương gà Enaak gói 30g tiện lợi, ăn vặt vui và tiện lợi, an toàn.\n\nĐôi nét về thương hiệu\n\nSnack Enaak là thương hiệu bánh snack nổi tiếng đến từ Indonesia với thành phần từ 100% khoai tây thật và sợi mì được nướng không qua chiên dầu mỡ. Bánh snack Enaak mang đến cho người tiêu dùng món ăn vặt thú vị hấp dẫn thích hợp sử dụng khi đi du lịch, xem phim hoặc trong các buổi gặp mặt trò chuyện cùng bạn bè.\n\nThành phần dinh dưỡng của sản phẩm\n\nThành phần chính của sản phẩm gồm: Bột mì, dầu cọ, tinh bột sắn, bột hành, bột tỏi, sốt đậu nành, bột gia vị gà (đường, chất điều vị (E621), hương gà giống tự nhiên), chất điều vị (E621), đường, chất tạo xốp, muối, chất tạo ngọt. Ngoài ra, sản phẩm cung cấp cho cơ thể khoảng 120kcal trong 30g mì.\n\nTác dụng của sản phẩm với sức khỏe\n\nSnack mì hương gà Enaak gói 30g sẽ là một món ăn độc đáo và thơm ngon cho những ai yêu thích hương vị của sợi mì kết hợp với mùi hương thịt gà. Với hương vị thơm ngon được tạo thành từ việc phối trộn gia vị thích hợp và cách chế biến kết hợp với các sợi mì tươi ngon được thấm đều gia vị và sấy khô giòn rụm, sản phẩm mang đến cho người ăn một cảm giác giòn rụm, thơm ngon vị gà và có thể nhâm nhi cả ngày không biết chán.\n\nLưu ý khi sử dụng sản phẩm\n\nLưu ý, Snack mì hương gà Enaak gói 30g không phù hợp dùng cho trẻ dưới 1 tuổi. Để bảo quản mì, bạn cần để sản phẩm ở nơi khô ráo, thoáng mát, tránh ánh nắng trực tiếp.")
                .setDiscount(0)
                .setAvgRating(5)
                .setRatingCount(13)
                .setWeight(30)
                .setWeightUnit("g")
                .build());

        _products.add(new Product.Builder()
                .setId(UUID.fromString("b632a614-1e49-4f70-bd6c-bbc4ba11e4c4"))
                .setName("Snack vị cua Kinh Đô gói 32g")
                .setPrice(0.28f)
                .setImage(R.drawable.image_snack_2)
                .setDescription("Snack có hình dáng ngộ nghĩnh của những chú cua kích thích vị giác. Snack vị cua Kinh Đô gói 32g giòn ngon, hấp dẫn là món ăn yêu thích không chỉ trẻ em mà còn người lớn. Snack Kinh Đô chất lượng, tiện lợi, dễ mang theo cho các buổi đi chơi, là món ăn vặt giúp bạn thư giãn.\n\nĐôi nét về thương hiệu\n\nHiện nay, thương hiệu Kinh Đô được quản lý bởi Công ty Cổ phần Mondelez Kinh Đô có trụ sở tại Mỹ. Các sản phẩm bánh Kinh Đô từ lâu đã trở nên quen thuộc với người dân Việt Nam. Hương vị bánh thơm ngon, đa dạng và hấp dẫn đã giúp các sản phẩm được ưa chuộng hơn.\n\nThành phần dinh dưỡng trong sản phẩm\n\nBánh snack được làm từ bột mì, bột gạo, dầu thực vật và các gia vị khác. Sản phẩm sẽ cung cấp năng lượng, chất đạm, chất béo cho cơ thể. Đây là loại snack quen thuộc với nhiều trẻ em bởi vị thơm giòn dễ ăn.\n\nLưu ý khi sử dụng và cách bảo quản sản phẩm\n\nSản phẩm có vị giòn, hương thơm nên khi mở gói, bạn nên sớm sử dụng để tránh không khí làm ảnh hưởng đến độ giòn thơm của bánh. Bảo quản sản phẩm ở nơi khô ráo, thoáng mát, tránh nhiệt độ cao hoặc ánh nắng trực tiếp từ mặt trời.\n\nLưu ý: Không dùng khi sản phẩm hết hạn hoặc có dấu hiệu ẩm mốc.")
                .setDiscount(5)
                .setAvgRating(4.8f)
                .setRatingCount(5)
                .setWeight(32)
                .setWeightUnit("g")
                .build());

        _products.add(new Product.Builder()
                .setId(UUID.fromString("c8cc8d12-424e-447c-9cf9-79922d3e3d2d"))
                .setName("Snack khoai tây vị bít tết kiểu New York Swing gói 32g")
                .setPrice(0.30f)
                .setImage(R.drawable.image_snack_3)
                .setDescription("Snack khoai tây giòn tan, ăn cực đã với hương vị bò bít tết đậm đà thích thú khi ăn. Snack khoai tây vị bít tết kiểu New York Swing gói 32g tiện lợi, dễ mang theo khi đi chơi, dã ngoại. Snack Swing còn thích hợp vừa ăn vừa xem phim, đọc sách")
                .setDiscount(2)
                .setAvgRating(3.0f)
                .setRatingCount(10)
                .setWeight(32)
                .setWeightUnit("g")
                .build());

        _products.add(new Product.Builder()
                .setId(UUID.fromString("e601862a-b2c4-4d1e-87aa-0a9e15e1c4d7"))
                .setName("Snack khoai tây vị tảo biển Nori Lay's gói 58g")
                .setPrice(0.49f)
                .setImage(R.drawable.image_snack_4)
                .setDescription("Snack thơm nức mũi luôn, khoai tây thì mỏng, giòn rụm. Vị khoai tây thật. Snack khoai tây vị tảo biển Nori Lay's gói 58g ăn rất giòn luôn, vị tảo biển Nori khá thơm, đậm đà trên từng miếng khoai tây. Sack khoai tây lay's được rất nhiều bạn trẻ đón nhận và sử dụng cho nhiều hoạt động nhé\n\nĐôi nét về thương hiệu\n\nLay's là thương hiệu khoai tây chiên lớn hàng đầu thế giới, là thương hiệu của Mỹ, thành lập vào năm 1965 khi khởi đầu chỉ là cửa hàng thức ăn nhẹ vào năm 1932, sau đó với việc mua lại nhà máy sản xuất khoai tây và thương hiệu Lay’s cũng chính thức ra đời và trở thành biểu tượng mỗi khi nhắc đến các loại khoai tây chiên thơm ngon độc đáo, có mặt ở rất nhiều các quốc gia trên toàn thế giới và được nhiều khách hàng tin dùng chọn lựa.\n\nThành phần dinh dưỡng trong sản phẩm\n\nSnack khoai tây vị tảo biển Nori Lay's gói 52g dạng snack ăn nhanh, ăn liền tiện dụng với hương vị độc đáo, đóng gói gọn nhẹ, sản phẩm được làm từ các nguyên liệu: Khoai tây, dầu thực vật, bột gia vị Tảo Biển Nori 6% (đường, maltodetrin, muối, hương tự nhiên và giống tự nhiên, chất điều vị,... đảm bảo cung cấp được các chất dinh dưỡng thiết yếu: Chất béo, natri, năng lượng, đường, chất đạm,... Theo như thông tin hãng công bố trong 100g sản phẩm sẽ chứa khoảng 170 calo.\n\nTác dụng của sản phẩm với sức khỏe\n\nSnack khoai tây vị tảo biển Nori Lay's gói 52g với thành phần khoai tây tự nhiên cũng công nghệ sản xuất chuẩn vệ sinh an toàn thực phẩm, sản phẩm dùng ngay khi mở bao bì, bạn có thể dùng thay cho các bữa ăn vặt, giúp bạn qua cơn đó cũng như có năng lượng, chứa các chất dinh dưỡng hoạt động cả ngày. Dùng để ăn vặt giúp giảm căng thẳng và tập trung hơn.")
                .setDiscount(8)
                .setAvgRating(4.8f)
                .setRatingCount(9)
                .setWeight(58)
                .setWeightUnit("g")
                .build());

        _products.add(new Product.Builder()
                .setId(UUID.fromString("d79b40e4-7c2f-4b18-83f4-02b9e1f78a67"))
                .setName("Snack khoai tây vị tảo biển O'Star gói 32g")
                .setPrice(0.28f)
                .setImage(R.drawable.image_snack_5)
                .setDescription("Snack khoai tây giòn thơm ngon với độ dày vừa phải, khi ăn cảm giác tan trong miệng thích thú. Snack khoai tây vị tảo biển O'star gói 32g với hương vị tảo biển lạ miệng thơm ngon, tiện lợi thích hợp đọc sách, xem phim và ăn. Snack O'Star là thương hiệu Hàn Quốc\n\nCó vô vàn loại snack khác nhau và nhưng để tìm được loại snack thích hợp thì Snack khoai tây vị tảo biển O'Star gói 36g sẽ cho bạn khám phá sự khác biệt với việc phối trộn hài hòa khoai tây và rong biển. Sản phẩm là sự phối hợp đặc biệt của các loại nguyên liệu thân thuộc và tươi mát dễ gây nghiện cho những người yêu thích snack ngay lần đầu tiên thưởng thức, vị ngon và mùi vị đặc trưng của rong biển sẽ kích thích bạn không thể ngừng thưởng thức đó.\n\nKhoai tây và rong biển đã được tuyển chọn nghiêm ngặt từ khâu đầu vào cho đến khi ra sản phẩm đóng gói chuyển cho người tiêu dùng. Khoai tây tạo hình cắt mỏng phối trộn với bột rong biển tự nhiên. Trải qua quá trình chế biến và tổng hợp của các loại gia vị bổ sung cho ra miếng khoai tây giòn rụm và màu sắc tuyệt đẹp vàng đặc trưng và hương vị rong biển không lẫn vào đâu được\n\nThiết kế dạng túi dễ bảo quản và dễ mở cho mọi người. Đây là món ăn vặt không thể từ chối để cung cấp nhanh năng lượng trong một ngày làm việc mệt mỏi\n\nĐược tuyển chọn và sản xuất theo quy trình khắc khe từ Hàn Quốc Chắc chắn thương hiệu Snack O'Star bingsu dưa lưới sẽ khiến bạn không thể quên được nếu đã trót thử qua một lần.")
                .setDiscount(0)
                .setAvgRating(5f)
                .setRatingCount(11)
                .setWeight(32)
                .setWeightUnit("g")
                .build());

        _products.add(new Product.Builder()
                .setId(UUID.fromString("15a40d0e-eed7-443c-8bbd-7f202bf1d9c9"))
                .setName("Snack pho mát miếng Oishi gói 39g")
                .setPrice(0.28f)
                .setImage(R.drawable.image_snack_6)
                .setDescription("Snack giòn ngon, thơm thơm vị phô mát kích thích vị giác vô cùng. Snack pho mát miếng Oishi gói 39g hấp dẫn, phù hợp vừa xem phim, vừa nghe nhạc vừa nhâm nhi thưởng thức. Snack Oishi tiện lợi, nhỏ gọn, dễ mang theo cho các buổi hoạt động ngoài trời.\n\nĐôi nét về thương hiệu\n\nNhắc đến các thương hiệu bánh kẹo kinh điển tại Việt Nam bạn không thể nào bỏ qua được Oishi, có nguồn gốc từ đảo quốc Philippines, là 1 trong số các thương hiệu đa quốc gia đặt chân vào Việt Nam và đã có các chiến dịch quảng bá thành công, trở thành thương hiệu quen thuộc, được rất nhiều khách hàng tin dùng và chọn lựa, chiếm lĩnh thị trường cũng như giữ vị trí cao trong sự lựa chọn của khách hàng với các sản phẩm từ bánh, kẹo ăn vặt, đậu phộng, thức uống, snack,...\n\nThành phần dinh dưỡng trong sản phẩm\n\nSnack pho mát 39g snack giòn tan, làm sẵn đóng gói tiện dụng, hơn hết là sản phẩm chay mặn đều dùng được, có vị mặn ngọt đậm đà khó cưỡng, thu hút được nhiều thực khách, làm từ các thành phần như: Bột bắp, tinh bột sắn, dầu ăn, bột phô mai (3%), sữa không kem, muối i-ốt, đường, bột ngọt, bột nổi và anti-oxydant (E320 hoặc E321),...\n\nTheo thông tin hãng công bố, trong 30g bánh sẽ cung cấp khoảng 150 calo.\n\nTác dụng của sản phẩm với sức khỏe\n\nSnack pho mát Oishi gói 39g bánh snack chay mặn đều dùng được, có vị thơm ngon khó cưỡng, thường được sử dụng ăn vặt, tụ tập bạn bè, kích thích vị giác tốt, chứa các chất dinh dưỡng chống đói, giảm căng thẳng, cho bạn tập trung và hoạt động hiệu quả hơn.")
                .setDiscount(3)
                .setAvgRating(4.8f)
                .setRatingCount(5)
                .setWeight(39)
                .setWeightUnit("g")
                .build());

        _reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(5)
                .setContent("Mình đã ăn nhiều loại mì ăn sống nhưng loại này mình thấy ngon nhất nha")
                .setReviewerName("Bùi Thị Ngọc Hoa")
                .setReviewerAvatar(R.drawable.image_avatar_1)
                .setCreatedAt(LocalDateTime.parse("2023-04-03T00:00:00"))
                .build());

        _reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(4)
                .setContent("Sản phẩm dùng rất thích")
                .setReviewerName("Nguyễn Văn Hạnh")
                .setReviewerAvatar(R.drawable.image_avatar_2)
                .setCreatedAt(LocalDateTime.parse("2023-04-04T00:00:00"))
                .build());

        _reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(4)
                .setContent("Snack này rất ngon, giòn giòn vị vừa phải k cay, giá như này mình thấy hơi cao ah")
                .setReviewerName("An Thu Thảo")
                .setReviewerAvatar(R.drawable.image_avatar_3)
                .setCreatedAt(LocalDateTime.parse("2023-04-01T00:00:00"))
                .build());

        _reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(5)
                .setContent("Bé nhà mình thích ăn loại mì này lắm. Mì giòn nhưng không bị cứng như mì bình thường. Vị mặn mặn, ngọt ngọt nữa. Mình cong thích nữa chứ đừng nói con nít.")
                .setReviewerName("Khả Khanh")
                .setReviewerAvatar(R.drawable.image_avatar_4)
                .setCreatedAt(LocalDateTime.parse("2023-04-02T00:00:00"))
                .build());

        _reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(5)
                .setContent("Vị ăn rất ngon, chống đói nhanh chóng")
                .setReviewerName("Lê Tuấn")
                .setReviewerAvatar(R.drawable.image_avatar_5)
                .setCreatedAt(LocalDateTime.parse("2023-04-05T00:00:00"))
                .build());

        _reviews.add(new Review.Builder()
                .setId(UUID.randomUUID())
                .setRating(3)
                .setContent("Tạm được")
                .setReviewerName("Kim Ánh")
                .setReviewerAvatar(R.drawable.image_avatar_6)
                .setCreatedAt(LocalDateTime.parse("2023-04-01T00:00:00"))
                .build());


        _cartItems.add(new CartItem(_products.get(0), 1));
        _cartItems.add(new CartItem(_products.get(1), 2));
        _cartItems.add(new CartItem(_products.get(2), 3));
        _cartItems.add(new CartItem(_products.get(3), 4));

        _orders.add(new Order.Builder()
                .setId(1)
                .setTotalPrice(100000)
                .setName("pending order 1")
                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
                .setPhone("0123456789")
                .setProducts(_cartItems)
                .setStatus(Order.OrderStatus.PENDING)
                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
                .build());

        _orders.add(new Order.Builder()
                .setId(2)
                .setTotalPrice(200000)
                .setName("delivering order 2")
                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
                .setPhone("0123456789")
                .setProducts(_cartItems)
                .setStatus(Order.OrderStatus.DELIVERING)
                        .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
                .build());

        _orders.add(new Order.Builder()
                .setId(3)
                .setTotalPrice(300000)
                .setName("delivered order 3")
                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
                .setPhone("0123456789")
                .setProducts(_cartItems)
                .setStatus(Order.OrderStatus.DELIVERED)
                        .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
                .build());

        _orders.add(new Order.Builder()
                .setId(4)
                .setTotalPrice(400000)
                .setName("canceled order 4")
                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
                .setPhone("0123456789")
                .setProducts(_cartItems)
                .setStatus(Order.OrderStatus.PENDING)
                        .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
                .build());

        _orders.add(new Order.Builder()
                .setId(5)
                .setTotalPrice(500000)
                .setName("pending order 5")
                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
                .setPhone("0123456789")
                .setProducts(_cartItems)
                .setStatus(Order.OrderStatus.PENDING)
                        .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
                .build());

        _orders.add(new Order.Builder()
                .setId(6)
                .setTotalPrice(600000)
                .setName("Pending order 2")
                .setAddress("123 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh")
                .setPhone("0123456789")
                .setProducts(_cartItems)
                .setStatus(Order.OrderStatus.PENDING)
                .setUpdatedAt(LocalDateTime.parse("2023-04-01T00:00:00").toString())
                .build());
    }
}
