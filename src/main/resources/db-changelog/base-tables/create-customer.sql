CREATE TABLE "customers" (
                           id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                           bank_account VARCHAR(10) CHECK (LENGTH(bank_account) = 10) UNIQUE
);