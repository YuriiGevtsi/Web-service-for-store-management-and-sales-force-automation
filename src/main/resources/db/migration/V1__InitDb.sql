create sequence hibernate_sequence start 1 increment 1;

create table action (
                        id_action int8 not null,
                        date_finish int4,
                        date_start date,
                        provider_product int8,
                        primary key (id_action)
);

create table barcode (
                         id_barcode int8 not null,
                         code varchar(2048),
                         provider_product int8,
                         primary key (id_barcode)
);

create table bucket (
                        id_bucket int8 not null,
                        count float8,
                        provider_product int8,
                        selling_operation int8,
                        primary key (id_bucket)
);

create table client (
                        id_client int8 not null,
                        name varchar(2048),
                        surname varchar(2048),
                        primary key (id_client)
);

create table client_card (
                             id_card varchar(2048) not null,
                             client int8,
                             primary key (id_card)
);

create table contract (
                          id_contract int8 not null,
                          date_start date,
                          position int8 not null,
                          worker int8 not null,
                          primary key (id_contract)
);

create table delivery (
                          id_delivery int8 not null,
                          comment varchar(2048),
                          date date,
                          status varchar(2048),
                          vendor int8,
                          primary key (id_delivery)
);

create table delivery_basket (
                                 id_delivery_basket int8 not null,
                                 amount int8,
                                 measuring_rate varchar(2048),
                                 price int8,
                                 delivery int8,
                                 provider_product int8,
                                 primary key (id_delivery_basket));

create table delivery_connect_order (
                                        id_delivery int8 not null,
                                        id_order int8 not null,
                                        primary key (id_delivery, id_order)
);

create table financial_operations (
                                      id_financial_operations int8 not null,
                                      comment varchar(2048),
                                      summ numeric(19, 2),
                                      time date,
                                      type varchar(2048),
                                      shift int4,
                                      primary key (id_financial_operations));

create table manufacturer (
                              id_manufacturer int8 not null,
                              name varchar(2048),
                              primary key (id_manufacturer)
);

create table measuring_rate_connect_provider_product (
                                                         id_measuring_rate_connect_provider_product int8 not null,
                                                         coefficient numeric(19, 2),
                                                         name varchar(2048),
                                                         provider_product int8,
                                                         measuring_rate int8,
                                                         primary key (id_measuring_rate_connect_provider_product));

create table order_bucket (
                              id_order_bucket int8 not null,
                              amount float8,
                              currency varchar(2048),
                              measuring_rate varchar(2048),
                              price_per_unit float8,
                              orders int8,
                              provider_product int8,
                              primary key (id_order_bucket)
);

create table order_payments (
                                id_order_payments int8 not null,
                                currency varchar(2048),
                                percent float8 not null,
                                sum float8 not null,
                                type varchar(2048),
                                orders int8,
                                primary key (id_order_payments)
);

create table orders (
                        id_order int8 not null,
                        amount_of_delivery varchar(2048),
                        date_of_create date,
                        date_of_wishing_delivery date,
                        description varchar(2048),
                        place varchar(2048),
                        price float4,
                        status varchar(2048),
                        provider int8,
                        primary key (id_order)
);

create table position (
                          id_position int8 not null,
                          description varchar(2048),
                          name varchar(2048),
                          primary key (id_position)
);

create table price (
                       id_price int8 not null,
                       date_finish date,
                       date_start date,
                       price float8 not null,
                       provider_product int8,
                       primary key (id_price)
);

create table product_category (
                                  id_product_category int4 not null,
                                  name varchar(2048),
                                  primary key (id_product_category)
);

create table product_connect_category (
                                          id_product_category int4 not null,
                                          product_category int4,
                                          provider_product int8,
                                          primary key (id_product_category)
);

create table provider (
                          id_provider int8 not null,
                          address varchar(2048),
                          e_mail varchar(2048),
                          name varchar(2048),
                          primary key (id_provider)
);

create table provider_connect_product (
                                          id_provider_connect_product int8 not null,
                                          provider int8,
                                          provider_product int8,
                                          primary key (id_provider_connect_product)
);

create table provider_price (
                                id_provider_price int8 not null,
                                currency varchar(2048),
                                date date,
                                price float8 not null,
                                provider_product int8,
                                primary key (id_provider_price)
);


create table provider_product (
                                  id_provider_product int8 not null,
                                  description varchar(2048),
                                  name varchar(2048),
                                  photo varchar(2048),
                                  vat float8,
                                  manufacturer int8,
                                  base_measuring_rate int8,
                                  primary key (id_provider_product));

create table provider_product_measuring_rate (
                                                 id_provider_product_measuring_rate int8 not null,
                                                 name varchar(2048),
                                                 primary key (id_provider_product_measuring_rate));

create table returned_product (
                                  id_returned_product int8 not null,
                                  amount float8 not null,
                                  date date,
                                  reason varchar(2048),
                                  id_product int8,
                                  primary key (id_returned_product));

create table role (
                      id_worker int8 not null,
                      roles varchar(2048));

create table selling_operation (
                                   id_selling int8 not null,
                                   date date,
                                   status varchar(2048),
                                   summ float8 not null,
                                   client_card varchar(2048),
                                   shift int4,
                                   primary key (id_selling));

create table shift (
                       id_shift int4 not null,
                       beginning_time date,
                       ending_time date,
                       primary key (id_shift));

create table shift_worker (
                              id_shift_worker int4 not null,
                              login_time date,
                              logout_time date,
                              shift int4,
                              worker int8,
                              primary key (id_shift_worker));

