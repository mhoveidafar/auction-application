package com.mhoveidafar.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String itemId;

    private String description;

    @OneToOne(mappedBy = "item")
    @JsonIgnore
    private AuctionItem auctionItem;
}