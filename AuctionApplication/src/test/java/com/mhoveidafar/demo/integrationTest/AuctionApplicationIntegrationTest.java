package com.mhoveidafar.demo.integrationTest;

import com.mhoveidafar.demo.domain.AuctionItem;
import com.mhoveidafar.demo.domain.Bid;
import com.mhoveidafar.demo.domain.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuctionApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_PostAuctionItem() {

        AuctionItem requestedAuctionItem = AuctionItem.builder()
                .reservePrice(2500)
                .item(Item.builder()
                        .itemId("abcd")
                        .description("item description")
                        .build())
                .build();

        ResponseEntity<AuctionItem> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/auctionItems", requestedAuctionItem, AuctionItem.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_GetAuctionItem() {

        ResponseEntity<List> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/auctionItems", List.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_GetAuctionItemById() {

        AuctionItem requestedAuctionItem = AuctionItem.builder()
                .reservePrice(2500)
                .item(Item.builder()
                        .itemId("efgh")
                        .description("item description")
                        .build())
                .build();

        ResponseEntity<AuctionItem> responsePost = restTemplate.postForEntity(
                "http://localhost:" + port + "/auctionItems", requestedAuctionItem, AuctionItem.class);

        ResponseEntity<AuctionItem> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/auctionItems/" + responsePost.getBody().getAuctionItemId(), AuctionItem.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_PostBid() {

        AuctionItem requestedAuctionItem = AuctionItem.builder()
                .reservePrice(2500)
                .item(Item.builder()
                        .itemId("igkl")
                        .description("item description")
                        .build())
                .build();

        ResponseEntity<AuctionItem> savedAuction = restTemplate.postForEntity(
                "http://localhost:" + port + "/auctionItems", requestedAuctionItem, AuctionItem.class);

        Bid requestedBid = Bid.builder()
                .auctionItemId(savedAuction.getBody().getAuctionItemId())
                .maxAutoBidAmount(3000)
                .bidderName("A Dealership")
                .build();

        ResponseEntity<Bid> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/bids", requestedBid, Bid.class);

        assertEquals(204, response.getStatusCodeValue());
    }
}
