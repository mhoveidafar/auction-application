package com.ally.demo.validation;

import com.ally.demo.domain.AuctionItem;
import com.ally.demo.domain.Item;
import com.ally.demo.exception.ValidationException;
import com.ally.demo.repository.ItemRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.ally.demo.validation.AuctionItemValidation.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuctionItemValidationTest {
    @Autowired
    AuctionItemValidation auctionItemValidation;

    @MockBean
    ItemRepository itemRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_validateReservePrice(){
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(RESERVE_PRICE_CANNOT_BE_BLANK);

        AuctionItem item = AuctionItem.builder()
                .item(Item.builder()
                        .itemId("abc")
                        .description("test")
                        .build())
                .build();

        auctionItemValidation.validate(item);
    }

    @Test
    public void test_validateItemId_withoutItemId(){
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(ITEM_ID_CANNOT_BE_EMPTY);

        AuctionItem item = AuctionItem.builder()
                .reservePrice(200)
                .item(Item.builder()
                        .description("test")
                        .build())
                .build();

        auctionItemValidation.validate(item);
    }

    @Test
    public void test_validateItemId_withExistingId(){
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(ITEM_ALREADY_EXISTS);
        Item item = Item.builder()
                .itemId("ab")
                .description("test")
                .build();
        AuctionItem auctionItem = AuctionItem.builder()
                .reservePrice(200)
                .item(item)
                .build();
        when(itemRepository.findById("ab")).thenReturn(Optional.ofNullable(item));

        auctionItemValidation.validate(auctionItem);
    }

}