tbl_address

CREATE TABLE usermanagement.tbl_address
(
  pki_address_id serial NOT NULL,
  fki_user_id integer NOT NULL,foreign key(fki_user_id) references usermanagement.tbl_user(pki_user_id),
  vc_location varchar(75) NOT NULL,
  vc_location_lattitude varchar(75) NOT NULL,
  vc_location_longitude varchar(75) NOT NULL,
  i_location_buffer integer,
  vc_pin_code varchar(50),
  i_is_primary integer not null
  );

Tbl_country


CREATE TABLE tbl_country
(
  pki_country_id serial primary key NOT NULL,
  vc_country_name varchar(100) NOT NULL,
  vc_country_code varchar(50) NOT NULL
  );

tbl_state


CREATE TABLE tbl_state
(
  pki_state_id serial primary key NOT NULL,
  vc_state_name varchar(100) NOT NULL,
  vc_state_code varchar(50) NOT NULL
  );

Usermanagement.tbl_user_details

 alter table usermanagement.tbl_user_details add  CONSTRAINT fk_country_id foreign key(fki_country_id) references tbl_country;




tbl_images


CREATE TABLE tbl_images
(
  pki_image_id serial primary key NOT NULL,
  vc_image_url varchar(100) NOT NULL,
  b_is_primary boolean,
  i_mage_order integer,
  fki_user_id bigint not null, foreign key(fki_user_id) references usermanagement.tbl_user(pki_user_id)
  );


Hospital.tbl_document_type


CREATE TABLE hospital.tbl_document_type
(
  pki_document_type_id serial primary key NOT NULL,
  vc_document_type_name varchar(100) NOT NULL
 
  );

Hospital.tbl_document


CREATE TABLE hospital.tbl_document
(
  pki_document_id serial primary key NOT NULL,
  vc_document_name varchar(100) NOT NULL,
  fki_document_type_id  bigint not null, foreign key(fki_document_type_id) references hospital.tbl_document_type(pki_document_type_id),
  vc_document_url varchar(100) ,
  fki_user_id bigint not null, foreign key(fki_user_id) references usermanagement.tbl_user(pki_user_id)
  );

alter table hospital.tbl_document add column t_text text




hospital.tbl_hos_dept_type


CREATE TABLE hospital.tbl_hos_dept_type
(
  pki_hos_dept_type_id serial primary key NOT NULL,
  vc_hos_dept_type_name  varchar(100) unique NOT NULL,
  fki_parent_dept_type_id  bigint not null, foreign key(fki_parent_dept_type_id) references hospital.tbl_hos_dept_type(pki_hos_dept_type_id)
  );


hospital.tbl_patient_appnt


