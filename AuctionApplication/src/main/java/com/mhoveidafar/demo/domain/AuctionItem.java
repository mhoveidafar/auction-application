package com.mhoveidafar.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionItemId;

    @Column(name="reserve_price", columnDefinition="Decimal(10,2)")
    private double reservePrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId")
    private Item item;

    @Column(name="current_bid", columnDefinition="Decimal(10,2)")
    private double currentBid;

    private String bidderName;
}