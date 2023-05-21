-- 为了避免外键约束违规（例如删除父表时子表仍然存在相关记录），
-- 日常测试、开发时，需要把表都先删除了(先删除Reference 的表，再删除其他的表)。

DELETE FROM Ingredient_Ref;
DELETE FROM Ingredient;

DELETE FROM Taco;
DELETE FROM Taco_Order;

CREATE TABLE IF NOT EXISTS Taco_Order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    delivery_name VARCHAR(50) NOT NULL,
    delivery_street VARCHAR(50) NOT NULL,
    delivery_city VARCHAR(50) NOT NULL,
    delivery_state VARCHAR(2) NOT NULL,
    delivery_zip VARCHAR(10) NOT NULL,
    cc_number VARCHAR(16) NOT NULL,
    cc_expiration VARCHAR(5) NOT NULL,
    cc_cvv VARCHAR(3) NOT NULL,
    placed_at TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS Taco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    taco_order INT NOT NULL,
    taco_order_key INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (taco_order) REFERENCES Taco_Order(id)
    );

CREATE TABLE IF NOT EXISTS Ingredient (
    id VARCHAR(4) NOT NULL,
    NAME VARCHAR(25) NOT NULL,
    TYPE VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)  -- Added primary key constraint
    );

CREATE TABLE IF NOT EXISTS Ingredient_Ref (
    ingredient VARCHAR(4) NOT NULL,
    taco INT NOT NULL,
    taco_key INT NOT NULL,
    FOREIGN KEY (ingredient) REFERENCES Ingredient(id),
    FOREIGN KEY (taco) REFERENCES Taco(id)  -- Added foreign key constraint
    );