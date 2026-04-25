create table transactions(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    step INT NOT NULL,
    type ENUM('CASH-IN', 'CASH-OUT', 'DEBIT', 'PAYMENT', 'TRANSFER'),
    amount DECIMAL(20,2) NOT NULL,
    name_origin varchar(100) NOT NULL,
    old_balance_origin DECIMAL(20,2) NOT NULL,
    new_balance_origin DECIMAL(20,2) NOT NULL,
    name_destiny varchar(100) NOT NULL,
    old_balance_destiny DECIMAL(20,2) NOT NULL,
    new_balance_destiny DECIMAL(20,2) NOT NULL,
    is_fraud BIT DEFAULT 0,
    is_flagged_fraud Bit DEFAULT 0
)