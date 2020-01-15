create table inventory
(
    id_inventory bigserial not null,
    product bigint
        constraint inventory_provider_product_id_provider_product_fk
            references provider_product,
    difference int,
    date timestamp
);

create unique index inventory_id_inventory_uindex
    on inventory (id_inventory);

alter table inventory
    add constraint inventory_pk
        primary key (id_inventory);

create sequence inventory_id_inventory_seq2;

alter table inventory alter column id_inventory set default nextval('public.inventory_id_inventory_seq2');

alter sequence inventory_id_inventory_seq2 owned by inventory.id_inventory;