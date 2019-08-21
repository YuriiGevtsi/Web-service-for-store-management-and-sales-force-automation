alter table delivery_basket alter column amount type double precision using amount::double precision;

alter table delivery_basket alter column price type double precision using price::double precision;