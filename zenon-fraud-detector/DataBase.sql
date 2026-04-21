create table Transactions(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    step int NOT NULL,
    type ENUM('CASH-IN', 'CASH-OUT', 'DEBIT', 'PAYMENT', 'TRANSFER'),
    amount DECIMAL(9,2) NOT NULL,
    nameOrig varchar(100) NOT NULL,
    oldBalanceOrig DECIMAL(9,2) NOT NULL,
    newBalanceOrig DECIMAL(9,2) NOT NULL,
    nameDest varchar(100) NOT NULL,
    oldBalanceDest DECIMAL(9,2) NOT NULL,
    newBalanceDest DECIMAL(9,2) NOT NULL,
    isFraud BIT NOT NULL,
    isFlaggedFraud Bit NOT NULL
)