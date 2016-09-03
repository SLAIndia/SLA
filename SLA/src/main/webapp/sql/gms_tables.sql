

Centos or any redhat version
----------------------------

yum install postgresql94-contrib.x86_64
connect to database then - \i /tmp/pgcrypto.sql

Ubuntu
-------

apt install postgresql94-contrib.x86_64
connect to database then - \i /tmp/pgcrypto.sql

windows
-------

create extension pgcrypto;




//SCHEMAS
========================


// SCHEMA USERMANAGEMENT
========================

create schema usermanagement;

//SCHEMA FARM
========================

create schema farm;

//SCHEMA MASTER
========================

create schema master;


// TABLES
========================
 
// ROLE


create table usermanagement.role(role_id serial primary key not null, role_name varchar(50) not null);
ALTER TABLE usermanagement.role ADD CONSTRAINT role_name_unq UNIQUE (role_name);

// user

create table usermanagement.user(user_id serial primary key not null, user_name varchar(50) not null, user_password varchar(100) not null, user_role_id integer not null, foreign key(user_role_id) references usermanagement.role(role_id), user_active boolean not null default true);

alter table usermanagement.user add column user_created_dt timestamp ;

alter table usermanagement.user add column user_updated_dt timestamp without time zone default now();

 alter table usermanagement.user alter column user_updated_dt set not null;
 alter table usermanagement.user alter column user_created_dt set not null;
 alter table usermanagement.user add column user_temp_password varchar(100);
 alter table usermanagement.user alter column user_password drop not null;
 alter table usermanagement.user add column user_fname varchar(100);
 alter table usermanagement.user add column user_lname varchar(100);
 alter table usermanagement.user add column user_location varchar(100);
 alter table usermanagement.user add column user_email varchar(100);
 alter table usermanagement.user add column user_address text;
 alter table usermanagement.user add column user_phone varchar(50);
 alter table usermanagement.user drop column user_active;



 ALTER TABLE usermanagement.user ADD CONSTRAINT user_name_unq UNIQUE (user_name);

// OWNER

create table usermanagement.owner (owner_id serial primary key not null, owner_fname varchar(50) not null, owner_lname varchar(50) not null, owner_user_id integer not null, foreign key(owner_user_id) references usermanagement.user(user_id), owner_address text);
alter table usermanagement.owner add column owner_status boolean default true;
ALTER TABLE usermanagement.owner add column owner_location varchar(75) not null;
 alter table usermanagement.owner add column owner_email varchar(75);
 alter table usermanagement.owner add column owner_phone varchar(75);
 alter table usermanagement.owner alter column owner_lname drop not null;
alter table usermanagement.owner drop column owner_fname;
 alter table usermanagement.owner drop column owner_lname;
alter table usermanagement.owner drop column owner_address;
alter table usermanagement.owner drop column owner_location;
 alter table usermanagement.owner drop column owner_email;
alter table usermanagement.owner drop column owner_phone;




//FARM

create table farm.farm(farm_id serial primary key not null, farm_name varchar(50) not null, farm_address text,farm_location varchar(100) not null, farm_status boolean not null default true);

alter table farm.farm add column farm_code varchar(100) not null;
ALTER TABLE farm.farm ADD CONSTRAINT farm_code_unq UNIQUE (farm_code);
alter table farm.farm add column farm_created_dt timestamp without time zone not null;


//FARM_OWNER_LINK

create table farm.farm_owner_link(farm_owner_link_id serial primary key not null, farm_owner_link_farm_id integer not null, farm_owner_link_owner_id integer not null, foreign key(farm_owner_link_farm_id) references farm.farm(farm_id), foreign key(farm_owner_link_owner_id) references usermanagement.owner(owner_id));

//VET

