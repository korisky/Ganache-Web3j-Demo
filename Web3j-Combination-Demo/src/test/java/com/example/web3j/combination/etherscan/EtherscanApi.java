package com.example.web3j.combination.etherscan;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.web3j.combination.ssl.TrustAllX509CertManager;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Roylic
 * 2023/7/21
 */
public class EtherscanApi {


    private static final String ETHERSCAN_BASE_API = "https://api.etherscan.io/api";


    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(TrustAllX509CertManager.getSslSocketFactory(), new TrustAllX509CertManager()) // trust all certs
            .hostnameVerifier((s, sslSession) -> true) // trust for all hostname
            .retryOnConnectionFailure(false)
            .build();


    @Test
    public void plainTxn() throws IOException {

        String address = "0xE304450bF78e357C3edc790C98fcb28dDeb87830";
        Long startBlock = 0L;
        Long endBlock = 99999999L;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(ETHERSCAN_BASE_API).newBuilder();
        urlBuilder.addQueryParameter("module", "account")
                .addQueryParameter("action", "txlist")
                .addQueryParameter("address", address)
                .addQueryParameter("startblock", startBlock.toString())
                .addQueryParameter("endblock", endBlock.toString())
                .addQueryParameter("sort", "desc");

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .get()
                .build();

        Response response = okHttpClient.newCall(request).execute();
        JSONObject respJson = JSON.parseObject(response.body().string());
        System.out.println(respJson);
    }

}
