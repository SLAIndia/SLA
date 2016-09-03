
--=======================================farm.fn_farm_save==================================================================

CREATE OR REPLACE FUNCTION farm.fn_farm_save (tmp_farm_id IN integer, tmp_farm_name IN VARCHAR,tmp_farm_address in text, tmp_farm_location in varchar, tmp_farm_created_dt timestamp, tmp_farm_status boolean, tmp_created_dt timestamp)
RETURNS SETOF farm.typ_farm AS 
$$
declare
    	tmp_farm_code varchar;
	queryString varchar;
	users_rec farm.typ_farm;
BEGIN
   if tmp_farm_id = 0 then
   select nextval('farm.farm_farm_id_seq') into tmp_farm_id;
   tmp_farm_code:= 'FARM-'||tmp_farm_id;
   insert into farm.farm(farm_id, farm_name, farm_address, farm_location,farm_code, farm_created_dt, farm_status,created_dt,unique_sync_key) values(tmp_farm_id,tmp_farm_name,tmp_farm_address,tmp_farm_location,tmp_farm_code, tmp_farm_created_dt, tmp_farm_status,tmp_created_dt,tmp_farm_code);

	
elsif tmp_farm_id > 0 then
	update farm.farm set farm_name = tmp_farm_name, farm_location = tmp_farm_location, farm_address = tmp_farm_address, farm_status = tmp_farm_status, updated_dt =tmp_created_dt where farm_id = tmp_farm_id ;
end if;

queryString:='select farm_id,farm_name,farm_code,farm_address,farm_location,farm_status,farm_created_dt from farm.farm where farm_id = '||tmp_farm_id||'';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;

END;
$$ LANGUAGE plpgsql;


--select * from farm.fn_farm_save (0,'FARM','ADDRESS','LOCATION','2014-05-13 00:00:00',true,'2014-05-13 00:00:00');




--======================================= usermanagement.typ_user==================================================================



CREATE TYPE usermanagement.typ_user AS(
	user_id integer,     
	user_name varchar, 
	user_password varchar,
	user_role_id integer,  
	user_role_name varchar,  
	user_active boolean,     
	user_created_dt timestamp,
	user_updated_dt timestamp,
	user_temp_password varchar,
        createddt timestamp
);



--====================================== usermanagement.fn_user_sel================================================================


