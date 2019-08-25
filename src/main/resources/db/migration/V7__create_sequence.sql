create sequence shift_id_shift_seq;

alter table shift alter column id_shift set default nextval('public.shift_id_shift_seq');

alter sequence shift_id_shift_seq owned by shift.id_shift;

create sequence shift_worker_id_shift_worker_seq;

alter table shift_worker alter column id_shift_worker set default nextval('public.shift_worker_id_shift_worker_seq');

alter sequence shift_worker_id_shift_worker_seq owned by shift_worker.id_shift_worker;

create sequence selling_operation_id_selling_seq;

alter table selling_operation alter column id_selling set default nextval('public.selling_operation_id_selling_seq');

alter sequence selling_operation_id_selling_seq owned by selling_operation.id_selling;

create sequence bucket_id_bucket_seq;

alter table bucket alter column id_bucket set default nextval('public.bucket_id_bucket_seq');

alter sequence bucket_id_bucket_seq owned by bucket.id_bucket;