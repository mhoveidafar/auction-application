package com.ally.demo.service;

import com.ally.demo.domain.AuctionItem;
import com.ally.demo.domain.Bid;
import com.ally.demo.exception.NotFoundException;
import com.ally.demo.exception.ValidationException;
import com.ally.demo.repository.AuctionItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuctionItemServiceTest {

    @MockBean
    AuctionItemRepository auctionItemRepository;

    @Autowired
    AuctionItemService auctionItemService;

    private AuctionItem auctionItem;

    @Before
    public void setUp() {
        auctionItem = AuctionItem.builder().build();
    }

    @Test
    public void test_saveAuctionItem() {
        AuctionItem expectedAuctionItem = AuctionItem.builder()
                .auctionItemId(1)
                .build();
        Map<String, Integer> expectedResponse = new HashMap<>();
        expectedResponse.put("auctionItemId", 1);

        when(auctionItemRepository.save(auctionItem)).thenReturn(expectedAuctionItem);

        Map<String, Integer> actualResponse = auctionItemService.saveAuctionItem(auctionItem);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void test_retrieveAuctionItems() {

        List<AuctionItem> expectedList = new ArrayList<>();
        expectedList.add(auctionItem);
        when(auctionItemRepository.findAll()).thenReturn(expectedList);

        List<AuctionItem> actualList = auctionItemService.retrieveAuctionItems();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void test_retrieveAuctionItemById() {

        when(auctionItemRepository.findById(1)).thenReturn(Optional.ofNullable(auctionItem));

        AuctionItem actualAuctionItem = auctionItemService.retrieveAuctionItemById(1);

        assertEquals(auctionItem, actualAuctionItem);
    }

    @Test(expected = NotFoundException.class)
    public void retrieveAuctionItemById_throwsException() {

        AuctionItem actualAuctionItem = auctionItemService.retrieveAuctionItemById(1);
    }

    @Test
    public void test_placeBid() {

        Bid bid = Bid.builder()
                .auctionItemId(1)
                .bidderName("fake name")
                .maxAutoBidAmount(2300.00)
                .build();
        AuctionItem auctionItem = AuctionItem.builder()
                .auctionItemId(1)
                .currentBid(100)
                .reservePrice(2000)
                .build();
        AuctionItem expectedAuctionItem = AuctionItem.builder()
                .auctionItemId(1)
                .currentBid(2300.00)
                .bidderName("fake name")
                .reservePrice(2000)
                .build();
        when(auctionItemRepository.findById(1)).thenReturn(Optional.ofNullable(auctionItem));
        when(auctionItemRepository.save(auctionItem)).thenReturn(auctionItem);

        auctionItemService.placeBid(bid);

        ArgumentCaptor<AuctionItem> captor = ArgumentCaptor.forClass(AuctionItem.class);
        verify(auctionItemRepository).save(captor.capture());
        AuctionItem actualArgument = captor.getValue();

        assertEquals(expectedAuctionItem, actualArgument);
    }

    @Test(expected = ValidationException.class)
    public void test_placeBid_throwsException_forReservePrice() {

        Bid bid = Bid.builder()
                .auctionItemId(1)
                .bidderName("fake name")
                .maxAutoBidAmount(1500.00)
                .build();
        AuctionItem auctionItem = AuctionItem.builder()
                .auctionItemId(1)
                .currentBid(100)
                .reservePrice(2000)
                .build();

        when(auctionItemRepository.findById(1)).thenReturn(Optional.ofNullable(auctionItem));
        when(auctionItemRepository.save(auctionItem)).thenReturn(auctionItem);

        auctionItemService.placeBid(bid);
    }

    @Test(expected = ValidationException.class)
    public void test_placeBid_throwsException_forLessThanCurrent() {

        Bid bid = Bid.builder()
                .auctionItemId(1)
                .bidderName("fake name")
                .maxAutoBidAmount(50)
                .build();
        AuctionItem auctionItem = AuctionItem.builder()
                .auctionItemId(1)
                .currentBid(100)
                .reservePrice(2000)
                .build();

        when(auctionItemRepository.findById(1)).thenReturn(Optional.ofNullable(auctionItem));
        when(auctionItemRepository.save(auctionItem)).thenReturn(auctionItem);

        auctionItemService.placeBid(bid);
    }
}