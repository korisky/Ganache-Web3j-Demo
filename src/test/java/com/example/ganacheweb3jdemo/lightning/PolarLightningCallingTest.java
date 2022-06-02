package com.example.ganacheweb3jdemo.lightning;

import com.example.ganacheweb3jdemo.web3j.okhttp.interceptor.ApplicationInterceptorImp;
import com.example.ganacheweb3jdemo.web3j.okhttp.interceptor.LogInterceptorImp;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.lightningj.lnd.wrapper.StatusException;
import org.lightningj.lnd.wrapper.SynchronousLndAPI;
import org.lightningj.lnd.wrapper.ValidationException;
import org.lightningj.lnd.wrapper.message.*;
import org.web3j.utils.Numeric;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 尝试进行RPC解析测试
 * cert: https://www.baeldung.com/okhttp-self-signed-cert
 * trust: https://github.com/lightningnetwork/lnd/blob/master/docs/grpc/java.md
 *
 * @author Roylic
 * @date 2022/4/21
 */
public class PolarLightningCallingTest {

    private final static String POLAR_BASE_URL = "https://127.0.0.1:";
    private final static String POLAR_MACAROON_LOC = "/Users/pundix2022/.polar/networks/1/volumes/lnd";

    // Alice
    private final static int ALICE_GRPC_PORT = 10001;
    private final static String ALICE_CERT = POLAR_MACAROON_LOC + "/alice/tls.cert";
    private final static String ALICE_MACAROON = POLAR_MACAROON_LOC + "/alice/data/chain/bitcoin/regtest/admin.macaroon";
    private final static String ALICE_PUB_KEY = "02e5e73d1654251a54709341cbda20f8441a639d6ac24e8f5ff0f2b15ef0aaacb5";

    // Erin
    private final static int ERIN_GRPC_PORT = 10005;
    private final static String ERIN_CERT = POLAR_MACAROON_LOC + "/erin/tls.cert";
    private final static String ERIN_MACAROON = POLAR_MACAROON_LOC + "/erin/data/chain/bitcoin/regtest/admin.macaroon";
    private final static String ERIN_PUB_KEY = "02cabd7ebe714e37fad2f25e07f9bdde8713f44c16af2d74e3d0b278727b62d1fc";

    // Dave
    private final static int DAVE_GRPC_PORT = 10004;
    private final static String DAVE_CERT = POLAR_MACAROON_LOC + "/dave/tls.cert";
    private final static String DAVE_MACAROON = POLAR_MACAROON_LOC + "/dave/data/chain/bitcoin/regtest/admin.macaroon";
    private final static String DAVE_PUB_KEY = "0216ba75f68d5e695c754696751080e98db0f1d8674fa72b1c1df221f25fecc2f5";

