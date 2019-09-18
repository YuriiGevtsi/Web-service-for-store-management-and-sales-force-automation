alter table financial_operations alter column shift type bigint using shift::bigint;

alter table selling_operation alter column shift type bigint using shift::bigint;

alter table shift_worker alter column shift type bigint using shift::bigint;

alter table product_connect_category alter column product_category type bigint using product_category::bigint;