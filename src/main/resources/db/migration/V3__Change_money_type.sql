alter table financial_operations alter column summ type float using summ::float;

alter table financial_operations alter column comment type varchar(2048) using comment::varchar(2048);