CREATE OR REPLACE FUNCTION usermanagement.fn_user_sel(tmp_username IN VARCHAR,tmp_user_password in VARCHAR)
	RETURNS SETOF usermanagement.typ_user AS
	$$

	DECLARE
		queryString varchar;
		users_rec usermanagement.typ_user;

	BEGIN
	queryString:='select user_id,user_name,user_password,user_role_id,role_name,user_isactive,user_created_dt,user_updated_dt,user_temp_password,usr.created_dt from usermanagement.user usr inner join 		usermanagement.role on(user_role_id=role_id) where UPPER(user_name) = UPPER('''||tmp_username||''')';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from usermanagement.fn_user_sel('admin','admin');

--======================================= farm.typ_farm==================================================================



CREATE TYPE farm.typ_farm AS(
	farm_id integer,     
	farm_name varchar,  
	farm_code varchar,      
	farm_address text,  
	farm_location varchar,  
	farm_status boolean,
	farm_created_dt timestamp
);



--====================================== farm.fn_farm_sel================================================================


CREATE OR REPLACE FUNCTION farm.fn_farm_sel()
	RETURNS SETOF farm.typ_farm AS
	$$

	DECLARE
		queryString varchar;
		users_rec farm.typ_farm;

	BEGIN
	queryString:='select farm_id,farm_name,farm_code, farm_address,farm_location,farm_status,farm_created_dt from farm.farm ';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from farm.fn_farm_sel();



--=======================================usermanagement.fn_user_save==================================================================


CREATE OR REPLACE FUNCTION usermanagement.fn_user_save (tmp_user_name IN VARCHAR,tmp_user_password in varchar, tmp_role_name in varchar, tmp_user_created_dt  timestamp)
RETURNS integer AS
$$
declare
	tmp_user_id integer;tmp_role_id integer;
BEGIN
   select role_id into tmp_role_id from usermanagement.role where UPPER(role_name) = UPPER(tmp_role_name);
   select nextval('usermanagement.user_user_id_seq') into tmp_user_id;
   insert into usermanagement.user(user_id, user_name,user_password, user_role_id, user_created_dt) values(tmp_user_id, tmp_user_name,crypt(tmp_user_password, gen_salt('md5')), tmp_role_id, tmp_user_created_dt);
   select user_id into tmp_user_id from usermanagement.user where user_id = tmp_user_id;
   RETURN tmp_user_id;
END;
$$ LANGUAGE plpgsql;


--select * from usermanagement.fn_user_save('newadmin','admin','admin','2014-05-12 00:00:00');


--=======================================farm.fn_owner_farm_sel==================================================================

CREATE OR REPLACE FUNCTION farm.fn_owner_farm_sel(tmp_user_id in integer)
	RETURNS SETOF farm.typ_farm AS
	$$

	DECLARE
		queryString varchar;
		users_rec farm.typ_farm;

	BEGIN
	queryString:='select farm_id,farm_name,farm_code,farm_address,farm_location,farm_status,farm_created_dt from usermanagement.user inner join usermanagement.owner on(owner_user_id = user_id) inner join farm.farm_owner_link on (owner_id = farm_owner_link_owner_id) inner join farm.farm on (farm_id = farm_owner_link_farm_id) where user_id = '||tmp_user_id||' and user_isactive = true and farm_status = true';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';

--select * from farm.fn_owner_farm_sel(2);


--=================================================================farm.typ_cattle==========================================================================


CREATE TYPE farm.typ_cattle AS(
	cattle_id integer,     
	cattle_ear_tag_id varchar,        
	cattle_location varchar,  
	cattle_farm_id integer,  
	cattle_dob timestamp without time zone,     
	cattle_gender char,
	cattle_category char,
	cattle_status boolean,
	cattle_buck_id integer,
	cattle_doe_id integer,
	cattle_created_dt timestamp without time zone ,
	cattle_latt varchar,
	cattle_long varchar,
	cattle_land_mark varchar ,
	createddt timestamp 
);



--========================================farm.fn_cattle_save===============================================================================================

CREATE OR REPLACE FUNCTION farm.fn_cattle_save (tmp_cattle_id in integer, tmp_cattle_farm_id in integer,tmp_cattle_dob  timestamp,tmp_cattle_gender in char,tmp_cattle_location in varchar, tmp_cattle_category in char,tmp_cattle_status in boolean,tmp_cattle_buck_id in integer,tmp_cattle_doe_id in integer,tmp_cattle_created_dt  timestamp,tmp_cattle_latt varchar, tmp_cattle_long varchar, tmp_land_mark varchar,tmp_created_date timestamp)
RETURNS SETOF farm.typ_cattle AS 
$$
declare
    	tmp_cattle_ear_tag_id varchar;
	queryString varchar;
	tmp_farm_code varchar;
	users_rec farm.typ_cattle;
BEGIN

if tmp_cattle_doe_id=0 then
tmp_cattle_doe_id := null;
end if;
if tmp_cattle_buck_id=0 then
tmp_cattle_buck_id := null;
end if;
if tmp_cattle_latt = 'null' then
tmp_cattle_latt := null;
end if;
if tmp_cattle_long = 'null' then
tmp_cattle_long := null;
end if;
if tmp_land_mark = 'null' then
tmp_land_mark := null;
end if;



  
   select farm_code into tmp_farm_code from farm.farm where farm_id = tmp_cattle_farm_id;
   if tmp_farm_code  is not null then 
   if tmp_cattle_id = 0 then

   select nextval('farm.cattle_cattle_id_seq') into tmp_cattle_id;
   tmp_cattle_ear_tag_id:= tmp_farm_code||'/ET-'||tmp_cattle_id ;

   insert into farm.cattle(cattle_id, cattle_ear_tag_id, cattle_location, cattle_farm_id, cattle_dob , cattle_gender, cattle_category,cattle_status,cattle_buck_id,cattle_doe_id,cattle_created_dt,cattle_latt,cattle_long,cattle_land_mark,unique_sync_key,created_dt) values(tmp_cattle_id, tmp_cattle_ear_tag_id, tmp_cattle_location,tmp_cattle_farm_id,tmp_cattle_dob,tmp_cattle_gender, tmp_cattle_category,tmp_cattle_status,tmp_cattle_buck_id,tmp_cattle_doe_id,tmp_cattle_created_dt,tmp_cattle_latt, tmp_cattle_long , tmp_land_mark,tmp_cattle_ear_tag_id,tmp_created_date);

	
elsif tmp_cattle_id > 0 then
	update farm.cattle set  cattle_location = tmp_cattle_location,cattle_farm_id = tmp_cattle_farm_id,cattle_dob = tmp_cattle_dob, cattle_gender = tmp_cattle_gender , cattle_category = tmp_cattle_category,cattle_status = tmp_cattle_status,cattle_buck_id = tmp_cattle_buck_id,cattle_doe_id = tmp_cattle_doe_id, cattle_latt = tmp_cattle_latt, cattle_long = tmp_cattle_long , cattle_land_mark = tmp_land_mark, updated_dt = tmp_created_date  where cattle_id = tmp_cattle_id;
end if;
end if;
queryString:='select cattle_id, cattle_ear_tag_id, cattle_location, cattle_farm_id, cattle_dob , cattle_gender, cattle_category, cattle_status, cattle_buck_id, cattle_doe_id, cattle_created_dt,cattle_latt,cattle_long,cattle_land_mark,created_dt from farm.cattle where cattle_id = '||tmp_cattle_id||'';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;

END;
$$ LANGUAGE plpgsql;



--select * from farm.fn_cattle_save (0, 4,'2015-05-01 00:00:00','M','AFRICA', 'A',true, 0 ,0,'2015-05-18 00:00:00','1245878.1556','24578955.2564','Near Central School','2015-05-18 00:00:00');


--=======================================farm.fn_vet_farm_sel==================================================================


CREATE OR REPLACE FUNCTION farm.fn_vet_farm_sel(tmp_user_id in integer)
	RETURNS SETOF farm.typ_farm AS
	$$

	DECLARE
		queryString varchar;
		users_rec farm.typ_farm;

	BEGIN
	queryString:='select farm_id,farm_name,farm_code,farm_address,farm_location,farm_status,farm_created_dt from usermanagement.user inner join usermanagement.vet on(vet_user_id = user_id) inner join farm.farm_vet_link on (vet_id = farm_vet_link_vet_id) inner join farm.farm on (farm_id = farm_vet_link_farm_id) where user_id = '||tmp_user_id||' and vet_status = 1 and user_isactive = true and farm_status = true';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';

--select * from farm.fn_vet_farm_sel(2);



--=======================================farm.fn_owner_farm_all_sel==================================================================

CREATE OR REPLACE FUNCTION farm.fn_owner_farm_all_sel(tmp_user_id in integer)
	RETURNS SETOF farm.typ_farm AS
	$$

	DECLARE
		queryString varchar;
		users_rec farm.typ_farm;

	BEGIN
	queryString:='select farm_id,farm_name,farm_code,farm_address,farm_location,farm_status,farm_created_dt from usermanagement.user inner join usermanagement.owner on(owner_user_id = user_id) inner join farm.farm_owner_link on (owner_id = farm_owner_link_owner_id) inner join farm.farm on (farm_id = farm_owner_link_farm_id) where user_id = '||tmp_user_id||' and user_isactive = true';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';

--select * from farm.fn_owner_farm_all_(2);



--=======================================farm.fn_vet_farm_all_sel==================================================================


CREATE OR REPLACE FUNCTION farm.fn_vet_farm_all_sel(tmp_user_id in integer)
	RETURNS SETOF farm.typ_farm AS
	$$

	DECLARE
		queryString varchar;
		users_rec farm.typ_farm;

	BEGIN
	queryString:='select farm_id,farm_name,farm_code,farm_address,farm_location,farm_status,farm_created_dt from usermanagement.user inner join usermanagement.vet on(vet_user_id = user_id) inner join farm.farm_vet_link on (vet_id = farm_vet_link_vet_id) inner join farm.farm on (farm_id = farm_vet_link_farm_id) where user_id = '||tmp_user_id||' and vet_status = 1 and user_is_active = true';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';

--select * from farm.fn_vet_farm_sel(2); 


--====================================== farm.fn_farm_by_sel================================================================


CREATE OR REPLACE FUNCTION farm.fn_farm_by_sel(tmp_code varchar)
	RETURNS SETOF farm.typ_farm AS
	$$

	DECLARE
		queryString varchar;
		users_rec farm.typ_farm;

	BEGIN
	queryString:='select farm_id,farm_name,farm_code, farm_address,farm_location,farm_status,farm_created_dt from farm.farm where UPPER(farm_code) like  UPPER(''%'||tmp_code||'%'') OR UPPER(farm_name) like UPPER(''%'||tmp_code||'%'')';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from farm.fn_farm_by_sel('farm');


--======================================== usermanagement.typ_vet===================================


CREATE TYPE usermanagement.typ_vet AS(
	user_id integer,     
	user_name varchar, 
	user_password varchar,
	user_role_id integer,  
	user_role_name varchar,  
	user_active boolean,     
	user_created_dt timestamp,
	user_updated_dt timestamp,
        user_fname varchar,
        user_lname varchar,
        user_location varchar,
        user_address text,
        user_phone varchar,
        user_email varchar,
	vet_status integer,
	vet_id integer
);



--=====================================usermanagement.fn_vet_all_by_name_sel=============================

CREATE OR REPLACE FUNCTION usermanagement.fn_vet_all_by_name_sel(tmp_name IN VARCHAR)
	RETURNS SETOF usermanagement.typ_vet AS
	$$

	DECLARE
		queryString varchar;
		users_rec usermanagement.typ_vet;

	BEGIN
	queryString:='select user_id,user_name,user_password,user_role_id,role_name,user_isactive,user_created_dt,user_updated_dt,user_fname, user_lname, user_location, user_address,user_phone, user_email,vet_status, vet_id  from usermanagement.user inner join 		usermanagement.role on(user_role_id=role_id) inner join usermanagement.vet on(vet_user_id = user_id) where UPPER(user_fname) like  UPPER(''%'||tmp_name||'%'') OR UPPER(user_lname) like UPPER(''%'||tmp_name||'%'') and UPPER(role_name) = UPPER(''vet'')';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from usermanagement.fn_vet_all_by_name_sel('fname');




--=======================farm.typ_farm_cattle==============================================

CREATE TYPE farm.typ_farm_cattle AS(
	cattle_id integer,     
	cattle_ear_tag_id varchar,        
	cattle_location varchar,  
	cattle_farm_id integer,  
	cattle_dob timestamp without time zone,     
	cattle_gender char,
	cattle_category char,
	cattle_status boolean,
	cattle_buck_id integer,
	cattle_doe_id integer,
	cattle_created_dt timestamp without time zone ,
	cattle_latt varchar,
	cattle_long varchar,
	cattle_land_mark varchar  ,
	cattle_farm_code varchar,
        cattle_farm_name varchar
);



--===========================================farm.fn_assigned_farm_cattle_by_user_sel(tmp_user_id IN integer)================================================

CREATE OR REPLACE FUNCTION farm.fn_assigned_farm_cattle_by_user_sel(tmp_user_id IN integer)
	RETURNS SETOF farm.typ_farm_cattle AS
	$$

	DECLARE
		queryString varchar;
		rolename varchar;
		users_rec farm.typ_farm_cattle;

	BEGIN
select role_name into rolename from usermanagement.user  inner join usermanagement.role  on(user_role_id = role_id) where user_id =  tmp_user_id;

if UPPER(rolename) = 'OWNER' then 
	queryString:='select cattle_id,cattle_ear_tag_id,cattle_location,cattle_farm_id,cattle_dob,cattle_gender,cattle_category,cattle_status,cattle_buck_id,cattle_doe_id ,cattle_created_dt,cattle_latt,cattle_long,cattle_land_mark,farm_code,farm_name  from farm.cattle inner join farm.farm on(cattle_farm_id = farm_id) inner join farm.farm_owner_link on(farm_owner_link_farm_id = farm_id) inner join usermanagement.owner on(farm_owner_link_owner_id = owner_id) inner join usermanagement.user on(owner_user_id = user_id) where  user_id = '||tmp_user_id||' and farm_status = true and user_isactive = true';

elsif UPPER(rolename) = 'VET' then 

queryString:='select cattle_id,cattle_ear_tag_id,cattle_location,cattle_farm_id,cattle_dob,cattle_gender,cattle_category,cattle_status,cattle_buck_id,cattle_doe_id ,cattle_created_dt,cattle_latt,cattle_long,cattle_land_mark,farm_code,farm_name  from farm.cattle inner join farm.farm on(cattle_farm_id = farm_id) inner join farm.farm_vet_link on(farm_vet_link_farm_id = farm_id) inner join usermanagement.vet on(farm_vet_link_vet_id = vet_id) inner join usermanagement.user on(vet_user_id = user_id) where  user_id = '||tmp_user_id||' and farm_status = true and user_isactive = true';

else

end if;

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from farm.fn_assigned_farm_cattle_by_user_sel(2);


--=================================farm.typ_breed_kid===================================


CREATE TYPE farm.typ_breed_kid AS(
	cattle_id integer,     
	cattle_ear_tag_id varchar,        
	cattle_location varchar,  
	cattle_farm_id integer,  
	cattle_dob timestamp without time zone,     
	cattle_gender char,
	cattle_category char,
	cattle_status boolean,
	cattle_buck_id integer,
	cattle_doe_id integer,
	cattle_created_dt timestamp without time zone ,
	cattle_latt varchar,
	cattle_long varchar,
	cattle_land_mark varchar  ,
	cattle_farm_code varchar,
    cattle_farm_name varchar,
	breeding_id integer,
    breed_kid_id integer,
    breed_kids_nur_lact_dt timestamp without time zone ,
    breed_kids_weaning_dt timestamp without time zone ,
    breed_kids_weight double precision,
	createddt timestamp
);



--===========================================farm.fn_breed_kid_sel(tmp_user_id IN VARCHAR)================================================
CREATE OR REPLACE FUNCTION farm.fn_breed_kid_sel(tmp_breeding_id IN integer, tmp_breed_kid_id in integer, tmp_farm_id in integer,tmp_breed_kid_cattle_id in integer)
	RETURNS SETOF farm.typ_breed_kid AS
	$$

	DECLARE
		queryString varchar;
		queryStringbreeding varchar;
		queryStringbreedkid varchar;
	        queryStringfarm varchar;
		queryStringcattle varchar;
		users_rec farm.typ_breed_kid;

	BEGIN
     queryStringbreeding := '';queryStringbreedkid := ''; queryStringfarm := '';queryStringcattle := '';
     
     if tmp_breeding_id != 0 then
      queryStringbreeding := ' and breeding_id = '||tmp_breeding_id||' ';
      end if;
     if tmp_breed_kid_id != 0 then
     queryStringbreedkid := ' and breed_kids_id = '||tmp_breed_kid_id||' ';
	end if;
     if tmp_farm_id != 0 then
     queryStringfarm := ' and farm_id = '||tmp_farm_id||' ';
	end if;
if tmp_breed_kid_cattle_id != 0 then
     queryStringcattle := ' and breed_kids_cattle_id = '||tmp_breed_kid_cattle_id||' ';
	end if;

	queryString:='select cattle_id,cattle_ear_tag_id,cattle_location,cattle_farm_id,cattle_dob,cattle_gender,cattle_category,cattle_status,cattle_buck_id,cattle_doe_id ,cattle_created_dt,cattle_latt,cattle_long,cattle_land_mark,farm_code,farm_name,breeding_id,breed_kids_id,breed_kids_nur_lact_dt,breed_kids_weaning_dt, breed_kids_weight,breed_kids.created_dt from farm.cattle inner join farm.breed_kids on(breed_kids_cattle_id = cattle_id) inner join farm.breeding on(breeding_id = breed_kids_breeding_id) inner join farm.farm on(cattle_farm_id = farm_id) where farm_status = true '||queryStringbreeding||''||queryStringbreedkid||''||queryStringfarm||''||queryStringcattle||'';

	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from farm.fn_breed_kid_sel(1,0,0,173);

--=================================farm.typ_breeding===================================

CREATE TYPE farm.typ_breeding AS(
 breeding_id             integer,                   
 breeding_buck_id        integer,                    
 breeding_doe_id         integer  ,                   
 breeding_date           timestamp without time zone ,
 breeding_village        varchar,      
 breeding_aborted_dt     timestamp without time zone  ,
 breeding_kids_no        integer        ,              
 breeding_aborted_no     integer ,                     
 breeding_lact_start_dt  timestamp without time zone  ,
 breeding_lact_end_dt    timestamp without time zone ,
 doe_ear_tag varchar,
 buck_ear_tag varchar,
 buck_status boolean,
 doe_status boolean,
 createddt timestamp
);



--=============farm.fn_breeding_sel(tmp_breeding_id IN integer, tmp_user_id in integer, tmp_farm_id in integer)=======================

CREATE OR REPLACE FUNCTION farm.fn_breeding_sel(tmp_breeding_id IN integer, tmp_user_id in integer, tmp_farm_id in integer)
	RETURNS SETOF farm.typ_breeding AS
	$$


	DECLARE
		queryString varchar;
		queryStringbreeding varchar;
		rolename varchar;
	        queryStringfarm varchar;
		users_rec farm.typ_breeding;
                

	BEGIN
     queryStringbreeding := ''; queryStringfarm := '';
     

     select role_name into rolename from usermanagement.user  inner join usermanagement.role  on(user_role_id = role_id) where user_id =  tmp_user_id;

     if tmp_breeding_id != 0 then
      queryStringbreeding := ' and breeding_id = '||tmp_breeding_id||' ';
      end if;
     if tmp_farm_id != 0 then
     queryStringfarm := ' and farm_id = '||tmp_farm_id||' ';
	end if;

if UPPER(rolename) = 'OWNER' then  
	queryString:='select breeding_id,breeding_buck_id,breeding_doe_id,breeding_date,breeding_village,breeding_aborted_dt,breeding_kids_no,breeding_aborted_no,breeding_lact_start_dt,breeding_lact_end_dt, (select cattle_ear_tag_id from farm.cattle where cattle_id = breeding_buck_id) as buck_ear_tag, (select cattle_ear_tag_id from farm.cattle where cattle_id = breeding_doe_id) as doe_ear_tag,(select cattle_status from farm.cattle where cattle_id = breeding_buck_id) as buck_status,(select cattle_status from farm.cattle where cattle_id = breeding_doe_id) as doe_status,breeding.created_dt from farm.breeding breeding inner join farm.cattle on(cattle_id = breeding_buck_id) inner join farm.farm on (cattle_farm_id = farm_id) inner join farm.farm_owner_link on(farm_owner_link_farm_id = farm_id) inner join usermanagement.owner on(farm_owner_link_owner_id = owner_id) inner join usermanagement.user on(owner_user_id = user_id) where farm_status = true and user_isactive = true  and user_id = '||tmp_user_id||' '||queryStringbreeding||' '||queryStringfarm||'';


elsif UPPER(rolename) = 'VET' then  
	queryString:='select breeding_id,breeding_buck_id,breeding_doe_id,breeding_date,breeding_village,breeding_aborted_dt,breeding_kids_no,breeding_aborted_no,breeding_lact_start_dt,breeding_lact_end_dt, (select cattle_ear_tag_id from farm.cattle where cattle_id = breeding_buck_id) as buck_ear_tag, (select cattle_ear_tag_id from farm.cattle where cattle_id = breeding_doe_id) as doe_ear_tag,(select cattle_status from farm.cattle where cattle_id = breeding_buck_id) as buck_status,(select cattle_status from farm.cattle where cattle_id = breeding_doe_id) as doe_status,breeding.created_dt from  farm.breeding breeding inner join farm.cattle on(cattle_id = breeding_buck_id) inner join farm.farm on (cattle_farm_id = farm_id) inner join farm.farm_vet_link on(farm_vet_link_farm_id = farm_id) inner join usermanagement.vet on(farm_vet_link_vet_id = vet_id) inner join usermanagement.user on(vet_user_id = user_id) where  farm_status = true and user_isactive = true  and user_id = '||tmp_user_id||' '||queryStringbreeding||' '||queryStringfarm||'';

else

	queryString:='select breeding_id,breeding_buck_id,breeding_doe_id,breeding_date,breeding_village,breeding_aborted_dt,breeding_kids_no,breeding_aborted_no,breeding_lact_start_dt,breeding_lact_end_dt, (select cattle_ear_tag_id from farm.cattle where cattle_id = breeding_buck_id) as buck_ear_tag, (select cattle_ear_tag_id from farm.cattle where cattle_id = breeding_doe_id) as doe_ear_tag,(select cattle_status from farm.cattle where cattle_id = breeding_buck_id) as buck_status,(select cattle_status from farm.cattle where cattle_id = breeding_doe_id) as doe_status,breeding.created_dt from farm.breeding breeding inner join farm.cattle on(cattle_id = breeding_buck_id) inner join farm.farm on (cattle_farm_id = farm_id) where farm_status = true  '||queryStringfarm||'';


end if;



	RAISE NOTICE 'select-->> %',	queryString;
	FOR users_rec IN 
		EXECUTE queryString
	LOOP

	RETURN next users_rec;
	END LOOP;
	
	RETURN ;	
END
$$	
LANGUAGE 'plpgsql';


--select * from farm.fn_breeding_sel(1,2,0);



--=======================farm.fn_breeding_sync===========================================================================

CREATE or REPLACE FUNCTION farm.fn_breeding_sync(tmp_breeding_date varchar ,tmp_breeding_village varchar, tmp_breeding_aborted_dt varchar ,tmp_breeding_kids_no  int,tmp_breeding_aborted_no int, tmp_lact_start_dt varchar ,tmp_lact_end_dt varchar ,tmp_updated_dt varchar ,tmp_unique_sync_key varchar, tmp_created_dt varchar , tmp_breeding_buck_ear_tag varchar, tmp_breeding_doe_ear_tag varchar, tmp_gmt_time timestamp ) RETURNS void AS $$
   
    DECLARE
	tmp_doe_id integer; tmp_buck_id integer;queryString varchar; sync_unique_key varchar;
    BEGIN
       select cattle_id into tmp_doe_id from farm.cattle where cattle_ear_tag_id = tmp_breeding_doe_ear_tag;
       select cattle_id into tmp_buck_id from farm.cattle where cattle_ear_tag_id = tmp_breeding_buck_ear_tag;
       select unique_sync_key into sync_unique_key from farm.breeding where unique_sync_key = tmp_unique_sync_key;
if tmp_lact_start_dt = '0000-00-00 00:00:00' then
tmp_lact_start_dt := null;
end if;
if tmp_lact_end_dt = '0000-00-00 00:00:00' then
tmp_lact_end_dt := null;
end if;
if tmp_breeding_aborted_dt = '0000-00-00 00:00:00' then
tmp_breeding_aborted_dt := null;
end if;

if (tmp_created_dt = '0000-00-00 00:00:00') and (tmp_updated_dt != '0000-00-00 00:00:00') then
tmp_created_dt := null; 

 queryString:='update farm.breeding set breeding_buck_id = '||tmp_buck_id||',breeding_doe_id = '||tmp_doe_id||', breeding_date = '||tmp_breeding_date||',breeding_village = '||tmp_breeding_village||',breeding_aborted_dt = '||tmp_breeding_aborted_dt||', breeding_kids_no = '||tmp_breeding_kids_no||', breeding_aborted_no = '||tmp_breeding_aborted_no||', breeding_lact_start_dt = '||tmp_lact_start_dt||',breeding_lact_end_dt = '||tmp_lact_end_dt||',updated_dt = '||tmp_gmt_time||', breeding_buck_ear_tag = '||tmp_breeding_buck_ear_tag||',breeding_doe_ear_tag = '||tmp_breeding_doe_ear_tag||' where unique_sync_key = '||tmp_unique_sync_key||';';
	update farm.breeding set breeding_buck_id = tmp_buck_id,breeding_doe_id = tmp_doe_id, breeding_date = to_timestamp(tmp_breeding_date,'YYYY-MM-DD hh24:mi:ss'),breeding_village = tmp_breeding_village,breeding_aborted_dt = to_timestamp(tmp_breeding_aborted_dt,'YYYY-MM-DD hh24:mi:ss'), breeding_kids_no = tmp_breeding_kids_no, breeding_aborted_no = tmp_breeding_aborted_no, breeding_lact_start_dt = to_timestamp(tmp_lact_start_dt ,'YYYY-MM-DD hh24:mi:ss'),breeding_lact_end_dt = to_timestamp(tmp_lact_end_dt ,'YYYY-MM-DD hh24:mi:ss'),updated_dt = tmp_gmt_time, breeding_buck_ear_tag = tmp_breeding_buck_ear_tag,breeding_doe_ear_tag = tmp_breeding_doe_ear_tag where unique_sync_key = tmp_unique_sync_key;

elsif (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00') or (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt = '0000-00-00 00:00:00') then
if tmp_updated_dt = '0000-00-00 00:00:00' then
tmp_updated_dt := null;
end if;

if sync_unique_key is not null then
update farm.breeding set breeding_buck_id = tmp_buck_id,breeding_doe_id = tmp_doe_id, breeding_date = to_timestamp(tmp_breeding_date,'YYYY-MM-DD hh24:mi:ss'),breeding_village = tmp_breeding_village,breeding_aborted_dt = to_timestamp(tmp_breeding_aborted_dt,'YYYY-MM-DD hh24:mi:ss'), breeding_kids_no = tmp_breeding_kids_no, breeding_aborted_no = tmp_breeding_aborted_no, breeding_lact_start_dt = to_timestamp(tmp_lact_start_dt ,'YYYY-MM-DD hh24:mi:ss'),breeding_lact_end_dt = to_timestamp(tmp_lact_end_dt ,'YYYY-MM-DD hh24:mi:ss'),updated_dt = tmp_gmt_time, breeding_buck_ear_tag = tmp_breeding_buck_ear_tag,breeding_doe_ear_tag = tmp_breeding_doe_ear_tag where unique_sync_key = tmp_unique_sync_key;
elsif sync_unique_key is null then
insert into farm.breeding(breeding_buck_id,breeding_doe_id,breeding_date ,breeding_village , breeding_aborted_dt ,breeding_kids_no ,breeding_aborted_no , breeding_lact_start_dt ,breeding_lact_end_dt ,unique_sync_key , created_dt , breeding_buck_ear_tag , breeding_doe_ear_tag ) values(tmp_buck_id,tmp_doe_id,to_timestamp(tmp_breeding_date,'YYYY-MM-DD hh24:mi:ss'),tmp_breeding_village ,  to_timestamp(tmp_breeding_aborted_dt,'YYYY-MM-DD hh24:mi:ss') ,tmp_breeding_kids_no ,tmp_breeding_aborted_no ,  to_timestamp(tmp_lact_start_dt ,'YYYY-MM-DD hh24:mi:ss'),to_timestamp(tmp_lact_end_dt ,'YYYY-MM-DD hh24:mi:ss') ,tmp_unique_sync_key , tmp_gmt_time , tmp_breeding_buck_ear_tag , tmp_breeding_doe_ear_tag );

end if;
end if;
RAISE NOTICE 'select-->> %',	queryString;
    END;
$$ LANGUAGE plpgsql;


--select * from farm.fn_breeding_sync('2015-05-20 10:10:10','', null,11,0,null,null,'2015-06-10 10:10:10','FARM-74/ET-249FARM-74/ET-248/2015-06-10', null, 'FARM-74/ET-248', 'FARM-74/ET-249','2015-06-10 10:47:05.0');


--===================================fn_milk_production_sync====================================================================

CREATE or REPLACE FUNCTION farm.fn_milk_production_sync(tmp_milk_prod_dt varchar,tmp_milk_prod_qty double precision, tmp_milk_prod_comments text,tmp_updated_dt varchar,tmp_unique_sync_key varchar, tmp_created_dt varchar, tmp_gmt_time timestamp, tmp_doe_ear_tag varchar) RETURNS void AS $$
   
    DECLARE
	tmp_doe_id integer;sync_unique_key  varchar;
    BEGIN
       select cattle_id into tmp_doe_id from farm.cattle where cattle_ear_tag_id = tmp_doe_ear_tag;
 	select unique_sync_key into sync_unique_key from farm.milk_production where unique_sync_key = tmp_unique_sync_key;

if tmp_milk_prod_dt = '0000-00-00 00:00:00' then
tmp_milk_prod_dt := null;
end if;

if tmp_created_dt = '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00'  then
tmp_created_dt := null; 
	update farm.milk_production set milk_prod_dt = to_timestamp(tmp_milk_prod_dt,'YYYY-MM-DD hh24:mi:ss') ,milk_prod_qty = tmp_milk_prod_qty  , milk_prod_comments = tmp_milk_prod_comments ,updated_dt = tmp_gmt_time where unique_sync_key = tmp_unique_sync_key;

elsif (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00') or (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt = '0000-00-00 00:00:00') then
if tmp_updated_dt = '0000-00-00 00:00:00' then
tmp_updated_dt := null;
end if;
if sync_unique_key is not null then 
update farm.milk_production set milk_prod_dt = to_timestamp(tmp_milk_prod_dt,'YYYY-MM-DD hh24:mi:ss') ,milk_prod_qty = tmp_milk_prod_qty  , milk_prod_comments = tmp_milk_prod_comments ,updated_dt = tmp_gmt_time where unique_sync_key = tmp_unique_sync_key;
elsif sync_unique_key is  null then 
insert into farm.milk_production(milk_prod_doe_id,milk_prod_dt,milk_prod_qty,milk_prod_comments,created_dt,unique_sync_key) values(tmp_doe_id,to_timestamp(tmp_milk_prod_dt,'YYYY-MM-DD hh24:mi:ss'),tmp_milk_prod_qty,tmp_milk_prod_comments,tmp_gmt_time,tmp_unique_sync_key) ;

end if;
end if;

    END;
$$ LANGUAGE plpgsql;

--select * from farm.fn_milk_production_sync('2015-02-05 12:12:12','45.23','','2015-02-05 12:12:12','FARM-74/ET-249','2015-02-05 12:12:12','2015-06-10 10:47:05.0');

---===================================fn_breed_kids_sync===========================

CREATE or REPLACE FUNCTION farm.fn_breed_kids_sync(tmp_br_unique_sync_key varchar, tmp_br_kids_nur_lact_dt varchar,tmp_br_kids_weaning_dt varchar,tmp_br_kids_weight double precision, tmp_updated_dt varchar,tmp_unique_sync_key varchar, tmp_created_dt varchar, tmp_gmt_time timestamp) RETURNS void AS $$
   
    DECLARE
	tmp_breeding_id integer; tmp_kid_id integer;sync_unique_key varchar;
    BEGIN
       select breeding_id into tmp_breeding_id from farm.breeding where unique_sync_key = tmp_br_unique_sync_key;
       select cattle_id into tmp_kid_id from farm.cattle where cattle_ear_tag_id = tmp_unique_sync_key;
       select unique_sync_key into sync_unique_key from farm.breed_kids where unique_sync_key = tmp_unique_sync_key;

if tmp_br_kids_nur_lact_dt = '0000-00-00 00:00:00' then
tmp_br_kids_nur_lact_dt := null;
end if;
if tmp_br_kids_weaning_dt = '0000-00-00 00:00:00' then
tmp_br_kids_weaning_dt := null;
end if;

if tmp_created_dt = '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00'  then
tmp_created_dt := null; 
	update farm.breed_kids set breed_kids_breeding_id = tmp_breeding_id,breed_kids_cattle_id = tmp_kid_id ,breed_kids_nur_lact_dt = to_timestamp(tmp_br_kids_nur_lact_dt,'YYYY-MM-DD hh24:mi:ss') ,breed_kids_weaning_dt = to_timestamp(tmp_br_kids_weaning_dt,'YYYY-MM-DD hh24:mi:ss'),breed_kids_weight = tmp_br_kids_weight,updated_dt = tmp_gmt_time where unique_sync_key = tmp_unique_sync_key;

elsif (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00') or (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt = '0000-00-00 00:00:00') then
if tmp_updated_dt = '0000-00-00 00:00:00' then
tmp_updated_dt := null;
end if;
if sync_unique_key is not null then 
update farm.breed_kids set breed_kids_breeding_id = tmp_breeding_id,breed_kids_cattle_id = tmp_kid_id ,breed_kids_nur_lact_dt = to_timestamp(tmp_br_kids_nur_lact_dt,'YYYY-MM-DD hh24:mi:ss') ,breed_kids_weaning_dt = to_timestamp(tmp_br_kids_weaning_dt,'YYYY-MM-DD hh24:mi:ss'),breed_kids_weight = tmp_br_kids_weight,updated_dt = tmp_gmt_time where unique_sync_key = tmp_unique_sync_key;
elsif sync_unique_key is  null then 
insert into farm.breed_kids (breed_kids_breeding_id,breed_kids_cattle_id ,breed_kids_nur_lact_dt,breed_kids_weaning_dt,breed_kids_weight, created_dt,unique_sync_key) values(tmp_breeding_id,tmp_kid_id,to_timestamp(tmp_br_kids_nur_lact_dt,'YYYY-MM-DD hh24:mi:ss') ,to_timestamp(tmp_br_kids_weaning_dt,'YYYY-MM-DD hh24:mi:ss'),tmp_br_kids_weight,tmp_gmt_time,tmp_unique_sync_key) ;

end if;
end if;
    END;
$$ LANGUAGE plpgsql;

--select * from farm.fn_breed_kids_sync('2015-02-05 12:12:12','45.23','','2015-02-05 12:12:12','FARM-74/ET-249','2015-02-05 12:12:12','2015-06-10 10:47:05.0');

--=======================================usermanagement.fn_user_sync==================================================================


CREATE OR REPLACE FUNCTION usermanagement.fn_user_sync (tmp_role_name  varchar, tmp_user_updated_dt  varchar,tmp_user_fname varchar,tmp_user_lname varchar, tmp_user_location varchar,tmp_user_email varchar, tmp_user_address text, tmp_user_phone varchar, tmp_updated_dt varchar, tmp_unique_sync_key varchar, tmp_created_dt varchar, tmp_gmt_time timestamp ) RETURNS void AS $$
DECLARE
	tmp_role_id integer;sync_unique_key varchar;
BEGIN

 select role_id into tmp_role_id from usermanagement.role where UPPER(role_name) = UPPER(tmp_role_name);
 select unique_sync_key into sync_unique_key from usermanagement.user where unique_sync_key = tmp_unique_sync_key;

if sync_unique_key is not null then
tmp_created_dt := null; 
	update usermanagement.user set user_updated_dt = tmp_gmt_time ,user_fname = tmp_user_fname,user_lname = tmp_user_lname, user_location = tmp_user_location,user_email = tmp_user_email, user_address = tmp_user_address, user_phone = tmp_user_phone, unique_sync_key = tmp_unique_sync_key,updated_dt = tmp_gmt_time where unique_sync_key = tmp_unique_sync_key;

end if;

    END;
$$ LANGUAGE plpgsql;

--select * from usermanagement.fn_user_sync('owneruser','2015-05-20 00:00:00','fname','lname','loc','jinesh@gmail.com','address','9895456431','2015-06-22 00:00:00','owneruser','2012-02-12 00:00:00','2015-06-22 00:00:00');


--========================================farm.fn_generate_eartag===============================================================================================

CREATE OR REPLACE FUNCTION farm.fn_generate_eartag (tmp_farm_code varchar)
RETURNS varchar AS 
$$
declare
    	tmp_cattle_ear_tag_id varchar;
	tmp_cattle_id int;
	queryString varchar;
BEGIN

	select nextval('farm.cattle_cattle_id_seq') into tmp_cattle_id;
	tmp_cattle_ear_tag_id:= tmp_farm_code||'/ET-'||tmp_cattle_id ;
	

	RETURN tmp_cattle_ear_tag_id;
	

END;
$$ LANGUAGE plpgsql;

--select * from farm.fn_generate_eartag ('FARM-10');

--========================================farm.fn_cattle_sync===============================================================================================

CREATE OR REPLACE FUNCTION farm.fn_cattle_sync (tmp_cattle_ear_tag_id  varchar, tmp_farm_code  varchar,tmp_cattle_dob  varchar,tmp_cattle_gender  char,tmp_cattle_location  varchar, tmp_cattle_category  char,tmp_cattle_type integer, tmp_cattle_status  boolean,tmp_cattle_buck_ear_tag  varchar,tmp_cattle_doe_ear_tag  varchar,tmp_cattle_created_dt  varchar,tmp_cattle_latt varchar, tmp_cattle_long varchar, tmp_land_mark varchar, tmp_cattle_code varchar, tmp_updated_dt varchar, tmp_unique_sync_key varchar, tmp_created_dt varchar, tmp_gmt_time timestamp)


 RETURNS varchar AS 
$$
declare
	queryString varchar;
	users_rec farm.typ_cattle;
	sync_unique_key varchar;
	tmp_cattle_doe_id integer;
	tmp_cattle_buck_id integer;
	tmp_cattle_farm_id integer;
	tmp_cattle_id integer;
	
BEGIN

select farm_id into tmp_cattle_farm_id from farm.farm where farm_code = tmp_farm_code;
select unique_sync_key into sync_unique_key from farm.cattle where unique_sync_key = tmp_unique_sync_key;

if tmp_cattle_doe_ear_tag != 'null' then
select cattle_id into tmp_cattle_doe_id from farm.cattle where cattle_ear_tag_id = tmp_cattle_doe_ear_tag;
end if;
if tmp_cattle_buck_ear_tag != 'null' then
select cattle_id into tmp_cattle_buck_id from farm.cattle where cattle_ear_tag_id = tmp_cattle_buck_ear_tag;
end if;
if tmp_cattle_latt = 'null' then
tmp_cattle_latt := null;
end if;
if tmp_cattle_long = 'null' then
tmp_cattle_long := null;
end if;
if tmp_land_mark = 'null' then
tmp_land_mark := null;
end if;

if tmp_created_dt = '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00'  then
tmp_created_dt := null; 
update farm.cattle set  cattle_location = tmp_cattle_location,cattle_farm_id = tmp_cattle_farm_id,cattle_dob = to_date(tmp_cattle_dob,'YYYY-MM-DD'), cattle_gender = tmp_cattle_gender , cattle_category = tmp_cattle_category,cattle_status = tmp_cattle_status,cattle_buck_id = tmp_cattle_buck_id,cattle_doe_id = tmp_cattle_doe_id, cattle_latt = tmp_cattle_latt, cattle_long = tmp_cattle_long , cattle_land_mark = tmp_land_mark, cattle_code = tmp_cattle_code, updated_dt = tmp_gmt_time  where unique_sync_key = tmp_unique_sync_key; 

elsif (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt != '0000-00-00 00:00:00') or (tmp_created_dt != '0000-00-00 00:00:00' and tmp_updated_dt = '0000-00-00 00:00:00') then
if tmp_updated_dt = '0000-00-00 00:00:00' then
tmp_updated_dt := null;
end if;

if sync_unique_key is not null then
	update farm.cattle set  cattle_location = tmp_cattle_location,cattle_farm_id = tmp_cattle_farm_id,cattle_dob = to_date(tmp_cattle_dob,'YYYY-MM-DD'), cattle_gender = tmp_cattle_gender , cattle_category = tmp_cattle_category,cattle_status = tmp_cattle_status,cattle_buck_id = tmp_cattle_buck_id,cattle_doe_id = tmp_cattle_doe_id, cattle_latt = tmp_cattle_latt, cattle_long = tmp_cattle_long , cattle_land_mark = tmp_land_mark, updated_dt = tmp_gmt_time, cattle_code = tmp_cattle_code where unique_sync_key = tmp_unique_sync_key;
RAISE NOTICE 'sync_unique_key update--->> %',	sync_unique_key;
elsif sync_unique_key is null then
RAISE NOTICE 'sync_unique_key insert--->> %',	sync_unique_key;
	select nextval('farm.cattle_cattle_id_seq') into tmp_cattle_id;
  	insert into farm.cattle(cattle_id, cattle_ear_tag_id, cattle_location, cattle_farm_id, cattle_dob , cattle_gender, cattle_category,cattle_status,cattle_buck_id,cattle_doe_id,cattle_created_dt,cattle_latt,cattle_long,cattle_land_mark,unique_sync_key,created_dt,cattle_code) values(tmp_cattle_id, tmp_cattle_ear_tag_id, tmp_cattle_location,tmp_cattle_farm_id,to_date(tmp_cattle_dob,'YYYY-MM-DD'),tmp_cattle_gender, tmp_cattle_category,tmp_cattle_status,tmp_cattle_buck_id,tmp_cattle_doe_id,to_timestamp(tmp_cattle_created_dt,'YYYY-MM-DD hh24:mi:ss'),tmp_cattle_latt, tmp_cattle_long , tmp_land_mark,tmp_cattle_ear_tag_id,tmp_gmt_time,tmp_cattle_code);

end if;
end if;

	RETURN tmp_cattle_ear_tag_id;

END;
$$ LANGUAGE plpgsql;


--=====================fn_create_cattles(tmp_farm_count integer, tmp_cattle_count integer)======================================

CREATE OR REPLACE FUNCTION fn_create_cattles (tmp_farm_count integer, tmp_cattle_count integer)
RETURNS varchar AS 
$$
declare
    	queryString varchar; tmp_cattle_id int;k int; j int;tmp_farm_id int; dob_tm timestamp;tmp_gender char;
BEGIN

--farm insertion
j := 0;
FOR m IN 1..tmp_farm_count LOOP
   select count(*) into tmp_farm_id from farm.fn_farm_save (0,'FARM -'||m,'ADDRESS'||m,'LOCATION'||m,'now()',true,'now()');
j := m;
END LOOP;

--cattle insertion
k := 0;dob_tm := now();tmp_gender := 'M';
FOR i IN 1..tmp_cattle_count LOOP
   select count(*) into tmp_cattle_id from farm.fn_cattle_save (0, 1,dob_tm,tmp_gender,'US', 'A',true, 0 ,0,'now()','1245878.1556','24578955.2564','Near Central School'||i,'now()','CODE_0'||i);
k := i;dob_tm := now() - interval '1 day';

if tmp_gender = 'M' then
tmp_gender := 'F';
elsif tmp_gender = 'F' then
tmp_gender := 'M';
end if;


END LOOP;
RETURN 'No Of farms Saved---->>'||j||' No of cattle Saved----->'||k;		
END;
$$ LANGUAGE plpgsql;

--select * from fn_create_cattles (2,2);

--===================================fn_reset_seq()===================================================

CREATE OR REPLACE FUNCTION fn_reset_seq()
RETURNS integer AS 
$$
declare
    	k int; r RECORD;queryString varchar;
BEGIN

delete from farm.breed_kids;
delete from farm.breeding;

delete from farm.cattle_image;
delete from farm.vacc_hc_link;
delete from farm.healthcare;

delete from farm.milk_production;
delete from farm.surg_ill_det;
delete from farm.surgery_illness;
delete from farm.cattle;

delete from farm.farm_vet_link;
delete from farm.farm_owner_link;
delete from farm.farm;
delete from master.surgery;
delete from master.vaccination;
delete from usermanagement.owner_desk;
delete from usermanagement.owner;
delete from usermanagement.vet;
delete from usermanagement.user where user_name not in('adminuser','defaultowner');
RAISE NOTICE 'PRINT-->> %',	' Database Cleared';

k := 0;
FOR r IN select  n.nspname||'.'|| s.relname as seq
-- a.attname as col 
-- n.nspname as sch,
 
from pg_class s
  join pg_depend d on d.objid=s.oid and d.classid='pg_class'::regclass and d.refclassid='pg_class'::regclass
  join pg_class t on t.oid=d.refobjid
  join pg_namespace n on n.oid=t.relnamespace
  join pg_attribute a on a.attrelid=t.oid and a.attnum=d.refobjsubid
where s.relkind='S' and d.deptype='a' LOOP
RAISE NOTICE 'PRINT-->> %',	r.seq;
if r.seq = 'usermanagement.user_user_id_seq' then
queryString:= 'ALTER SEQUENCE '||r.seq||' RESTART WITH 3;';
elsif  r.seq = 'usermanagement.role_role_id_seq' then
queryString:= 'ALTER SEQUENCE '||r.seq||' RESTART WITH 4;';
else
queryString:= 'ALTER SEQUENCE '||r.seq||' RESTART WITH 1;';
end if;
EXECUTE queryString;    
k := k+1;
END LOOP;
RETURN k;	
END;
$$ LANGUAGE plpgsql;

--select * from fn_reset_seq();

--===================================================================================
