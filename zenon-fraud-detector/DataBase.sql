create table Transactions(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    step INT NOT NULL,
    type ENUM('CASH-IN', 'CASH-OUT', 'DEBIT', 'PAYMENT', 'TRANSFER'),
    amount DECIMAL(20,2) NOT NULL,
    nameOrig varchar(100) NOT NULL,
    oldBalanceOrig DECIMAL(20,2) NOT NULL,
    newBalanceOrig DECIMAL(20,2) NOT NULL,
    nameDest varchar(100) NOT NULL,
    oldBalanceDest DECIMAL(20,2) NOT NULL,
    newBalanceDest DECIMAL(20,2) NOT NULL,
    isFraud BIT DEFAULT 0,
    isFlaggedFraud Bit DEFAULT 0
)