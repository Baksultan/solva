-- Вставка двух кастомеров в таблицу "customers"
INSERT INTO "customers" (bank_account)
VALUES ('1234567890'),
       ('0987654321');

DO
$$
    DECLARE
        customer1_id BIGINT;
        customer2_id BIGINT;
    BEGIN
        SELECT id INTO customer1_id FROM "customers" WHERE bank_account = '1234567890';
        SELECT id INTO customer2_id FROM "customers" WHERE bank_account = '0987654321';

        INSERT INTO "customer_limit" (product_limit, service_limit, limit_date, customer_id)
        VALUES (1000.0, 500.0, NOW(), customer1_id),
               (2000.0, 1000.0, NOW(), customer2_id);
    END
$$;
