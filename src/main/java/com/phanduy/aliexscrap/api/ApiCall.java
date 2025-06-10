package com.phanduy.aliexscrap.api;

import com.phanduy.aliexscrap.model.response.ConfigInfo;
import com.phanduy.aliexscrap.model.response.GetPageRapidData;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.model.request.*;
import com.phanduy.aliexscrap.model.response.CheckInfoResponse;
import com.phanduy.aliexscrap.model.response.GetStoreInfoRapidData;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiCall {

    private static ApiCall apiCall;

    public ApiCall() {
        this.apiService = ApiClient.getClient().create(ApiService.class);
        this.apiServiceNoLog = ApiClient.getClient().create(ApiService.class);
    }

    public static ApiCall getInstance() {
        if (apiCall == null) {
            apiCall = new ApiCall();
        }
        return apiCall;
    }

    ApiService apiService;
    ApiService apiServiceNoLog;

    public ConfigInfo getConfig(CheckConfigsReq checkConfigsReq) {
        Call<ApiResponse<ConfigInfo>> call = apiService.checkConfig(checkConfigsReq);
        Response<ApiResponse<ConfigInfo>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        }
        return null;
    }

    public GetStoreInfoRapidData getStoreInfoRapidData(GetStoreInfoRapidDataReq getStoreInfoRapidDataReq) {
        Call<ApiResponse<GetStoreInfoRapidData>> call = apiService.getStoreInfo(getStoreInfoRapidDataReq);
        Response<ApiResponse<GetStoreInfoRapidData>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        }
        return null;
    }

    public GetPageRapidData getPageData(GetPageDataRapidDataReq getPageDataRapidDataReq) {
        Call<ApiResponse<GetPageRapidData>> call = apiService.getPageData(getPageDataRapidDataReq);
        Response<ApiResponse<GetPageRapidData>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        }
        return null;
    }

    public GetPageRapidData searchPageData(SearchRapidReq request) {
        Call<ApiResponse<GetPageRapidData>> call = apiService.searchRapidData(request);
        Response<ApiResponse<GetPageRapidData>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        }
        return null;
    }

    public TransformCrawlResponse getNewTemplateProduct(TransformRapidDataReq request) throws Exception {
        Call<ApiResponse<TransformCrawlResponse>> call = apiServiceNoLog.getNewTemplateProduct(request);
        Response<ApiResponse<TransformCrawlResponse>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        } else {
            if (response.body() != null && response.body().error != null) {
                throw new Exception(response.body().error);
            } else {
                return null;
            }
        }
    }

    public TransformCrawlResponse getOldTemplateProduct(TransformRapidDataReq request) throws Exception {
        Call<ApiResponse<TransformCrawlResponse>> call = apiServiceNoLog.getOldTemplateProduct(request);
        Response<ApiResponse<TransformCrawlResponse>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        } else {
            if (response.body() != null && response.body().error != null) {
                throw new Exception(response.body().error);
            } else {
                return null;
            }
        }
    }

    public CheckInfoResponse checkInfo(CheckInfoReq request) throws Exception {
        Call<ApiResponse<CheckInfoResponse>> call = apiService.checkSerialInfo(request);
        Response<ApiResponse<CheckInfoResponse>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        } else {
            if (response.body() != null && response.body().error != null) {
                throw new Exception(response.body().error);
            } else {
                return null;
            }
        }
    }
}