create table usermanagement.vet (vet_id serial primary key not null, vet_fname varchar(50) not null, vet_lname varchar(50) not null, vet_type integer not null ,vet_user_id integer not null,foreign key(vet_user_id) references usermanagement.user(user_id), vet_address text,vet_location varchar(50) not null);
alter table usermanagement.vet drop column vet_fname;
alter table usermanagement.vet drop column vet_lname;
alter table usermanagement.vet drop column vet_location;
 alter table usermanagement.vet drop column vet_address;
 alter table usermanagement.vet drop column vet_email;
 alter table usermanagement.vet drop column vet_phone;
 

//FARM_VET

create table farm.farm_vet_link(farm_vet_link_id serial primary key not null, farm_vet_link_farm_id integer not null, farm_vet_link_vet_id integer not null, foreign key(farm_vet_link_farm_id) references farm.farm(farm_id), foreign key(farm_vet_link_vet_id) references usermanagement.vet(vet_id));
alter table usermanagement.vet add column vet_email varchar(50) not null;
alter table usermanagement.vet add column vet_phone varchar(50) not null;
alter table usermanagement.vet add column vet_status integer ;
alter table usermanagement.vet alter column vet_status set not null;


// VACCINATION


create table master.vaccination(vaccination_id serial primary key not null, vaccination_name varchar(50) not null, vaccination_exp_dt timestamp);
alter table  master.vaccination add column vaccination_dose double precision;
alter table  master.vaccination add column vaccination_unit varchar(10);
alter table master.vaccination add column vaccination_details text;
 alter table master.vaccination add column vaccination_status integer default 1;

//SURGERY

create table master.surgery(surgery_id serial primary key not null, surgery_name varchar(50) not null);

//CATTLE

create table farm.cattle(cattle_id serial primary key not null, cattle_ear_tag_id varchar(50) unique not null,cattle_location varchar(100) not null, cattle_farm_id integer not null, cattle_dob timestamp not null, cattle_gender char(2), cattle_category  char(2));

  alter table farm.cattle add column cattle_status boolean not null default true;
  alter table farm.cattle add column cattle_buck_id integer;
  alter table farm.cattle add column cattle_doe_id integer;
  alter table farm.cattle add foreign key (cattle_buck_id) references farm.cattle(cattle_id);
  alter table farm.cattle add foreign key (cattle_doe_id) references farm.cattle(cattle_id);
  alter table farm.cattle add column cattle_created_dt timestamp without time zone not null;
  alter table farm.cattle add column cattle_latt varchar(100);
  alter table farm.cattle add column cattle_long varchar(100);
  alter table farm.cattle add column cattle_land_mark varchar(100);
  alter table farm.cattle alter column cattle_category set data type char(1);
  alter table farm.cattle alter column cattle_gender set data type char(1);
  alter table farm.cattle add column cattle_code varchar(100);
  
  
  
  //CATTLE_IMAGE

create table farm.cattle_image(cattle_image_id serial primary key not null, cattle_image_url text not null, cattle_image_is_primary boolean not null default false, cattle_image_cattle_id integer not null,foreign key(cattle_image_cattle_id) references farm.cattle(cattle_id) );

//BREEDING

 CREATE TABLE farm.breeding(breeding_id serial primary key not null, breeding_buck_id integer, breeding_doe_id integer not null, breeding_date timestamp not null, breeding_village varchar(100), breeding_abortion_status boolean default false, breeding_aborted_dt timestamp, foreign key(breeding_buck_id) references farm.cattle(cattle_id),foreign key(breeding_doe_id) references farm.cattle(cattle_id) );
 alter table farm.breeding add column breeding_kids_no integer;
 alter table farm.breeding add column breeding_aborted_no integer;
 alter table farm.breeding add column breeding_lact_start_dt timestamp without time zone;
 alter table farm.breeding add column breeding_lact_end_dt timestamp without time zone;

//BREED KIDS

 CREATE TABLE farm.breed_kids(breed_kids_id serial primary key not null, breed_kids_breeding_id integer not null,breed_kids_cattle_id integer not null, breed_kids_nur_lact_dt timestamp, breed_kids_weaning_dt timestamp, foreign key(breed_kids_breeding_id) references farm.breeding(breeding_id), foreign key(breed_kids_cattle_id) references farm.cattle(cattle_id) );
 alter table farm.breed_kids add column breed_kids_weight double precision;
 

