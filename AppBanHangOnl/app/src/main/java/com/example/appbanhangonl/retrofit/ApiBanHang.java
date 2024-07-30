package com.example.appbanhangonl.retrofit;

import com.example.appbanhangonl.model.Category;
import com.example.appbanhangonl.model.MessageModel;
import com.example.appbanhangonl.model.Product;
import com.example.appbanhangonl.model.ThongKeModel;
import com.example.appbanhangonl.model.User;
import com.example.appbanhangonl.model.ViewOrders;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiBanHang {
    @GET("getcategory.php")
    Observable<Category> getAPICategory();

    @GET("getproduct.php")
    Observable<Product> getAPIProduct();

    @POST("getinfoproduct.php")
    @FormUrlEncoded
    Observable<Product> getAPIProduct(
            @Field("page") int page,
            @Field("info") int info
    );

    @POST("register.php")
    @FormUrlEncoded
    Observable<User> registerAPI(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("uid") String uid
    );

    @POST("login.php")
    @FormUrlEncoded
    Observable<User> loginAPI(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<User> resetpassAPI(
            @Field("email") String email
    );

    @POST("bill.php")
    @FormUrlEncoded
    Observable<User> billAPI(
            @Field("email") String email,
            @Field("total") String total,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("quanlity") int quanlity,
            @Field("iduser") int iduser,
            @Field("billinfo") String billinfo
    );

    @POST("vieworders.php")
    @FormUrlEncoded
    Observable<ViewOrders> viewordersAPI(
            @Field("iduser") int id
    );

    @POST("search.php")
    @FormUrlEncoded
    Observable<Product> searchsAPI(
            @Field("search") String search
    );

    @POST("deletenewproduct.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteNewProduct (
            @Field("MaSP") int id
    );

    @POST("insertsp.php")
    @FormUrlEncoded
    Observable<MessageModel> insertSp(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int id,
            @Field("soLuongTon") int soLuongTon
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesp (
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int idloai,
            @Field("MaSP") int id,
            @Field("soLuongTon") int soLuongTon
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken (
            @Field("id") int id,
            @Field("token") String token
    );

    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);

    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<User> gettoken (
            @Field("status") int status,
            @Field("iduser") int iduser
    );

    @POST("updateorders.php")
    @FormUrlEncoded
    Observable<MessageModel> updateOrders (
            @Field("id") int id,
            @Field("trangthai") int status
    );

    @GET("thongkehang.php")
    Observable<ThongKeModel> getthongkeThang();

    @GET("thongke.php")
    Observable<ThongKeModel> getthongke();

    @GET("updateprofile.php")
    Call<Void> updateUserProfile(
            @Query("email") String email,
            @Query("username") String username,
            @Query("mobile") String mobile,
            @Query("ImageUser") String ImageUser
    );

    @Multipart
    @POST("uploadImage.php")
    Call<String> uploadImage(@Part MultipartBody.Part image);

    @GET("getalltoken.php")
    Observable<User> getAllToken();
}
