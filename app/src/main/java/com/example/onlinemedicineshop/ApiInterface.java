package com.example.onlinemedicineshop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("isUser.php")
    Call<ServerResponse> isUser(@Query("email") String email, @Query("password") String password);

    @GET("registration.php")
    Call<ServerResponse> registration(@Query("name") String name,@Query("email") String email,
                                      @Query("password") String password, @Query("contact") String contact,
                                      @Query("address") String address);

    @GET("getUser.php")
    Call<List<User>> getUser(@Query("email") String email, @Query("password") String password);

//    @GET("getSupplier.php")
//    Call<List<Supplier>> getSupplier(@Query("email") String email, @Query("password") String password);

    @GET("getUserById.php")
    Call<List<User>> getUserById(@Query("id") int id);
//
//    @GET("getAllCategory.php")
//    Call<List<Category>> getAllCategory();

//    @GET("getAllProduct.php")
//    Call<List<Product>> getAllProduct();
//
    @GET("getOrders.php")
    Call<List<Order>> getOrders(@Query("userId") int userId,@Query("status") String status);
//
//    @GET("doneOrderBySupplier.php")
//    Call<ServerResponse> doneOrderBySupplier(@Query("orderId") int orderId,@Query("supplierId") int supplierId);
//
//    @GET("doneOrderByCustomer.php")
//    Call<ServerResponse> doneOrderByCustomer(@Query("orderId") int orderId);
//
    @GET("getMostSellsMedicine.php")
    Call<List<StockEntity>> getMostSellsMedicine();

    @GET("getStock.php")
    Call<List<StockEntity>> getStock();

    @GET("getStockById.php")
    Call<List<StockEntity>> getStockById(@Query("id") int id);

    @GET("getAllTips.php")
    Call<List<ProductTipsEntity>> getAllTips();

    @GET("getAllMedicineInfo.php")
    Call<List<ProductTipsEntity>> getAllMedicineInfo();

    @GET("getAllBrand.php")
    Call<List<BrandEntity>> getAllBrand();

    @GET("getAllCategory.php")
    Call<List<BrandEntity>> getAllCategory();

    @GET("getMedicineByBrand.php")
    Call<List<StockEntity>> getMedicineByBrand(@Query("brand") String brand);

    @GET("getMedicineByCategory.php")
    Call<List<StockEntity>> getMedicineByCategory(@Query("type") String type);

    @GET("searchMedicine.php")
    Call<List<StockEntity>> searchMedicine(@Query("key") String key);

    @GET("getAllBestSeller.php")
    Call<List<BrandEntity>> getAllBestSeller();

    @GET("getProductsByCategory.php")
    Call<List<StockEntity>> getProductsByCategory(@Query("type") String type);

    @GET("order.php")
    Call<ServerResponse> order(@Query("userId") int userId, @Query("qtn") int qtn, @Query("cost") int cost,
               @Query("orderDate") String orderDate, @Query("deliveryDate") String deliveryDate,
       @Query("deliverySystem") String deliverySystem,@Query("payment") String payment ,@Query("status") String status);

    @GET("editProfile.php")
    Call<ServerResponse> editProfile(@Query("id") int id,@Query("name") String name,@Query("email")
            String email, @Query("password") String password, @Query("contact") String contact,
                                     @Query("address") String address);

    @GET("addReview.php")
    Call<ServerResponse> addReview(@Query("orderid") int orderid,@Query("msg") String msg);

    @GET("getAllMyCart.php")
    Call<List<CartEntity>> getAllMyCart(@Query("userId") int userId);

    @GET("addToCart.php")
    Call<ServerResponse> addToCart(@Query("userId") int userId, @Query("stockId") int stockId,
                               @Query("name") String name,@Query("qtn") int qtn, @Query("cost") int cost);

    @GET("updateStock.php")
    Call<ServerResponse> updateStock(@Query("stockId") int stockId,@Query("qtn") int qtn);

    @GET("deleteCartItems.php")
    Call<ServerResponse> deleteCartItems(@Query("userId") int userId);

    @GET("isSeenOrder.php")
    Call<ServerResponse> isSeenOrder(@Query("userId") int userId);

    @GET("setSeenOrder.php")
    Call<ServerResponse> setSeenOrder(@Query("userId") int userId);

}
