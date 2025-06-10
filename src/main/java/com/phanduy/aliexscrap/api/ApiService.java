package com.phanduy.aliexscrap.api;

import com.phanduy.aliexscrap.model.response.CheckInfoResponse;
import com.phanduy.aliexscrap.model.response.ConfigInfo;
import com.phanduy.aliexscrap.model.response.GetPageRapidData;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.model.request.*;
import com.phanduy.aliexscrap.model.request.GetStoreInfoRapidDataReq;
import com.phanduy.aliexscrap.model.response.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("pltool/getStoreInfos")
    Call<ApiResponse<StoreInfoResponseData>> getFullStoreInfo(@Body GetStoreInfosReq request);

    @POST("pltool/getItemsByCategory")
    Call<ApiResponse<GetItemsByCategoryResponseData>> getItemsByCategory(@Body GetItemsByCategoryReq request);

    @POST("pltool/rapid/getProductInfo")
    Call<ApiResponse<NewTransformCrawlResponse>> getProductInfo(@Body TransformRapidDataReqV3 request);

    @POST("pltool/rapid/getProduct/v2/check_configs")
    Call<ApiResponse<ConfigInfo>> checkConfig(@Body CheckConfigsReq request);

    @POST("pltool/rapid/getProduct/v2/storeInfo")
    Call<ApiResponse<GetStoreInfoRapidData>> getStoreInfo(@Body GetStoreInfoRapidDataReq request);

    @POST("pltool/rapid/getProduct/v2/pageInfo")
    Call<ApiResponse<GetPageDataResponse>> getListProductByPage(@Body GetListProductByPageReq request);

    @POST("pltool/rapid/getProduct/classic")
    Call<ApiResponse<NewTransformCrawlResponse>> getProductOld(@Body NewTransformRapidDataReq request);

    @POST("pltool/rapid/getProduct/v2/pageInfo")
    Call<ApiResponse<GetPageRapidData>> getPageData(@Body GetPageDataRapidDataReq request);

    @POST("pltool/rapid/getProduct/v2/search")
    Call<ApiResponse<GetPageRapidData>> searchRapidData(@Body SearchRapidReq request);

    @POST("pltool/rapid/getProduct/v2/new")
    Call<ApiResponse<TransformCrawlResponse>> getNewTemplateProduct(@Body TransformRapidDataReq request);

    @POST("pltool/rapid/getProduct/v2")
    Call<ApiResponse<TransformCrawlResponse>> getOldTemplateProduct(@Body TransformRapidDataReq request);

    @POST("clientinfo/checkserial/new")
    Call<ApiResponse<CheckInfoResponse>> checkSerialInfo(@Body CheckInfoReq request);
}
