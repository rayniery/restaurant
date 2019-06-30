insert into restaurant.menu_item(id, name, description, image, price, additional_details) values (1,'Hawaiian Pizza', 'All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg',300,'Italian,Ham,Pineapple');
insert into restaurant.menu_item(id, name, description, image, price, additional_details) values (2,'Chicken Tom Yum Pizza', 'Best marinated chicken with pineapple and mushroom on Spicy Lemon sauce. Enjoy our tasty Thai style pizza.','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu2.jpg',350,'Italian,Thai,Chicken,Mushroom,Hot');
insert into restaurant.menu_item(id, name, description, image, price, additional_details) values (3,'Xiaolongbao', 'Chinese steamed bun','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu3.jpg',200,'Chinese,Pork,Recommended');
insert into restaurant.menu_item(id, name, description, image, price, additional_details) values (4,'Kimchi', 'Traditional side dish made from salted and fermented vegetables','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu4.jpg',50,'Korean,Radish,Cabba');
insert into restaurant.menu_item(id, name, description, image, price, additional_details) values (5,'Oolong tea', 'Partially fermented tea grown in the Alishan area','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu5.jpg',30,'Hot, Non-alcohol');
insert into restaurant.menu_item(id, name, description, image, price, additional_details) values (6,'Beer', 'Fantastic flavors and authentic regional appeal beer','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu6.jpg',60,'Alcohol');

insert into restaurant.menu_bill(id) values (1);
insert into restaurant.menu_bill(id) values (2);
insert into restaurant.menu_bill(id) values (3);

insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 10:00:00', 'dd-MM-yyyy hh:mm:ss'), 1, 1, 300, 1);
insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 10:00:00', 'dd-MM-yyyy hh:mm:ss'), 2, 4, 100, 1);
insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 11:00:00', 'dd-MM-yyyy hh:mm:ss'), 1, 4, 50, 1);
insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 12:00:00', 'dd-MM-yyyy hh:mm:ss'), 1, 3, 200, 2);
insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 12:00:00', 'dd-MM-yyyy hh:mm:ss'), 1, 6, 60, 2);
insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 15:00:00', 'dd-MM-yyyy hh:mm:ss'), 1, 5, 30, 3);
insert into restaurant.menu_order(ordered_time, quantity, menu_item_id, total_price, bill_id) values(parsedatetime('01-01-2017 15:00:00', 'dd-MM-yyyy hh:mm:ss'), 3, 6, 180, 3);
