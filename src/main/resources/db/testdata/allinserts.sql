INSERT INTO `javaschool`.`client` (`name`, `surname`, `date_of_birth`, `email`, `password`)
VALUES
    ('Alberto', 'Martín', '1999-07-31', 'alberto@example.com', 'password123'),
    ('María', 'Jimenez', '1985-03-20', 'maria@example.com', 'password321'),
    ('Juan', 'García', '1995-07-10', 'pedro@example.com', 'password456');

INSERT INTO `javaschool`.`clients_address` (`country`, `city`, `postal_code`, `street`, `home`, `apartment`, `client_id`)
VALUES
	('Spain', 'Madrid', '28001', 'Calle de Serrano', 'apartment','45B', 1),
    ('United States', 'New York', '10001', 'Main Street', 'apartment','123A', 2),
    ('France', 'Paris', '75008', 'Champs-Élysées', 'house','578', 3);

INSERT INTO `javaschool`.`orders` (`payment_method`, `delivery_method`, `payment_status`, `order_status`, `client_id`, `clients_address_id`)
VALUES
	('Credit Card', 'Express Shipping', 'Paid', 'Processing', 1, 2);


INSERT INTO `javaschool`.`product` (`title`, `price`, `category`, `parameters`, `weight`, `volume`, `quantity_stock`)
VALUES
    ('MIDI Piano', 200, 'Musical Instruments', 'Type: MIDI, Color: Silver', 15, 0, 5),
    ('Modern Desk Lamp', 50, 'Home & Living', 'Power Source: Electric', 1, 0, 20),
    ('Rolex', 10000, 'Fashion', 'Material: Stainless Steel', 0, 0, 10);


INSERT INTO `javaschool`.`order_has_product` (`orders_id`, `product_id`, `quantity`)
VALUES
    (1, 1, 3);
