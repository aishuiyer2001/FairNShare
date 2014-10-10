# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table person (
  email                     varchar(255) not null,
  fname                     varchar(255),
  lname                     varchar(255),
  score                     integer,
  dob                       varchar(255),
  ph_no                     varchar(255),
  gender                    varchar(255),
  password                  varchar(255),
  constraint pk_person primary key (email))
;

create sequence person_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists person;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists person_seq;

