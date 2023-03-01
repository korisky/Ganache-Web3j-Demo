package com.example.web3j.combination.eth;

import org.junit.jupiter.api.Test;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Roylic
 * 2023/3/1
 */
public class EthNftTest {

    private static final String web3Url = "https://goerli.infura.io/v3/3f0482cf4c3545dbabaeab75f414e467";

    public static final Web3j web3j = Web3j.build(new HttpService(web3Url));

    @Test
    public void specificOpenSeaMintFunction() {
        Function mintPublic = new Function(
                "mintPublic",
                Arrays.asList(Address.DEFAULT, Address.DEFAULT, Address.DEFAULT, Uint256.DEFAULT),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(mintPublic);
        System.out.println(encode.substring(0, 10));
    }

    @Test
    public void specificOpenSeaDropMintEvent() {
        Event seaDropMint = new Event("SeaDropMint",
                Arrays.asList(
                        TypeReference.create(Address.class, true), // nft-contract
                        TypeReference.create(Address.class, true), // minter
                        TypeReference.create(Address.class, true), // feeRecipient
                        TypeReference.create(Address.class), // payer
                        TypeReference.create(Uint256.class), // quantityMinted
                        TypeReference.create(Uint256.class), // uintMintPrice
                        TypeReference.create(Uint256.class), // feeBps
                        TypeReference.create(Uint256.class) // dropStageIndex
                )
        );
        String encode = EventEncoder.encode(seaDropMint);
        System.out.println(encode);
    }

    @Test
    public void specificOpenSeaDropMintEventLog() throws IOException {
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(8570683)),
                DefaultBlockParameter.valueOf(BigInteger.valueOf(8570683)),
                Collections.emptyList());

        Event seaDropMint = new Event("SeaDropMint",
                Arrays.asList(
                        TypeReference.create(Address.class, true), // nft-contract
                        TypeReference.create(Address.class, true), // minter
                        TypeReference.create(Address.class, true), // feeRecipient
                        TypeReference.create(Address.class), // payer
                        TypeReference.create(Uint256.class), // quantityMinted
                        TypeReference.create(Uint256.class), // uintMintPrice
                        TypeReference.create(Uint256.class), // feeBps
                        TypeReference.create(Uint256.class) // dropStageIndex
                )
        );
        filter.addOptionalTopics(EventEncoder.encode(seaDropMint));
        EthLog logs = web3j.ethGetLogs(filter).send();

        // filter
        List<EthLog.LogResult> caredLog = logs.getLogs().stream().filter(curLog ->
                        "0x0e71420f7b965345248f33764327619ea5680356fd1d87754a9f9be00be37aeb"
                                .equals(((EthLog.LogObject) curLog).getTransactionHash()))
                .collect(Collectors.toList());

        // decoding log
        EthLog.LogResult logResult = caredLog.get(0);
        EthLog.LogObject log = (EthLog.LogObject) logResult;
        List<String> topics = log.getTopics();
        Address nftAddress = new Address(topics.get(1));
        Address minterAddress = new Address(topics.get(2));
        Address feeRecipientAddress = new Address(topics.get(3));
        System.out.println("NFT Address: " + nftAddress.getValue());
        System.out.println("Minter Address: " + minterAddress.getValue());
        System.out.println("Fee Recipient Address: " + feeRecipientAddress.getValue());
        System.out.println();

        // decoding Event data
        List<Type> decode = FunctionReturnDecoder.decode(log.getData(), seaDropMint.getNonIndexedParameters());
        Address payerAddress = (Address) decode.get(0);
        Uint256 quantityMined = (Uint256) decode.get(1);
        Uint256 uintMintPrice = (Uint256) decode.get(2);
        Uint256 feeBps = (Uint256) decode.get(3);
        Uint256 dropStageIndex = (Uint256) decode.get(4);
        System.out.println("Payer Address: " + payerAddress.getValue());
        System.out.println("Quantity Mined: " + quantityMined.getValue());
        System.out.println("Uint Mint Price: " + uintMintPrice.getValue());
        System.out.println("Fee Bps: " + feeBps.getValue());
        System.out.println("Drop Stage Index: " + dropStageIndex.getValue());

        System.out.println();
    }


}
