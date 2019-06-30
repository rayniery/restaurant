## Instructions
You are going to design and implement RESTful web service for a restaurant in order to support menu and bill order management.

## Running the code
The project is a regular Spring Boot app with H2 as an embedded database. The data is recreated in each startup. 

```
$> mvn clean install
$> mvn spring-boot:run
```

The server will start at http://localhost:8080. Some demo data is imported automatically, so you will be able to see some menu items, orders and bills.

## Design
The app has 3 main concepts:
- Menu item: things that you can order
- Menu order: a list of things that you ordered at a certain time.
- Menu bill: a collection of orders group together.

## Using the app
To see the a list of menu items:

Request:
GET http://localhost:8080/restaurant/menu/item

Response
```
{
"menuItems": [
	{
		"id": 1,
		"name": "Hawaiian Pizza",
		"description": "All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.",
		"image": "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
		"price": 300,
		"additionalDetails": "Italian,Ham,Pineapple"
	},
	{
		"id": 2,
		"name": "Chicken Tom Yum Pizza",
		"description": "Best marinated chicken with pineapple and mushroom on Spicy Lemon sauce. Enjoy our tasty Thai style pizza.",
		"image": "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu2.jpg",
		"price": 350,
		"additionalDetails": "Italian,Thai,Chicken,Mushroom,Hot"
	}
}
```

To see a single menu item:
GET http://localhost:8080/restaurant/menu/item/{itemId}

To create a new menu item:
POST http://localhost:8080/restaurant/menu/item/

Request body:
```
{
"menuItem": 
	{
		"name": "Hawaiian Pizza",
		"description": "la peor pizza",
		"image": "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
		"price": 300,
		"additionalDetails": "concha 'e tu madre"
	}
}
```

To update a menu item:
PUT http://localhost:8080/restaurant/menu/item/{itemId}

To see a list of orders:
http://localhost:8080/restaurant/menu/order

Response:
```
{
"orders": [
	{
		"id": 1,
		"menuItem": {
			"id": 1,
			"name": "Hawaiian Pizza",
			"description": "All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.",
			"image": "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
			"price": 300,
			"additionalDetails": "Italian,Ham,Pineapple"
		},
		"quantity": 1,
		"orderedTime": "2016-12-31T21:00:00Z",
		"totalPrice": 300
	},
	{
		"id": 2,
		"menuItem": {
			"id": 4,
			"name": "Kimchi",
			"description": "Traditional side dish made from salted and fermented vegetables",
			"image": "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu4.jpg",
			"price": 50,
			"additionalDetails": "Korean,Radish,Cabba"
		},
		"quantity": 2,
		"orderedTime": "2016-12-31T21:00:00Z",
		"totalPrice": 100
	}
}
```

To see a single order: GET http://localhost:8080/restaurant/menu/order/{orderId}

To create a new order: POST http://localhost:8080/restaurant/menu/item/{itemId}/order

Request body
```
{
"order": {
	"quantity": 5
	}
}
```

To see a list of all bills:
GET http://localhost:8080/restaurant/menu/bill/

To see a bill :
GET http://localhost:8080/restaurant/menu/bill/{billId}

To create a new order:
POST http://localhost:8080/restaurant/menu/bill/

Request boyd:
```
{
"bill": {
	"orders": [ {
		"id": 1
	},
	{
		"id": 3
	}
	]
}
}
```
The order ids should have been created before creating a new bill.

To update a bill (remove or add orders)
PUT http://localhost:8080/restaurant/menu/bill/{billId}
```
{
"bill": {
	"orders": [ {
		"id": 1
	}
}
}
```
If we assume that we use the same bill id as before, this last request would delete order 3 from the bill.

## Pagination and search
All endpoints for retrieving a list of available resources (items, orders and bills) support pagination. For instance: http://localhost:8080/restaurant/menu/bill?page=1&size=2 is asking for the second page (0-based index) of size 2.

In addition, http://localhost:8080/restaurant/menu/item supports querying by string. http://localhost:8080/restaurant/menu/item?search=pizza will return any item with pizz in the name, description or additional details.