create table worker (
                        id_worker int8 not null,
                        date_of_birthday date,
                        hire_date date,
                        name varchar(2048),
                        surname varchar(2048),
                        primary key (id_worker));

create table worker_password (
                                 id_password int4 not null,
                                 password varchar(2048),
                                 worker int8,
                                 primary key (id_password));

create table written_off_product (
                                     id_written_off_product int4 not null,
                                     amount float8 not null,
                                     date date,
                                     reason varchar(2048),
                                     provider_product int8 not null,
                                     primary key (id_written_off_product));

alter table if exists action
    add constraint FKdee4j9abn6nvs69ndqu6512yv
        foreign key (provider_product) references provider_product;

alter table if exists barcode
    add constraint FKm9c97xh4gahkdnd9y89rxrf9l
        foreign key (provider_product) references provider_product;

alter table if exists bucket
    add constraint FKn6mtskeuqobyxg4q4ofgrc3jr
        foreign key (provider_product) references provider_product;

alter table if exists bucket
    add constraint FK7sidullfwld8m3p1yqfgllqyl
        foreign key (selling_operation) references selling_operation;

alter table if exists client_card
    add constraint FK5qer7hx192sucik6ikg4ts6t
        foreign key (client) references client;

alter table if exists contract
    add constraint FKea9g5lft48l033bpcqlo6bgcl
        foreign key (position) references position;
alter table if exists contract
    add constraint FK9rx2og2ctiyvp3ti27x25m2m3
        foreign key (worker) references worker;

alter table if exists delivery
    add constraint FKey20c2n9r2khdnf331vg0tr0q
        foreign key (vendor) references provider;

alter table if exists delivery_basket
    add constraint FK5mw6j025m675u0liag07vkxgv
        foreign key (delivery) references delivery;

alter table if exists delivery_basket
    add constraint FKfsl3canjqp2en3d17lc9ulnhh
        foreign key (provider_product) references provider_product;

alter table if exists delivery_connect_order
    add constraint FKl46hy6om9sdkm3w7honnghlab
        foreign key (id_order) references orders;

alter table if exists delivery_connect_order
    add constraint FKa1diapk1yxgd886h4a6oub12b
        foreign key (id_delivery) references delivery;

alter table if exists financial_operations
    add constraint FKmc6bfmgq90x7k4xy9qu4s529i
        foreign key (shift) references shift;

alter table if exists measuring_rate_connect_provider_product
    add constraint FK8poxpuqu9glc35ryna6khotfw
        foreign key (provider_product) references provider_product;

alter table if exists measuring_rate_connect_provider_product
    add constraint FK9hixyasocsf3ledibdepggkif
        foreign key (measuring_rate) references provider_product_measuring_rate;

alter table if exists order_bucket
    add constraint FK5dhq4gtc20ow7rj3ti92oo5y1
        foreign key (orders) references orders;

alter table if exists order_bucket
    add constraint FKd0brbvmva3u5u5vp0uwipkmjx
        foreign key (provider_product) references provider_product;

alter table if exists order_payments
    add constraint FKsse9bd6el1uo98t72kcnmoel5
        foreign key (orders) references orders;

alter table if exists orders
    add constraint FKj3lute2lkj9yeodikc0tnm3t6
        foreign key (provider) references provider;

alter table if exists price
    add constraint FKde3j2905nuxixffvqm0r0cyi6
        foreign key (provider_product) references provider_product;

alter table if exists product_connect_category
    add constraint FKjsrg7lg7hrhlk5ver5uhbo469
        foreign key (product_category) references product_category;

alter table if exists product_connect_category
    add constraint FK1m93oa1o21vf58478uokq8236
        foreign key (provider_product) references provider_product;

alter table if exists provider_connect_product
    add constraint FKt2qyx7blvdw4yksbe4i1m82xc
        foreign key (provider) references provider;

alter table if exists provider_connect_product
    add constraint FKtpl0wb16pv5tg63b71mcvo2e0
        foreign key (provider_product) references provider_product;

alter table if exists provider_price
    add constraint FK8tfxbd6ix26fdp63wsvqj5xqe
        foreign key (provider_product) references provider_product;

alter table if exists provider_product
    add constraint FKmh3aoab35wkmrt78fhhrsw3id
        foreign key (manufacturer) references manufacturer;

alter table if exists provider_product
    add constraint FKwpnfaxplp72155m01xlojxt1
        foreign key (base_measuring_rate) references provider_product_measuring_rate;

alter table if exists returned_product
    add constraint FK2jpj3fyg011sow40iw7ja45js
        foreign key (id_product) references provider_product;

alter table if exists role
    add constraint FK69jyd0qo8ukfe3x1k1gtqd9bo
        foreign key (id_worker) references worker;

alter table if exists selling_operation
    add constraint FKovkn1fi1fm7gvndri5ywcp8rd
        foreign key (client_card) references client_card;

alter table if exists selling_operation
    add constraint FK8ftvlguyj0mycymc9r6f1707j
        foreign key (shift) references shift;

alter table if exists shift_worker
    add constraint FKdvgo9qgoa7byjg4mnf28k2w7t
        foreign key (shift) references shift;

alter table if exists shift_worker
    add constraint FK9jsfl0lvbm38ud9xwh300n04s
        foreign key (worker) references worker;

alter table if exists worker_password
    add constraint FKnvjulsx78x4oreux8g1yb3n9i
        foreign key (worker) references worker;

alter table if exists written_off_product
    add constraint FKl48c4hphbsdf7vg2cfl65jjil
        foreign key (provider_product) references provider_product;