//HEALTH CARE 

CREATE TABLE farm.healthcare(healthcare_id serial primary key not null, healthcare_cattle_id integer not null, foreign key(healthcare_cattle_id) references farm.cattle(cattle_id),healthcare_vet_id integer not null, foreign key(healthcare_vet_id) references usermanagement.vet(vet_id), healthcare_medication_given text, healthcare_vet_sign varchar(75));
alter table farm.healthcare add column healthcare_service_dt timestamp without time zone not null;

//VACCINE_HC_LINK

 CREATE TABLE farm.vacc_hc_link(vacc_id serial primary key not null, vacc_healthcare_id integer not null, vacc_vaccination_id integer not null, foreign key(vacc_healthcare_id) references farm.healthcare(healthcare_id),foreign key(vacc_vaccination_id) references master.vaccination(vaccination_id), vacc_dose varchar(50), vacc_vaccination_dt timestamp, vacc_next_vacc_dt timestamp);
 alter table farm.vacc_hc_link add column vacc_hc_type integer not null default 0
 alter table farm.vacc_hc_link add column vacc_hc_vet_id integer ;
 alter table farm.vacc_hc_link add foreign key (vacc_hc_vet_id) references usermanagement.vet(vet_id);

//SURGERY_ILLNESS

CREATE TABLE farm.surgery_illness(surg_ill_id serial primary key not null, surg_ill_cattle_id integer not null,surg_ill_surgery_id integer not null,surg_ill_vet_id integer not null, foreign key(surg_ill_cattle_id) references farm.cattle(cattle_id),foreign key(surg_ill_surgery_id) references master.surgery(surgery_id), foreign key(surg_ill_vet_id) references usermanagement.vet(vet_id), surg_ill_proc_dt timestamp, surg_ill_weight  double precision, surg_ill_temp varchar(20), surg_ill_symp text, surg_ill_assess text, surg_ill_treat text, surg_ill_vet_sign varchar(50));

//MILK PRODUCTION

CREATE TABLE  farm.milk_production(milk_prod_id serial primary key not null, milk_prod_doe_id integer not null, foreign key(milk_prod_doe_id) references farm.cattle(cattle_id), milk_prod_lactation_dt timestamp, milk_prod_lactation double precision);
alter table farm.milk_production rename column milk_prod_lactation_dt to milk_prod_dt;
alter table farm.milk_production add column milk_prod_comments text;
alter table farm.milk_production rename column milk_prod_lactation to milk_prod_qty;


alter table farm.vacc_hc_link add column vacc_routine varchar(50) ;
alter table farm.vacc_hc_link alter column vacc_vaccination_id drop not null;
alter table farm.vacc_hc_link drop column vaccination_unit;

