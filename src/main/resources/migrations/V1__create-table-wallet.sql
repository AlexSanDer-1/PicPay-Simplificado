CREATE TABLE wallet(
      id bigint generated IDENTITY,
      namefull varchar (255) not null,
      cpf varchar(255) not null unique,
      email varchar(255) not null unique,
      balance decimal(15,2) not null,
      typeUser varchar(255) not null,
      primary key (id)
);