package com.ally.demo.controller;

import com.ally.demo.domain.AuctionItem;
import com.ally.demo.domain.Bid;
import com.ally.demo.domain.Item;
import com.ally.demo.exception.NotFoundException;
import com.ally.demo.exception.ValidationException;
import com.ally.demo.service.AuctionItemService;
import com.ally.demo.validation.BidValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuctionItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuctionItemService auctionItemService;

    @MockBean
    private BidValidation bidValidation;

    @Test
    public void test_addAuctionItem() throws Exception {
        AuctionItem auctionItem = AuctionItem.builder()
                .reservePrice(200)
                .item(Item.builder()
                        .itemId("product")
                        .build())
                .build();
        Map result = new HashMap();
        result.put("auctionItemId", 1);
        when(auctionItemService.saveAuctionItem(auctionItem)).thenReturn(result);

        this.mockMvc.perform(post("/auctionItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(auctionItem)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(result)));
    }

    @Test
    public void test_getAllAuctionItems() throws Exception {
        List result = new ArrayList<>();
        result.add(AuctionItem.builder().build());
        when(auctionItemService.retrieveAuctionItems()).thenReturn(result);

        this.mockMvc.perform(get("/auctionItems"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(result)));
    }

    @Test
    public void test_getAuctionItemById() throws Exception {
        AuctionItem auctionItem = AuctionItem.builder().build();
        when(auctionItemService.retrieveAuctionItemById(1)).thenReturn(auctionItem);

        this.mockMvc.perform(get("/auctionItems/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(auctionItem)));
    }

    @Test
    public void test_getAuctionItemById_Throws404() throws Exception {
        AuctionItem auctionItem = AuctionItem.builder().build();
        when(auctionItemService.retrieveAuctionItemById(1)).thenThrow(new NotFoundException(1));

        this.mockMvc.perform(get("/auctionItems/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("item with id:1 not found"));
    }

    @Test
    public void test_putBid() throws Exception {
        Bid bid = Bid.builder()
                .auctionItemId(1)
                .maxAutoBidAmount(200)
                .bidderName("fake bidder")
                .build();
        doNothing().when(auctionItemService).placeBid(bid);

        this.mockMvc.perform(post("/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bid)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_putBid_Throws400_ForReservePriceCriteria() throws Exception {
        Bid bid = Bid.builder()
                .auctionItemId(1)
                .maxAutoBidAmount(200)
                .bidderName("fake bidder")
                .build();
        doThrow(new ValidationException("Raise your bid. You have not met the reserve price"))
                .when(auctionItemService).placeBid(bid);

        this.mockMvc.perform(post("/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bid)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Raise your bid. You have not met the reserve price"));
    }

    @Test
    public void test_putBid_Throws400_ForValidation() throws Exception {
        Bid bid = Bid.builder().build();
        doThrow(new ValidationException("Bidder name cannot be blank"))
                .when(bidValidation).validate(bid);

        this.mockMvc.perform(post("/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bid)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bidder name cannot be blank"));
    }
}