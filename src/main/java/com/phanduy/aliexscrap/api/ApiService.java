package com.phanduy.aliexscrap.api;

import com.phanduy.aliexscrap.model.request.GetItemsByCategoryReq;
import com.phanduy.aliexscrap.model.request.GetStoreInfosReq;
import com.phanduy.aliexscrap.model.response.GetItemsByCategoryResponseData;
import com.phanduy.aliexscrap.model.response.StoreInfoResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("getStoreInfos")
    Call<ApiResponse<StoreInfoResponseData>> getFullStoreInfo(@Body GetStoreInfosReq request);

    @POST("getItemsByCategory")
    Call<ApiResponse<GetItemsByCategoryResponseData>> getItemsByCategory(@Body GetItemsByCategoryReq request);
}
