-- USUNIĘCIE TABEL (jeśli istnieją) W ODPOWIEDNIEJ KOLEJNOŚCI

DROP TABLE IF EXISTS items_in_receipt;
DROP TABLE IF EXISTS receipt;
DROP TABLE IF EXISTS cost;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS quantity;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS person;

-- TWORZENIE TABEL

CREATE TABLE person
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100),
    joined_at  TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE category
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    last_updated_at TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE quantity
(
    id    SERIAL PRIMARY KEY,
    value VARCHAR(50) NOT NULL
);

CREATE TABLE item
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    category_id INT          NOT NULL,
    quantity_id INT          NULL,
    CONSTRAINT fk_item_category
        FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT fk_item_quantity
        FOREIGN KEY (quantity_id) REFERENCES quantity (id)
);

CREATE TABLE cost
(
    id             SERIAL PRIMARY KEY,
    price          NUMERIC(10, 2) NOT NULL,
    effective_date DATE           NOT NULL,
    item_id        INT            NOT NULL,
    CONSTRAINT fk_cost_item
        FOREIGN KEY (item_id) REFERENCES item (id)
);

CREATE TABLE receipt
(
    id           SERIAL PRIMARY KEY,
    person_id    INT            NOT NULL,
    purchased_at TIMESTAMP      NOT NULL,
    total_cost   NUMERIC(12, 2) NOT NULL,
    CONSTRAINT fk_receipt_person
        FOREIGN KEY (person_id) REFERENCES person (id)
);

CREATE TABLE items_in_receipt
(
    id         SERIAL PRIMARY KEY,
    item_id    INT NOT NULL,
    quantity   INT NOT NULL,
    receipt_id INT NOT NULL,
    CONSTRAINT fk_iir_item
        FOREIGN KEY (item_id) REFERENCES item (id),
    CONSTRAINT fk_iir_receipt
        FOREIGN KEY (receipt_id) REFERENCES receipt (id)
);

-- DANE STARTOWE

INSERT INTO person (id, first_name, last_name, joined_at)
VALUES (3, 'bartosz', 'andrzejewski', '2004-05-04 12:00:00');

INSERT INTO category (id, name, created_at, last_updated_at)
VALUES (1, 'food', '2004-05-04 12:00:00', '2004-05-04 13:20:00');

INSERT INTO quantity (id, value)
VALUES (1, '50');

INSERT INTO item (id, name, category_id, quantity_id)
VALUES (1, 'apple', 1, 1);

INSERT INTO cost (id, price, effective_date, item_id)
VALUES (1, 2.50, '2004-05-04', 1);

INSERT INTO receipt (id, person_id, purchased_at, total_cost)
VALUES (1, 1, '2004-05-04 14:00:00', 50.00);

INSERT INTO items_in_receipt (id, item_id, quantity, receipt_id)
VALUES (1, 1, 1, 1);

-- USTAWIENIE SEKWENCJI NA MAKSYMALNE ISTNIEJĄCE ID

SELECT setval('person_id_seq',      (SELECT COALESCE(MAX(id), 1) FROM person));
SELECT setval('category_id_seq',    (SELECT COALESCE(MAX(id), 1) FROM category));
SELECT setval('quantity_id_seq',    (SELECT COALESCE(MAX(id), 1) FROM quantity));
SELECT setval('item_id_seq',        (SELECT COALESCE(MAX(id), 1) FROM item));
SELECT setval('cost_id_seq',        (SELECT COALESCE(MAX(id), 1) FROM cost));
SELECT setval('receipt_id_seq',     (SELECT COALESCE(MAX(id), 1) FROM receipt));
SELECT setval('items_in_receipt_id_seq', (SELECT COALESCE(MAX(id), 1) FROM items_in_receipt));