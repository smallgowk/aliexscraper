package com.phanduy.aliexscrap.api;

import com.models.request.CheckConfigsReq;
import com.models.request.GetPageDataRapidDataReq;
import com.models.request.SearchRapidReq;
import com.models.request.TransformRapidDataReq;
import com.models.response.ConfigInfo;
import com.models.response.GetPageRapidData;
import com.models.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.model.request.*;
import com.phanduy.aliexscrap.model.response.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("getStoreInfos")
    Call<ApiResponse<StoreInfoResponseData>> getFullStoreInfo(@Body GetStoreInfosReq request);

    @POST("getItemsByCategory")
    Call<ApiResponse<GetItemsByCategoryResponseData>> getItemsByCategory(@Body GetItemsByCategoryReq request);

    @POST("rapid/getProductInfo")
    Call<ApiResponse<NewTransformCrawlResponse>> getProductInfo(@Body TransformRapidDataReqV3 request);

    @POST("rapid/getProduct/v2/check_configs")
    Call<ApiResponse<ConfigInfo>> checkConfig(@Body CheckConfigsReq request);

    @POST("rapid/getProduct/v2/storeInfo")
    Call<ApiResponse<GetStoreInfoRapidData>> getStoreInfo(@Body GetStoreInfoRapidDataReq request);

    @POST("rapid/getProduct/v2/pageInfo")
    Call<ApiResponse<GetPageDataResponse>> getListProductByPage(@Body GetListProductByPageReq request);

    @POST("rapid/getProduct/classic")
    Call<ApiResponse<NewTransformCrawlResponse>> getProductOld(@Body NewTransformRapidDataReq request);

    @POST("rapid/getProduct/v2/pageInfo")
    Call<ApiResponse<GetPageRapidData>> getPageData(@Body GetPageDataRapidDataReq request);

    @POST("rapid/getProduct/v2/search")
    Call<ApiResponse<GetPageRapidData>> searchRapidData(@Body SearchRapidReq request);

    @POST("rapid/getProduct/v2/new")
    Call<ApiResponse<TransformCrawlResponse>> getNewTemplateProduct(@Body TransformRapidDataReq request);

    @POST("rapid/getProduct/v2")
    Call<ApiResponse<TransformCrawlResponse>> getOldTemplateProduct(@Body TransformRapidDataReq request);
}
