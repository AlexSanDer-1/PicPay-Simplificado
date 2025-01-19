CREATE TABLE transction (
       id bigint generated IDENTITY,
       value decimal(15,2) not null,
       payer_wallet_id BIGINT,
       payee_wallet_id BIGINT,
       primary key (id)
);