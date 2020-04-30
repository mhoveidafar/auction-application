import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuctionItem } from './models/AuctionItem';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Item } from './models/Item';
import { Bid } from './models/Bid';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    auctionItemForm: FormGroup;
    bidForm: FormGroup;
    auctionItemList: AuctionItem[]
    title = 'AuctionApplication-UI';

    constructor(private httpClient: HttpClient, private formBuilder: FormBuilder) { };

    ngOnInit() {
        this.auctionItemForm = this.formBuilder.group({
            reservePrice: [''],
            itemId: [''],
            itemDescription: ['']
        });

        this.bidForm = this.formBuilder.group({
            auctionItemId: [''],
            maxAutoBidAmount: [''],
            bidderName: ['']
        });

        this.getAuctionItems().subscribe
            (
                (response) => {

                    this.auctionItemList = response;
                },
                (error) => console.log(error)
            );
    }
    getAuctionItems() {

        return this.httpClient.get<AuctionItem[]>("http://localhost:8080/auctionItems")
    }

    saveAuctionItems(auctionItem: AuctionItem) {

        return this.httpClient.post<AuctionItem[]>("http://localhost:8080/auctionItems", auctionItem)
    }

    placeBid(bid: Bid) {

        return this.httpClient.post<AuctionItem[]>("http://localhost:8080/bids", bid)
    }

    get fitem() { return this.auctionItemForm.controls; }

    get fbid() { return this.bidForm.controls; }

    onAuctionItemSubmission() {
        const reservePrice = this.fitem.reservePrice.value;
        const itemId = this.fitem.itemId.value;
        const itemDescription = this.fitem.itemDescription.value;
        const item = new Item(itemId, itemDescription);
        const auctionItem = new AuctionItem(reservePrice, item);

        this.auctionItemForm.reset()

        this.saveAuctionItems(auctionItem).subscribe(response => {
            this.getAuctionItems().subscribe(data => this.auctionItemList = data)
        }, err => {
            alert(err.error);
        }

        )
    }

    onBidSubmission() {
        let currentBidderName;
        let payable;
        const auctionItemId = this.fbid.auctionItemId.value;
        const maxAutoBidAmount = this.fbid.maxAutoBidAmount.value;
        const bidderName = this.fbid.bidderName.value;
        const bid = new Bid(auctionItemId, maxAutoBidAmount, bidderName);

        const currentItem: AuctionItem[] = this.auctionItemList.filter(item => item.auctionItemId == auctionItemId)
        if (currentItem.length > 0) {
            const currenBidAmount = currentItem[0].currentBid;
            currentBidderName = currentItem[0].bidderName;
            payable = currenBidAmount + 1;
        }
        this.bidForm.reset()

        this.placeBid(bid).subscribe(response => {
            this.getAuctionItems().subscribe(data => {
                if (currentBidderName != null) {
                    alert(currentBidderName + " has been outbidden, Now " + bidderName + " should pay " + payable)
                }
                this.auctionItemList = data
            })
        }, err => {
            this.getAuctionItems().subscribe(data => this.auctionItemList = data)
            alert(err.error);
        }
        )

    }

}