alter table farm.vacc_hc_link add column created_dt timestamp;
alter table farm.vacc_hc_link add column updated_dt timestamp;
alter table master.surgery add column created_dt timestamp;
alter table master.surgery add column updated_dt timestamp;
alter table farm.healthcare add column created_dt timestamp;
alter table farm.healthcare add column updated_dt timestamp;
alter table farm.milk_production add column created_dt timestamp;
alter table farm.milk_production add column updated_dt timestamp;
alter table farm.breeding add column created_dt timestamp;
alter table farm.breeding add column updated_dt timestamp;
alter table usermanagement.role add column created_dt timestamp;
alter table usermanagement.role add column updated_dt timestamp;
alter table usermanagement.user_details add column created_dt timestamp;
alter table usermanagement.user_details add column updated_dt timestamp;
alter table usermanagement.vet add column created_dt timestamp;
alter table usermanagement.vet add column updated_dt timestamp;
alter table master.vaccination add column created_dt timestamp;
alter table master.vaccination add column updated_dt timestamp;
alter table farm.breed_kids add column created_dt timestamp;
alter table farm.breed_kids add column updated_dt timestamp;
alter table farm.surgery_illness add column created_dt timestamp;
alter table farm.surgery_illness add column updated_dt timestamp;
alter table usermanagement.owner add column created_dt timestamp;
alter table usermanagement.owner add column updated_dt timestamp;
alter table farm.farm_owner_link add column created_dt timestamp;
alter table farm.farm_owner_link add column updated_dt timestamp;
alter table farm.farm_vet_link add column created_dt timestamp;
alter table farm.farm_vet_link add column updated_dt timestamp;
alter table farm.farm add column created_dt timestamp;
alter table farm.farm add column updated_dt timestamp;
alter table farm.cattle_image add column created_dt timestamp;
alter table farm.cattle_image add column updated_dt timestamp;
alter table farm.surg_ill_det add column created_dt timestamp;
alter table farm.surg_ill_det add column updated_dt timestamp;
alter table farm.cattle add column created_dt timestamp;
alter table farm.cattle add column updated_dt timestamp;
alter table usermanagement.user add column created_dt timestamp;
alter table usermanagement.user add column updated_dt timestamp;
alter table master.surgery add column created_dt timestamp;
alter table master.surgery add column updated_dt timestamp;



alter table farm.vacc_hc_link add column deleted_dt timestamp;
alter table farm.vacc_hc_link add column unique_sync_key varchar(75);
alter table master.surgery add column deleted_dt timestamp;
alter table master.surgery add column unique_sync_key varchar(75);
alter table farm.healthcare add column deleted_dt timestamp;
alter table farm.healthcare add column unique_sync_key varchar(75);
alter table farm.milk_production add column deleted_dt timestamp;
alter table farm.milk_production add column unique_sync_key varchar(75);
alter table farm.breeding add column deleted_dt timestamp;
alter table farm.breeding add column unique_sync_key varchar(75);
alter table usermanagement.role add column deleted_dt timestamp;
alter table usermanagement.role add column unique_sync_key varchar(75);
alter table usermanagement.user_details add column deleted_dt timestamp;
alter table usermanagement.user_details add column unique_sync_key varchar(75);
alter table usermanagement.vet add column deleted_dt timestamp;
alter table usermanagement.vet add column unique_sync_key varchar(75);
alter table master.vaccination add column deleted_dt timestamp;
alter table master.vaccination add column unique_sync_key varchar(75);
alter table farm.breed_kids add column deleted_dt timestamp;
alter table farm.breed_kids add column unique_sync_key varchar(75);
alter table farm.surgery_illness add column deleted_dt timestamp;
alter table farm.surgery_illness add column unique_sync_key varchar(75);
alter table usermanagement.owner add column deleted_dt timestamp;
alter table usermanagement.owner add column unique_sync_key varchar(75);
alter table farm.farm_owner_link add column deleted_dt timestamp;
alter table farm.farm_owner_link add column unique_sync_key varchar(75);
alter table farm.farm_vet_link add column deleted_dt timestamp;
alter table farm.farm_vet_link add column unique_sync_key varchar(75);
alter table farm.farm add column deleted_dt timestamp;
alter table farm.farm add column unique_sync_key varchar(75);
alter table farm.cattle_image add column deleted_dt timestamp;
alter table farm.cattle_image add column unique_sync_key varchar(75);
alter table farm.surg_ill_det add column deleted_dt timestamp;
alter table farm.surg_ill_det add column unique_sync_key varchar(75);
alter table farm.cattle add column deleted_dt timestamp;
alter table farm.cattle add column unique_sync_key varchar(75);
alter table usermanagement.user add column deleted_dt timestamp;
alter table usermanagement.user add column unique_sync_key varchar(75);