CREATE TABLE hospital.tbl_patient_appnt
(
  pki_patient_appnt_id serial primary key NOT NULL,
  vc_token_number  varchar(100) unique NOT NULL,
  i_confirmation_status integer not null default 0,
  fki_hospital_id  bigint , foreign key(fki_hospital_id) references usermanagement.tbl_user(pki_user_id),
  fki_doctor_id  bigint not null, foreign key(fki_doctor_id) references usermanagement.tbl_user(pki_user_id),
  fki_patient_id  bigint not null, foreign key(fki_patient_id) references usermanagement.tbl_user(pki_user_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  vc_op_number varchar(100) ,
  fki_hos_dept_type_id bigint not null, foreign key(fki_hos_dept_type_id) references hospital.tbl_hos_dept_type(pki_hos_dept_type_id),
  i_appnt_type integer not null default 1, --general,new,old etc
  dt_appnt_time timestamp without time zone,
  vc_reference_name varchar(75),
  fki_reference_patient bigint , foreign key(fki_reference_patient) references usermanagement.tbl_user(pki_user_id),
  vc_description varchar(200)
  );

 alter table hospital.tbl_patient_appnt add column i_consultation_type integer not null default 1;
 alter table hospital.tbl_patient_appnt add column i_consession_type integer not null default 1;





hospital.tbl_appnt_setting


CREATE TABLE hospital.tbl_appnt_setting
(
  pki_appnt_setting_id serial primary key NOT NULL,
  fki_hospital_id  bigint , foreign key(fki_hospital_id) references usermanagement.tbl_user(pki_user_id),
fki_doctor_id  bigint , foreign key(fki_doctor_id) references usermanagement.tbl_user(pki_user_id),
dt_date timestamp without time zone default timezone('utc'::text, now()),
i_no_of_appnts_per_day int not null default 0,
fki_hos_dept_type_id integer not null ,foreign key(fki_hos_dept_type_id) references hospital.tbl_hos_dept_type(pki_hos_dept_type_id),
t_description text
);
Alter table hospital.tbl_appnt_setting add column vc_time_slot varchar(100) not null;
 
hospital.tbl_appnt_setting

CREATE TABLE hospital.tbl_appnt_setting
(
  pki_appnt_setting_id serial primary key NOT NULL,
  fki_hospital_id  bigint , foreign key(fki_hospital_id) references usermanagement.tbl_user(pki_user_id),
fki_doctor_id  bigint , foreign key(fki_doctor_id) references usermanagement.tbl_user(pki_user_id),
dt_date timestamp without time zone default timezone('utc'::text, now()),
i_no_of_appnts_per_day int not null default 0,
fki_hos_dept_type_id integer not null ,foreign key(fki_hos_dept_type_id) references hospital.tbl_hos_dept_type(pki_hos_dept_type_id),
t_description text
);











hospital.tbl_appnt_setting_det


CREATE TABLE hospital.tbl_appnt_setting_det
(
pki_appnt_setting_det_id serial primary key NOT NULL,
fki_appnt_setting_id  bigint , foreign key(fki_appnt_setting_id) references hospital.tbl_appnt_setting(pki_appnt_setting_id),
vc_day varchar(75) not null,
vc_time_slot varchar(500) not null,
t_description text
);



hospital.tbl_hos_test_report


CREATE TABLE hospital.tbl_hos_test_report
(
  pki_hos_test_report_id serial primary key NOT NULL,
  vc_hos_test_report_name  varchar(100) unique NOT NULL,
  fki_user_id bigint not null, foreign key(fki_user_id) references usermanagement.tbl_user(pki_user_id),
  dt_date timestamp without time zone not null,
  vc_hos_test_report_code varchar(100) ,
  vc_op_number  varchar(100) ,
  fki_patient_appnt_id bigint not null, foreign key(fki_patient_appnt_id) references hospital.tbl_patient_appnt(pki_patient_appnt_id),
  fki_parent_dept_type_id  bigint not null, foreign key(fki_parent_dept_type_id) references hospital.tbl_hos_dept_type(pki_hos_dept_type_id)
  );

hospital.tbl_hos_notification

CREATE TABLE hospital.tbl_hos_notification
(
  pki_hos_notification_id serial primary key NOT NULL,
  fki_hospital_id  bigint , foreign key(fki_hospital_id) references usermanagement.tbl_user(pki_user_id),
  fki_doctor_id  bigint not null, foreign key(fki_doctor_id) references usermanagement.tbl_user(pki_user_id),
  dt_from_date timestamp without time zone not null,
  dt_to_date timestamp without time zone not null,
  fki_hos_dept_type_id bigint not null, foreign key(fki_hos_dept_type_id) references hospital.tbl_hos_dept_type(pki_hos_dept_type_id),
  t_description text
  );

hospital.tbl_doctor_qualif_master


CREATE TABLE hospital.tbl_doctor_qualif_master
(
pki_doctor_qualif_master_id serial primary key NOT NULL,
uvc_qualif_name varchar(100),
t_description text
);

hospital.tbl_doctor_qualif_link

CREATE TABLE hospital.tbl_doctor_qualif_link
(
pki_doctor_qualif_link_id serial primary key NOT NULL,
fki_doctor_qualif_master_id  bigint , foreign key(fki_doctor_qualif_master_id) references hospital.tbl_doctor_qualif_master(pki_doctor_qualif_master_id),
fki_doctor_id  bigint , foreign key(fki_doctor_id) references usermanagement.tbl_user(pki_user_id)
);

hospital.tbl_hos_post_consultation


CREATE TABLE hospital.tbl_hos_post_consultation
(
  pki_hos_post_consultation_id serial primary key NOT NULL,
  fki_patient_id  bigint , foreign key(fki_patient_id) references usermanagement.tbl_user(pki_user_id),
  fki_doctor_id  bigint not null, foreign key(fki_doctor_id) references usermanagement.tbl_user(pki_user_id),
  fki_hospital_id  bigint , foreign key(fki_hospital_id) references usermanagement.tbl_user(pki_user_id),
  fki_patient_appnt_id bigint not null, foreign key(fki_patient_appnt_id) references hospital.tbl_patient_appnt(pki_patient_appnt_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  dt_next_consultation_date timestamp without time zone not null,
  vc_medicines_prescribed  text,
  t_description text
  );

tbl_trust_stories


CREATE TABLE tbl_trust_stories
(
  pki_trust_stories_id serial primary key NOT NULL,
  fki_provider_parent_id   bigint , foreign key(fki_provider_parent_id) references    usermanagement.tbl_user(pki_user_id),
  fki_provider_id  bigint not null, foreign key(fki_provider_id) references usermanagement.tbl_user(pki_user_id),
  fki_consumer_id  bigint , foreign key(fki_consumer_id) references usermanagement.tbl_user(pki_user_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  t_description text,
  i_rating integer not null default 0
  );

restaurant.tbl_rest_food_category

CREATE TABLE restaurant.tbl_rest_food_category
(
  pki_rest_food_category_id serial primary key NOT NULL,
  vc_rest_food_category_name varchar(100) unique NOT NULL,
  t_description text,
  i_rating integer default 0
  );



restaurant.tbl_rest_food_types


CREATE TABLE restaurant.tbl_rest_food_types
(
  pki_rest_food_types_id serial primary key NOT NULL,
  vc_food_type_name varchar(100) unique NOT NULL,
  fki_rest_food_category_id bigint, foreign key(fki_rest_food_category_id) references restaurant.tbl_rest_food_category(pki_rest_food_category_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  t_description text,
  i_rating integer default 0
  );

 restaurant.tbl_rest_order

CREATE TABLE restaurant.tbl_rest_order
(
  pki_rest_order_id serial primary key NOT NULL,
  fki_rest_id bigint not null, foreign key(fki_rest_id) references usermanagement.tbl_user(pki_user_id),
  fki_user_id   bigint not null, foreign key(fki_rest_id) references usermanagement.tbl_user(pki_user_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  dt_delivery_time timestamp without time zone,
  vc_token_number  varchar(100) unique NOT NULL,
  fki_rest_food_types_id bigint not null, foreign key(fki_rest_food_types_id) references restaurant.tbl_rest_food_types(pki_rest_food_types_id),
  i_confirmation_status integer not null default 0,
  vc_reference_name varchar(75),
  fki_reference_user bigint , foreign key(fki_reference_user) references usermanagement.tbl_user(pki_user_id),
  vc_order_status varchar(200),
  vc_delivery_boy_name  varchar(100)
  );








 tbl_alerts


CREATE TABLE tbl_alerts
(
  pki_alert_id serial primary key NOT NULL,
  fki_from_user_id bigint not null, foreign key(fki_from_user_id) references usermanagement.tbl_user(pki_user_id),
  fki_from_user_type_id bigint not null, foreign key(fki_from_user_type_id) references usermanagement.tbl_user_type(pki_user_type_id),
  fki_to_user_id bigint not null, foreign key(fki_to_user_id) references usermanagement.tbl_user(pki_user_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  vc_token_number  varchar(100) unique NOT NULL,
  i_alert_type integer not null default 0,
  t_description text
  );


 tbl_favourites


CREATE TABLE tbl_favourites
(
  pki_favourite_id serial primary key NOT NULL,
  fki_provider_user_id bigint not null, foreign key(fki_provider_user_id) references usermanagement.tbl_user(pki_user_id),
  fki_provider_user_type_id bigint not null, foreign key(fki_provider_user_type_id) references usermanagement.tbl_user_type(pki_user_type_id),
  fki_consumer_user_id bigint not null, foreign key(fki_consumer_user_id) references usermanagement.tbl_user(pki_user_id),
  dt_date timestamp  without time zone DEFAULT timezone('utc'::text, now()),
  t_description text
  );



--09-09-2016-- Jinesh

alter table hospital.tbl_hos_dept_type add column dt_created_date timestamp  without time zone DEFAULT timezone('utc'::text, now());
alter table hospital.tbl_hos_dept_type add column dt_updated_date timestamp  without time zone;
alter table hospital.tbl_hos_dept_type alter column fki_parent_dept_type_id drop not null;

--10-092016-- Ciby

ALTER TABLE usermanagement.tbl_user ADD COLUMN dt_tem_password_dt timestamp without time zone;
COMMENT ON COLUMN usermanagement.tbl_user.dt_tem_password_dt IS 'temporary password update time ';


-- added state & country column  in usermanagement.tbl_address  table
alter table usermanagement.tbl_user_details drop column fki_country_id;
ALTER TABLE usermanagement.tbl_address ADD COLUMN fki_state_id bigint;


ALTER TABLE usermanagement.tbl_address
  ADD CONSTRAINT fki_state_id FOREIGN KEY (fki_state_id)
      REFERENCES public.tbl_state (pki_state_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE usermanagement.tbl_address ADD COLUMN fki_country_id bigint;

      ALTER TABLE usermanagement.tbl_address
  ADD CONSTRAINT fk_country_id FOREIGN KEY (fki_country_id)
      REFERENCES public.tbl_country (pki_country_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

--NB: DROPPED fki_country_id  column in usermanagement.tbl_user_details table

--21-09-2016-- Jinesh
alter table hospital.tbl_hos_dept_type alter column fki_parent_dept_type_id drop not null;
alter TABLE hospital.tbl_doctor_qualif_master alter column uvc_qualif_name set not null
