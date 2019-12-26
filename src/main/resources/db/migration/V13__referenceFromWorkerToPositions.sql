alter table worker add position bigint ;

alter table worker add constraint worker_position_id_position_fk foreign key (position) references position;