alter table farm.vacc_hc_link drop column create_dt ;
alter table master.surgery drop column create_dt ;
alter table farm.healthcare drop column create_dt ;
alter table farm.milk_production drop column create_dt ;
alter table farm.breeding drop column create_dt ;
alter table usermanagement.role drop column create_dt ;
alter table usermanagement.user_details drop column create_dt ;
alter table usermanagement.vet drop column create_dt ;
alter table master.vaccination drop column create_dt ;
alter table farm.breed_kids drop column create_dt ;
alter table farm.surgery_illness drop column create_dt ;
alter table usermanagement.owner drop column create_dt ;
alter table farm.farm_owner_link drop column create_dt ;
alter table farm.farm_vet_link drop column create_dt ;
alter table farm.farm drop column create_dt ;
alter table farm.cattle_image drop column create_dt ;
alter table farm.surg_ill_det drop column create_dt ;
alter table farm.cattle drop column create_dt ;
alter table usermanagement.user drop column create_dt ;
alter table master.surgery drop column create_dt ;

create table farm.cattle_type (cattle_type_id serial primary key not null,cattle_type_name varchar(100) not null);
alter table farm.cattle add column cattle_type integer;
update farm.cattle set cattle_type = 1;
insert into farm.cattle_type(cattle_type_name) values('GOAT');
alter table farm.cattle add foreign key (cattle_type) references farm.cattle_type(cattle_type_id);

alter table farm.cattle_type add column deleted_dt timestamp;
alter table farm.cattle_type add column unique_sync_key varchar(75);
alter table farm.cattle_type add column created_dt timestamp;
alter table farm.cattle_type add column updated_dt timestamp;

create table farm.farm_owner_link_temp(farm_owner_link_id serial primary key not null, farm_owner_link_farm_id integer not null, farm_owner_link_owner_id integer not null, foreign key(farm_owner_link_farm_id) references farm.farm(farm_id), foreign key(farm_owner_link_owner_id) references usermanagement.owner(owner_id));
alter table farm.farm_owner_link_temp add column created_dt timestamp;
alter table farm.farm_owner_link_temp add column updated_dt timestamp;
alter table farm.farm_owner_link_temp add column deleted_dt timestamp;
alter table farm.farm_owner_link_temp add column unique_sync_key varchar(75);
create table farm.cattle_image_temp(cattle_image_id serial primary key not null, cattle_image_url text not null,cattle_image_is_primary boolean not null default false,cattle_image_cattle_id integer not null,created_dt timestamp, updated_dt timestamp, deleted_dt timestamp,unique_sync_key varchar(75),foreign key(cattle_image_cattle_id) references farm.cattle(cattle_id) );
create table farm.surg_ill_det_temp (surg_ill_det_id serial primary key not null,surg_ill_det_surg_ill_id integer not null,surg_ill_det_name varchar(100),surg_ill_det_dose double precision,surg_ill_det_dose_unit varchar(20),surg_ill_det_treat text,updated_dt timestamp,deleted_dt timestamp,unique_sync_key);

create table farm.farm_vet_link_temp(farm_vet_link_id serial primary key not null, farm_vet_link_farm_id integer not null, farm_vet_link_vet_id integer not null, foreign key(farm_vet_link_farm_id) references farm.farm(farm_id), foreign key(farm_vet_link_vet_id) references usermanagement.vet(vet_id));
  alter table farm.farm_vet_link_temp add column created_dt timestamp;
  alter table farm.farm_vet_link_temp add column updated_dt timestamp;
  alter table farm.farm_vet_link_temp add column deleted_dt timestamp;
  alter table farm.farm_vet_link_temp add column unique_sync_key varchar(75);
  
   alter table master.surgery add column surgery_comments text;
alter table master.surgery add constraint surgery_name_unq unique(surgery_name);
alter table farm.cattle_type add constraint cattle_type_name_unq unique(cattle_type_name);

alter table farm.breeding alter column breeding_doe_id drop not null;
 alter table farm.milk_production alter column milk_prod_dt set data type date;
 insert into usermanagement.user (user_id,user_name,user_password,user_role_id,user_created_dt,user_isactive,user_fname,unique_sync_key,created_dt) values(2,'defaultowner','YWRtaW4=',2,now(),true,'defaultowner','defaultowner',now());