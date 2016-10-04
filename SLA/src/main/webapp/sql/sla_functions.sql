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

--======================================= hospital.typ_doct_qual_link==================================================================



CREATE TYPE hospital.typ_doct_qual_link AS(
	fki_doctor_id bigint,     
	uvc_qualif_name varchar, 
	t_description varchar
);



--====================================== usermanagement.fn_doct_qual_sel================================================================


CREATE OR REPLACE FUNCTION hospital.fn_doct_qual_sel(tmp_fki_doctor_id in bigint,tmp_uvc_qualif_name in varchar)
	RETURNS SETOF hospital.typ_doct_qual_link AS
	$$

	DECLARE
		queryString varchar;
		users_rec hospital.typ_doct_qual_link;

	BEGIN
		if tmp_fki_doctor_id = 0 then
			queryString:='select fki_doctor_id,uvc_qualif_name,t_description
    		from hospital.tbl_doctor_qualif_link inner join hospital.tbl_doctor_qualif_master
    		on(pki_doctor_qualif_master_id = fki_doctor_qualif_master_id) where UPPER(uvc_qualif_name) =  UPPER('''||tmp_uvc_qualif_name||''')';
		elsif tmp_uvc_qualif_name is not null then
			queryString:='select fki_doctor_id,uvc_qualif_name,t_description
    		from hospital.tbl_doctor_qualif_link inner join hospital.tbl_doctor_qualif_master
    		on(pki_doctor_qualif_master_id = fki_doctor_qualif_master_id) where fki_doctor_id = '||tmp_fki_doctor_id;
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

--select * from hospital.fn_doct_qual_sel(0,'MBBS');

