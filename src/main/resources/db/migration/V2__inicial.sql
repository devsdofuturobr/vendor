
CREATE TABLE products (
    prod_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vend_id INT NOT NULL,
    prod_name VARCHAR(100) NOT NULL,
    prod_price DECIMAL(10, 2) NOT NULL,
    prod_desc TEXT,
    FOREIGN KEY (vend_id) REFERENCES vendors(vend_id)
);