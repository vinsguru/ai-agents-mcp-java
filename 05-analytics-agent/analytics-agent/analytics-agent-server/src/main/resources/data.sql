CREATE TABLE orders (
    order_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50),
    product_id VARCHAR(50),
    category VARCHAR(50),
    price DECIMAL(20, 2),
    discount DECIMAL(5, 2),
    quantity INT,
    payment_method VARCHAR(30),
    order_date DATE,
    delivery_time_days INT,
    region VARCHAR(50),
    returned BOOLEAN,
    total_amount DECIMAL(20, 2),
    shipping_cost DECIMAL(20, 2),
    profit_margin DECIMAL(10, 2),
    customer_age INT,
    customer_gender VARCHAR(10)
);

INSERT INTO orders SELECT * FROM CSVREAD('classpath:orders.csv');