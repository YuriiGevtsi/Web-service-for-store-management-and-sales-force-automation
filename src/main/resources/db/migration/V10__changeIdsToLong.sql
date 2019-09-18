alter table product_category alter column id_product_category type bigint using id_product_category::bigint;

alter table product_connect_category alter column id_product_category type bigint using id_product_category::bigint;

alter table shift alter column id_shift type bigint using id_shift::bigint;

alter table shift_worker alter column id_shift_worker type bigint using id_shift_worker::bigint;

alter table written_off_product alter column id_written_off_product type bigint using id_written_off_product::bigint;