    // Bob - c-lightning
    private final static String BOB_REST_API = "http://127.0.0.1:8182/";
    private final static String BOB_MACAROON = "/Users/pundix2022/.polar/networks/1/volumes/c-lightning/bob/rest-api/access.macaroon";


    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new ApplicationInterceptorImp())
            .addNetworkInterceptor(new LogInterceptorImp())
            .retryOnConnectionFailure(false)
            .build();


    // ************************************************** Grpc Request *********************************************************
    @Test
    public void LND_SyncChannelBalanceTest_Alice() throws IOException, StatusException, ValidationException {

        // Alice
        SynchronousLndAPI synchronousLndAPI = new SynchronousLndAPI(
                "127.0.0.1",
                ALICE_GRPC_PORT,
                new File(ALICE_CERT),
                new File(ALICE_MACAROON));
        System.out.println("\nAlice\n");
        System.out.println(synchronousLndAPI.listChannels(new ListChannelsRequest()).toJsonAsString(true));
//        System.out.println(synchronousLndAPI.channelBalance().toJsonAsString(true));
//        System.out.println(synchronousLndAPI.walletBalance().toJsonAsString(true));
//        System.out.println(synchronousLndAPI.listInvoices(new ListInvoiceRequest()).toJsonAsString(true));
//
//        System.out.println(synchronousLndAPI.listPayments(new ListPaymentsRequest()).toJsonAsString(true));

//        System.out.println(synchronousLndAPI.getChanInfo(148434069815297L));

//        SendRequest sendRequest = new SendRequest();
//        sendRequest.setPaymentRequest("lnbcrt500u1p3g73jspp5v8s6mywu3mlcz38y8axej6fus3y4ex8v3qfhfqxzw6md45ngznnsdqqcqzpgsp5ffxyh20dc4ztv6lwaj9k6pf7n9n5w705xzuuu6pu9mpangtnxfjs9qyyssq255dnth59h7dgu9nyh2ytu2j2r57npqy4w6esyv8tk5w3skj90uhgy9vrsqxy33sxq85aqqh59jq685anzxlm0w9fsk4y72jvls3eggq622hzh");
//
//        SendResponse sendResponse = synchronousLndAPI.sendPaymentSync(sendRequest);
//        System.out.println(sendResponse.toJsonAsString(true));

    }

    @Test
    public void LND_SyncChannelBalanceTest_Erin() throws IOException, StatusException, ValidationException {
        // Erin
        SynchronousLndAPI synchronousLndAPI = new SynchronousLndAPI(
                "127.0.0.1",
                ERIN_GRPC_PORT,
                new File(ERIN_CERT),
                new File(ERIN_MACAROON));
        System.out.println("\nErin\n");
        System.out.println(synchronousLndAPI.listChannels(new ListChannelsRequest()).toJsonAsString(true));
        System.out.println(synchronousLndAPI.channelBalance().toJsonAsString(true));
        System.out.println(synchronousLndAPI.walletBalance().toJsonAsString(true));
//        System.out.println(synchronousLndAPI.getChanInfo(130841883770880L));
//        System.out.println(synchronousLndAPI.listInvoices(new ListInvoiceRequest()));
    }

    @Test
    public void LND_SyncChannelBalanceTest_Dave() throws IOException, StatusException, ValidationException {

        // Dave
        SynchronousLndAPI synchronousLndAPI = new SynchronousLndAPI(
                "127.0.0.1",
                DAVE_GRPC_PORT,
                new File(DAVE_CERT),
                new File(DAVE_MACAROON));
        System.out.println("\nDave\n");
        System.out.println(synchronousLndAPI.listChannels(new ListChannelsRequest()).toJsonAsString(true));
        System.out.println(synchronousLndAPI.channelBalance().toJsonAsString(true));
        System.out.println(synchronousLndAPI.walletBalance().toJsonAsString(true));
//        System.out.println(synchronousLndAPI.getChanInfo(130841883770880L));

//        ListInvoiceRequest listInvoiceRequest = new ListInvoiceRequest();
//        listInvoiceRequest.setPendingOnly(true);
//        System.out.println(synchronousLndAPI.listInvoices(listInvoiceRequest));
    }

    @Test
    public void LND_OpenChannel_ByRpcAPI_Alice2Erin() throws StatusException, IOException, ValidationException {

        SynchronousLndAPI synchronousLndAPI = new SynchronousLndAPI(
                "127.0.0.1",
                ALICE_GRPC_PORT,
                new File(ALICE_CERT),
                new File(ALICE_MACAROON));

        OpenChannelRequest openChannelRequest = new OpenChannelRequest();
        openChannelRequest.setNodePubkey(Numeric.hexStringToByteArray(ERIN_PUB_KEY));
        // The number of SATs the wallet should commit to the channel
        openChannelRequest.setLocalFundingAmount(2_000_000L);
        // The number of SATs to push to the remote side as part of the initial commitment state
        openChannelRequest.setPushSat(5_000L);
        // The target number of blocks that the funding transaction should be confirmed by.
        openChannelRequest.setTargetConf(3);
        // Whether this channel should be private, not announced to the greater network.
        openChannelRequest.setPrivate(false);
        // The minimum number of confirmations each one of your outputs used for the funding transaction must satisfy.
        openChannelRequest.setMinConfs(5);
        // Whether unconfirmed outputs should be used as inputs for the funding transaction.
        openChannelRequest.setSpendUnconfirmed(false);


        // request
        Iterator<OpenStatusUpdate> result = synchronousLndAPI.openChannel(openChannelRequest);
        while (result.hasNext()) {
            System.out.println("Received Update: " + result.next().toJsonAsString(true));
        }

        // close stub
        synchronousLndAPI.close();
    }

    @Test
    public void LND_OpenChannel_ByRpcAPI_Erin2Dave() throws StatusException, IOException, ValidationException {

        SynchronousLndAPI synchronousLndAPI = new SynchronousLndAPI(
                "127.0.0.1",
                ERIN_GRPC_PORT,
                new File(ERIN_CERT),
                new File(ERIN_MACAROON));

        OpenChannelRequest openChannelRequest = new OpenChannelRequest();
        openChannelRequest.setNodePubkey(Numeric.hexStringToByteArray(DAVE_PUB_KEY));
        // The number of SATs the wallet should commit to the channel
        openChannelRequest.setLocalFundingAmount(1_000_000L);
        // The number of SATs to push to the remote side as part of the initial commitment state
        openChannelRequest.setPushSat(50_000L);
        // The target number of blocks that the funding transaction should be confirmed by.
        openChannelRequest.setTargetConf(3);
        // Whether this channel should be private, not announced to the greater network.
        openChannelRequest.setPrivate(false);
        // The minimum number of confirmations each one of your outputs used for the funding transaction must satisfy.
        openChannelRequest.setMinConfs(5);
        // Whether unconfirmed outputs should be used as inputs for the funding transaction.
        openChannelRequest.setSpendUnconfirmed(false);


        // request
        Iterator<OpenStatusUpdate> result = synchronousLndAPI.openChannel(openChannelRequest);
        while (result.hasNext()) {
            System.out.println("Received Update: " + result.next().toJsonAsString(true));
        }

        // close stub
        synchronousLndAPI.close();
    }

    @Test
    public void LND_CreateInvoice_ByRpcAPI() throws StatusException, SSLException, ValidationException {

        SynchronousLndAPI synchronousLndAPI = new SynchronousLndAPI(
                "127.0.0.1",
                DAVE_GRPC_PORT,
                new File(DAVE_CERT),
                new File(DAVE_MACAROON));

        Invoice invoice = new Invoice();
        // 	An optional memo to attach along with the invoice. Used for record keeping purposes for the invoice's creator,
        // 	and will also be set in the description field of the encoded payment request if
        // 	the description_hash field is not being used.
        invoice.setMemo("Dave's invoice should be paid by Alice 10,000");
        // The value of this invoice in satoshis The fields value and value_msat are mutually exclusive.
        // if the value is smaller than node's routing policy, this invoice could not 'see' this route
        invoice.setValue(10_000L);

        AddInvoiceResponse addInvoiceResponse = synchronousLndAPI.addInvoice(invoice);
        System.out.println(addInvoiceResponse.toJsonAsString(true));
    }

    @Test
    public void LND_SendPayment_ByRpcAPI() throws StatusException, SSLException, ValidationException {
        // Alice
        SynchronousLndAPI synchronousLndAPI_Alice = new SynchronousLndAPI(
                "127.0.0.1",
                ALICE_GRPC_PORT,
                new File(ALICE_CERT),
                new File(ALICE_MACAROON));
        System.out.println("\nAlice\n");

        SendRequest sendRequest = new SendRequest();
        sendRequest.setPaymentRequest("lnbcrt100u1p3g75mdpp5my0ndmtkdcw6xk5qjul7sut8zjclxfhk25um2lwycavu0zwxp0yqdzgg3shvef8wvsxjmnkda5kxefqwd5x7atvvssxyefqwpskjepqvfujqstvd93k2gp3xqkrqvpscqzpgsp5l8x3pw2q8sryzst5pqd562k070umghcul50x04xyj9srm8vnjakq9qyyssq7m7qmqjxla95p0gs83at5vnfh8z29zcardcl4evt9luz0vlppla5n58zlf3s9glxnjt0km8rg0gpqr2480zw6ccn9ds8mscu0ay583sphqhtue");

        SendResponse sendResponse = synchronousLndAPI_Alice.sendPaymentSync(sendRequest);
        System.out.println(sendResponse.toJsonAsString(true));
    }

    @Test
    public void LND_CloseChannel_ByRpcAPI() throws StatusException, SSLException, ValidationException {

        SynchronousLndAPI synchronousLndAPI_Alice = new SynchronousLndAPI(
                "127.0.0.1",
                ALICE_GRPC_PORT,
                new File(ALICE_CERT),
                new File(ALICE_MACAROON));

        ChannelPoint channelPoint = new ChannelPoint();
        // before :
        channelPoint.setFundingTxidStr("0c50ff69a260ba757c72bfbefea7ab13c5bce52afbc14334a004503f82d83a0f");
        // after :
        channelPoint.setOutputIndex(1);

        CloseChannelRequest closeChannelRequest = new CloseChannelRequest();
        // channel point
        closeChannelRequest.setChannelPoint(channelPoint);
        // force to close the channel
        closeChannelRequest.setForce(false);
        // target number of blocks that the closure transaction should be confirmed by.
        closeChannelRequest.setTargetConf(3);

        // request
        Iterator<CloseStatusUpdate> result = synchronousLndAPI_Alice.closeChannel(closeChannelRequest);
        while (result.hasNext()) {
            System.out.println("Received Update: " + result.next().toJsonAsString(true));
        }

        // close stub
        synchronousLndAPI_Alice.close();
    }


    @Test
    public void LND_EstimateFee_ByRpcAPI() throws StatusException, SSLException, ValidationException {
        SynchronousLndAPI synchronousLndAPI_Alice = new SynchronousLndAPI(
                "127.0.0.1",
                ALICE_GRPC_PORT,
                new File(ALICE_CERT),
                new File(ALICE_MACAROON));

        Map<String, Long> reqMap = new HashMap<>();
        reqMap.put("0336a9dc9c596320c7dabedc8e02299040e4a8224d1a16237d1caef118593d8508", 500_000L);

        EstimateFeeRequest request = new EstimateFeeRequest();
        request.setAddrToAmount(reqMap);

        EstimateFeeResponse estimateFeeResponse = synchronousLndAPI_Alice.estimateFee(request);
        System.out.println(estimateFeeResponse.toJsonAsString(true));

    }


    // ************************************************** Rest Request **************************************************

    @Test
    public void LND_SyncRestTest() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // By Rest

        // this is for verifying
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final Certificate certificate = cf.generateCertificate(new FileInputStream(ALICE_CERT));

        // encode binary macaroon into hex string
        String macaroonHexStr = Hex.encodeHexString(Files.readAllBytes(Paths.get(ALICE_MACAROON)));
        // When using Numeric, it will automatically add 0x, at the front... but LND would not accept this 0x
        String macaroon0xStr = Numeric.toHexString(Files.readAllBytes(Paths.get(ALICE_MACAROON)));
        if (macaroon0xStr.substring(2).equals(macaroonHexStr)) {
            System.out.println(">>> They are the same");
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://127.0.0.1:8081" + "/v1/getinfo").newBuilder();
        String url = urlBuilder
                .build()
                .toString();

        Request requestGet = new Request.Builder()
                .url(url)
                .header("Grpc-Metadata-macaroon", macaroonHexStr)
                .build();

        // For TrustManager
        X509TrustManager TRUST_FILES_CERTS = generateTrustManagerByFile(certificate);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{TRUST_FILES_CERTS}, new java.security.SecureRandom());

        OkHttpClient okHttpClient_sin = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), TRUST_FILES_CERTS)
                .build();
        Call call = okHttpClient_sin.newCall(requestGet);

        ResponseBody body = call.execute().body();
        if (null != body) {
            System.out.println();
            System.out.println(body.string());
            System.out.println();
        }
    }

    @NotNull
    private X509TrustManager generateTrustManagerByFile(Certificate certificate) {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                try {
                    certificate.verify(chain[0].getPublicKey());
                } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                    System.out.println("Client Illegal");
                    e.printStackTrace();
                }
                System.out.println("Client Trusted");
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                try {
                    certificate.verify(chain[0].getPublicKey());
                } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                    System.out.println("Server Illegal");
                    e.printStackTrace();
                }
                System.out.println("Server Trusted");
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
    }


    /**
     * c-lightning test
     */
    @Test
    public void CLIGHTNING_SyncChannelBalanceTest() throws IOException, NoSuchAlgorithmException, KeyManagementException {

        // toBase64String already contains calling the encode function
        byte[] macaroonBytes = Files.readAllBytes(Paths.get(BOB_MACAROON));
        String macaroonBase64 = Base64.toBase64String(macaroonBytes);

        String real_base64 = "AgELYy1saWdodG5pbmcCPk1vbiBNYXkgMjMgMjAyMiAwMzo1MzoyMSBHTVQrMDAwMCAoQ29vcmRpbmF0ZWQgVW5pdmVyc2FsIFRpbWUpAAAGIFt67R66vxKgyh+4mRWNozBf+La8r4cxrTQWgeH6Laxp";
        if (macaroonBase64.equals(real_base64)) {
            System.out.println("Base64 equals");
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BOB_REST_API + "v1/getinfo").newBuilder();
        String url = urlBuilder
                .build()
                .toString();

        Request requestGet = new Request.Builder()
                .url(url)
                .header("macaroon", macaroonBase64)
                .build();

        Call call = okHttpClient.newCall(requestGet);

        ResponseBody body = call.execute().body();
        if (null != body) {
            System.out.println();
            System.out.println(body.string());
            System.out.println();
        }
    }
}
