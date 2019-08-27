create sequence action_id_action_seq;

alter table action alter column id_action set default nextval('public.action_id_action_seq');

alter sequence action_id_action_seq owned by action.id_action;

create sequence barcode_id_barcode_seq;

alter table barcode alter column id_barcode set default nextval('public.barcode_id_barcode_seq');

alter sequence barcode_id_barcode_seq owned by barcode.id_barcode;

create sequence client_id_client_seq;

alter table client alter column id_client set default nextval('public.client_id_client_seq');

alter sequence client_id_client_seq owned by client.id_client;

create sequence client_card_id_card_seq;

alter table client_card alter column id_card set default nextval('public.client_card_id_card_seq');

alter sequence client_card_id_card_seq owned by client_card.id_card;

create sequence contract_id_contract_seq;

alter table contract alter column id_contract set default nextval('public.contract_id_contract_seq');

alter sequence contract_id_contract_seq owned by contract.id_contract;

create sequence delivery_id_delivery_seq;

alter table delivery alter column id_delivery set default nextval('public.delivery_id_delivery_seq');

alter sequence delivery_id_delivery_seq owned by delivery.id_delivery;

create sequence delivery_basket_id_delivery_basket_seq;

alter table delivery_basket alter column id_delivery_basket set default nextval('public.delivery_basket_id_delivery_basket_seq');

alter sequence delivery_basket_id_delivery_basket_seq owned by delivery_basket.id_delivery_basket;

create sequence financial_operations_id_financial_operations_seq;

alter table financial_operations alter column id_financial_operations set default nextval('public.financial_operations_id_financial_operations_seq');

alter sequence financial_operations_id_financial_operations_seq owned by financial_operations.id_financial_operations;

create sequence manufacturer_id_manufacturer_seq;

alter table manufacturer alter column id_manufacturer set default nextval('public.manufacturer_id_manufacturer_seq');

alter sequence manufacturer_id_manufacturer_seq owned by manufacturer.id_manufacturer;

create sequence measuring_rate_connect_provider_product_id_measuring_rate_connect_provider_product_seq;

alter table measuring_rate_connect_provider_product alter column id_measuring_rate_connect_provider_product set default nextval('public.measuring_rate_connect_provider_product_id_measuring_rate_connect_provider_product_seq');

alter sequence measuring_rate_connect_provider_product_id_measuring_rate_connect_provider_product_seq owned by measuring_rate_connect_provider_product.id_measuring_rate_connect_provider_product;

create sequence order_bucket_id_order_bucket_seq;

alter table order_bucket alter column id_order_bucket set default nextval('public.order_bucket_id_order_bucket_seq');

alter sequence order_bucket_id_order_bucket_seq owned by order_bucket.id_order_bucket;

create sequence order_payments_id_order_payments_seq;

alter table order_payments alter column id_order_payments set default nextval('public.order_payments_id_order_payments_seq');

alter sequence order_payments_id_order_payments_seq owned by order_payments.id_order_payments;

create sequence orders_id_order_seq;

alter table orders alter column id_order set default nextval('public.orders_id_order_seq');

alter sequence orders_id_order_seq owned by orders.id_order;

create sequence position_id_position_seq;

alter table position alter column id_position set default nextval('public.position_id_position_seq');

alter sequence position_id_position_seq owned by position.id_position;

create sequence price_id_price_seq;

alter table price alter column id_price set default nextval('public.price_id_price_seq');

alter sequence price_id_price_seq owned by price.id_price;

create sequence product_category_id_product_category_seq;

alter table product_category alter column id_product_category set default nextval('public.product_category_id_product_category_seq');

alter sequence product_category_id_product_category_seq owned by product_category.id_product_category;

create sequence product_connect_category_id_product_category_seq;

alter table product_connect_category alter column id_product_category set default nextval('public.product_connect_category_id_product_category_seq');

alter sequence product_connect_category_id_product_category_seq owned by product_connect_category.id_product_category;

create sequence provider_id_provider_seq;

alter table provider alter column id_provider set default nextval('public.provider_id_provider_seq');

alter sequence provider_id_provider_seq owned by provider.id_provider;

create sequence provider_connect_product_id_provider_connect_product_seq;

alter table provider_connect_product alter column id_provider_connect_product set default nextval('public.provider_connect_product_id_provider_connect_product_seq');

alter sequence provider_connect_product_id_provider_connect_product_seq owned by provider_connect_product.id_provider_connect_product;

create sequence provider_price_id_provider_price_seq;

alter table provider_price alter column id_provider_price set default nextval('public.provider_price_id_provider_price_seq');

alter sequence provider_price_id_provider_price_seq owned by provider_price.id_provider_price;

create sequence provider_product_id_provider_product_seq;

alter table provider_product alter column id_provider_product set default nextval('public.provider_product_id_provider_product_seq');

alter sequence provider_product_id_provider_product_seq owned by provider_product.id_provider_product;

create sequence provider_product_measuring_rate_id_provider_product_measuring_rate_seq;

alter table provider_product_measuring_rate alter column id_provider_product_measuring_rate set default nextval('public.provider_product_measuring_rate_id_provider_product_measuring_rate_seq');

alter sequence provider_product_measuring_rate_id_provider_product_measuring_rate_seq owned by provider_product_measuring_rate.id_provider_product_measuring_rate;

create sequence returned_product_id_returned_product_seq;

alter table returned_product alter column id_returned_product set default nextval('public.returned_product_id_returned_product_seq');

alter sequence returned_product_id_returned_product_seq owned by returned_product.id_returned_product;

create sequence worker_id_worker_seq;

alter table worker alter column id_worker set default nextval('public.worker_id_worker_seq');

alter sequence worker_id_worker_seq owned by worker.id_worker;

create sequence worker_password_id_password_seq;

alter table worker_password alter column id_password set default nextval('public.worker_password_id_password_seq');

alter sequence worker_password_id_password_seq owned by worker_password.id_password;

create sequence written_off_product_id_written_off_product_seq;

alter table written_off_product alter column id_written_off_product set default nextval('public.written_off_product_id_written_off_product_seq');

alter sequence written_off_product_id_written_off_product_seq owned by written_off_product.id_written_off_product;
