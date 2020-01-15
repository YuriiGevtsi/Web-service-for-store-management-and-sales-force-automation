create table shift_schedule
(
    id_shift_schedule bigserial not null,
    worker bigint
        constraint shift_schedule_worker_id_worker_fk
            references worker,
    date timestamp,
    shift_time varchar(32)
);

create unique index shift_schedule_id_shift_schedule_uindex
    on shift_schedule (id_shift_schedule);

alter table shift_schedule
    add constraint shift_schedule_pk
        primary key (id_shift_schedule);

create sequence shift_schedule_id_shift_schedule_seq2;

alter table shift_schedule alter column id_shift_schedule set default nextval('public.shift_schedule_id_shift_schedule_seq2');

alter sequence shift_schedule_id_shift_schedule_seq2 owned by shift_schedule.id_shift_schedule;