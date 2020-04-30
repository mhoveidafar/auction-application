# auction-application

### PROJECT DESCRIPTION

#### How to test:
- Go to backend folder (AuctionApplication) and run the app as Spring Boot. Can use this command: `./gradlew bootRun`

- H2 in memory DB is used for this project. Can check the DB from: http://localhost:8080/h2

- Go to UI folder and start the angular application by running `ng serve` command. And navigate the application from http://localhost:4200/



POST /auctionItems
```
Example Request Body:
{
	“reservePrice”: 10450.00,
	“item”: {
		“itemId”: “abcd”,
		“description”: “item description”
	}
}

Example Response:
{
	"auctionItemId": 1234
}
```
GET /auctionItems
```
Example Response:
{
	[
{
	“auctionItemId”: 1234,
	“currentBid”: 0.00,
	“reservePrice”: 10450.00,
	“item”: {
		“itemId”: “abcd”,
		“description”: “item description”
	}
},
{
	“auctionItemId”: 1235,
	“currentBid”: 2950.00,
	“bidderName”: “ABC Dealership”
“reservePrice”: 2499.00,
	“item”: {
		“itemId”: “efgh”,
		“description”: “another item description”
	}
}
	]
}
```
GET /auctionItems/{auctionItemId}
```
Example Response:
{
	“auctionItemId”: 1234,
	“currentBid”: 0.00,
	“reservePrice”: 10450.00,
	“item”: {
		“itemId”: “abcd”,
		“description”: “item description”
	}
}
```
POST /bids
```
Example Request Body:
{
	“auctionItemId”: 1234,
	“maxAutoBidAmount”: 9500.00,
	“bidderName”: “ABC Dealership”
}
```

Bid Rules:

-	If the reserve price has not been met, current bid should be set to the maximum of the current bid and the max auto-bid amount – an exception should be returned letting the bidder know they have not met the reserve.

-	Once the reserve price has been met, max auto-bid amount becomes the max amount bidder is willing to pay but not necessarily the amount they must pay. As new bids are submitted for an item, bidder with the highest max auto-bid amount must only pay $1.00 more than the next highest bidder. It is important to remember the bid leaders max auto-bid amount in case future bids are submitted for the item. Any time a bidder has been outbid, an event/exception should be broadcast notifying them that they’ve been outbid.

-	Make sure you keep an audit log of all bidding

