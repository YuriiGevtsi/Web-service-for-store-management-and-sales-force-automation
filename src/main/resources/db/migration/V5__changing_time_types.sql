alter table action drop column date_finish;

alter table action alter column date_start type timestamp using date_start::timestamp;

alter table action add date_finish timestamp;

alter table financial_operations drop column time;

alter table financial_operations add time timestamp;

alter table delivery alter column date type timestamp using date::timestamp;

alter table returned_product alter column date type timestamp using date::timestamp;

alter table selling_operation alter column date type timestamp using date::timestamp;

alter table shift alter column beginning_time type timestamp using beginning_time::timestamp;

alter table shift alter column ending_time type timestamp using ending_time::timestamp;

alter table shift_worker alter column login_time type timestamp using login_time::timestamp;

alter table shift_worker alter column logout_time type timestamp using logout_time::timestamp;
