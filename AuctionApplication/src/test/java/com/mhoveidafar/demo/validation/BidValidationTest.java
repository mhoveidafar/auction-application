package com.mhoveidafar.demo.validation;

import com.mhoveidafar.demo.domain.Bid;
import com.mhoveidafar.demo.exception.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.mhoveidafar.demo.validation.BidValidation.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidValidationTest {
    @Autowired
    BidValidation bidValidation;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_validateAuctionItemId() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(AUCTION_ITEM_ID_CANNOT_BE_BLANK);

        Bid bid = Bid.builder()
                .maxAutoBidAmount(200)
                .bidderName("fake bidder")
                .build();

        bidValidation.validate(bid);
    }

    @Test
    public void test_validateMaxAutoBidAmount() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(BID_AMOUNT_CANNOT_BE_BLANK);

        Bid bid = Bid.builder()
                .auctionItemId(1)
                .bidderName("fake bidder")
                .build();

        bidValidation.validate(bid);
    }

    @Test
    public void test_validateBidderName() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(BIDDER_NAME_CANNOT_BE_BLANK);

        Bid bid = Bid.builder()
                .auctionItemId(1)
                .maxAutoBidAmount(200)
                .build();

        bidValidation.validate(bid);
    }
}
