create table transactions(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    step INT NOT NULL,
    type ENUM('CASH-IN', 'CASH-OUT', 'DEBIT', 'PAYMENT', 'TRANSFER'),
    amount DECIMAL(20,2) NOT NULL,
    origin_id INT NOT NULL,
    destiny_id INT NOT NULL,
    is_fraud BIT DEFAULT 0,
    is_flagged_fraud Bit DEFAULT 0,

    CONSTRAINT fk_transactions_origin
    FOREIGN KEY (origin_id)
    REFERENCES customer_transactions(id)
    ON DELETE CASCADE

    CONSTRAINT fk_transactions_destiny
    FOREIGN KEY (destiny_id)
    REFERENCES customer_transactions(id)
    ON DELETE CASCADE
)

create table customer_transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    old_balance DECIMAL(20,2) NOT NULL,
    new_balance DECIMAL(20,2) NOT NULL
)