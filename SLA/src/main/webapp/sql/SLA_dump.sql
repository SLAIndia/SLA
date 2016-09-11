--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: hospital; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA hospital;


ALTER SCHEMA hospital OWNER TO postgres;

--
-- Name: restaurant; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA restaurant;


ALTER SCHEMA restaurant OWNER TO postgres;

--
-- Name: usermanagement; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA usermanagement;


ALTER SCHEMA usermanagement OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = hospital, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: tbl_appnt_setting; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_appnt_setting (
    pki_appnt_setting_id integer NOT NULL,
    fki_hospital_id bigint,
    fki_doctor_id bigint,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    i_no_of_appnts_per_day integer DEFAULT 0 NOT NULL,
    fki_hos_dept_type_id integer NOT NULL,
    t_description text,
    vc_time_slot character varying(100) NOT NULL
);


ALTER TABLE hospital.tbl_appnt_setting OWNER TO postgres;

--
-- Name: tbl_appnt_setting_det; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_appnt_setting_det (
    pki_appnt_setting_det_id integer NOT NULL,
    fki_appnt_setting_id bigint,
    vc_day character varying(75) NOT NULL,
    vc_time_slot character varying(500) NOT NULL,
    t_description text
);


ALTER TABLE hospital.tbl_appnt_setting_det OWNER TO postgres;

--
-- Name: tbl_appnt_setting_det_pki_appnt_setting_det_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_appnt_setting_det_pki_appnt_setting_det_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_appnt_setting_det_pki_appnt_setting_det_id_seq OWNER TO postgres;

--
-- Name: tbl_appnt_setting_det_pki_appnt_setting_det_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_appnt_setting_det_pki_appnt_setting_det_id_seq OWNED BY tbl_appnt_setting_det.pki_appnt_setting_det_id;


--
-- Name: tbl_appnt_setting_pki_appnt_setting_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_appnt_setting_pki_appnt_setting_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_appnt_setting_pki_appnt_setting_id_seq OWNER TO postgres;

--
-- Name: tbl_appnt_setting_pki_appnt_setting_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_appnt_setting_pki_appnt_setting_id_seq OWNED BY tbl_appnt_setting.pki_appnt_setting_id;


--
-- Name: tbl_doctor_qualif_link; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_doctor_qualif_link (
    pki_doctor_qualif_link_id integer NOT NULL,
    fki_doctor_qualif_master_id bigint,
    fki_doctor_id bigint
);


ALTER TABLE hospital.tbl_doctor_qualif_link OWNER TO postgres;

--
-- Name: tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq OWNER TO postgres;

--
-- Name: tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq OWNED BY tbl_doctor_qualif_link.pki_doctor_qualif_link_id;


--
-- Name: tbl_doctor_qualif_master; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_doctor_qualif_master (
    pki_doctor_qualif_master_id integer NOT NULL,
    uvc_qualif_name character varying(100),
    t_description text
);


ALTER TABLE hospital.tbl_doctor_qualif_master OWNER TO postgres;

--
-- Name: tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq OWNER TO postgres;

--
-- Name: tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq OWNED BY tbl_doctor_qualif_master.pki_doctor_qualif_master_id;


--
-- Name: tbl_document; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_document (
    pki_document_id integer NOT NULL,
    vc_document_name character varying(100) NOT NULL,
    fki_document_type_id bigint NOT NULL,
    vc_document_url character varying(100),
    fki_user_id bigint NOT NULL,
    t_text text
);


ALTER TABLE hospital.tbl_document OWNER TO postgres;

--
-- Name: tbl_document_pki_document_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_document_pki_document_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_document_pki_document_id_seq OWNER TO postgres;

--
-- Name: tbl_document_pki_document_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_document_pki_document_id_seq OWNED BY tbl_document.pki_document_id;


--
-- Name: tbl_document_type; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_document_type (
    pki_document_type_id integer NOT NULL,
    vc_document_type_name character varying(100) NOT NULL
);


ALTER TABLE hospital.tbl_document_type OWNER TO postgres;

--
-- Name: tbl_document_type_pki_document_type_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_document_type_pki_document_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_document_type_pki_document_type_id_seq OWNER TO postgres;

--
-- Name: tbl_document_type_pki_document_type_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_document_type_pki_document_type_id_seq OWNED BY tbl_document_type.pki_document_type_id;


--
-- Name: tbl_hos_dept_type; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_hos_dept_type (
    pki_hos_dept_type_id integer NOT NULL,
    vc_hos_dept_type_name character varying(100) NOT NULL,
    fki_parent_dept_type_id bigint NOT NULL
);


ALTER TABLE hospital.tbl_hos_dept_type OWNER TO postgres;

--
-- Name: tbl_hos_dept_type_pki_hos_dept_type_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_hos_dept_type_pki_hos_dept_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_hos_dept_type_pki_hos_dept_type_id_seq OWNER TO postgres;

--
-- Name: tbl_hos_dept_type_pki_hos_dept_type_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_hos_dept_type_pki_hos_dept_type_id_seq OWNED BY tbl_hos_dept_type.pki_hos_dept_type_id;


--
-- Name: tbl_hos_notification; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_hos_notification (
    pki_hos_notification_id integer NOT NULL,
    fki_hospital_id bigint,
    fki_doctor_id bigint NOT NULL,
    dt_from_date timestamp without time zone NOT NULL,
    dt_to_date timestamp without time zone NOT NULL,
    fki_hos_dept_type_id bigint NOT NULL,
    t_description text
);


ALTER TABLE hospital.tbl_hos_notification OWNER TO postgres;

--
-- Name: tbl_hos_notification_pki_hos_notification_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_hos_notification_pki_hos_notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_hos_notification_pki_hos_notification_id_seq OWNER TO postgres;

--
-- Name: tbl_hos_notification_pki_hos_notification_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_hos_notification_pki_hos_notification_id_seq OWNED BY tbl_hos_notification.pki_hos_notification_id;


--
-- Name: tbl_hos_post_consultation; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_hos_post_consultation (
    pki_hos_post_consultation_id integer NOT NULL,
    fki_patient_id bigint,
    fki_doctor_id bigint NOT NULL,
    fki_hospital_id bigint,
    fki_patient_appnt_id bigint NOT NULL,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    dt_next_consultation_date timestamp without time zone NOT NULL,
    vc_medicines_prescribed text,
    t_description text
);


ALTER TABLE hospital.tbl_hos_post_consultation OWNER TO postgres;

--
-- Name: tbl_hos_post_consultation_pki_hos_post_consultation_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_hos_post_consultation_pki_hos_post_consultation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_hos_post_consultation_pki_hos_post_consultation_id_seq OWNER TO postgres;

--
-- Name: tbl_hos_post_consultation_pki_hos_post_consultation_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_hos_post_consultation_pki_hos_post_consultation_id_seq OWNED BY tbl_hos_post_consultation.pki_hos_post_consultation_id;


--
-- Name: tbl_hos_test_report; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_hos_test_report (
    pki_hos_test_report_id integer NOT NULL,
    vc_hos_test_report_name character varying(100) NOT NULL,
    fki_user_id bigint NOT NULL,
    dt_date timestamp without time zone NOT NULL,
    vc_hos_test_report_code character varying(100),
    vc_op_number character varying(100),
    fki_patient_appnt_id bigint NOT NULL,
    fki_parent_dept_type_id bigint NOT NULL
);


ALTER TABLE hospital.tbl_hos_test_report OWNER TO postgres;

--
-- Name: tbl_hos_test_report_pki_hos_test_report_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_hos_test_report_pki_hos_test_report_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_hos_test_report_pki_hos_test_report_id_seq OWNER TO postgres;

--
-- Name: tbl_hos_test_report_pki_hos_test_report_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_hos_test_report_pki_hos_test_report_id_seq OWNED BY tbl_hos_test_report.pki_hos_test_report_id;


--
-- Name: tbl_patient_appnt; Type: TABLE; Schema: hospital; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_patient_appnt (
    pki_patient_appnt_id integer NOT NULL,
    vc_token_number character varying(100) NOT NULL,
    i_confirmation_status integer DEFAULT 0 NOT NULL,
    fki_hospital_id bigint,
    fki_doctor_id bigint NOT NULL,
    fki_patient_id bigint NOT NULL,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    vc_op_number character varying(100),
    fki_hos_dept_type_id bigint NOT NULL,
    i_appnt_type integer DEFAULT 1 NOT NULL,
    dt_appnt_time timestamp without time zone,
    vc_reference_name character varying(75),
    fki_reference_patient bigint,
    vc_description character varying(200),
    i_consultation_type integer DEFAULT 1 NOT NULL,
    i_consession_type integer DEFAULT 1 NOT NULL
);


ALTER TABLE hospital.tbl_patient_appnt OWNER TO postgres;

--
-- Name: tbl_patient_appnt_pki_patient_appnt_id_seq; Type: SEQUENCE; Schema: hospital; Owner: postgres
--

CREATE SEQUENCE tbl_patient_appnt_pki_patient_appnt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hospital.tbl_patient_appnt_pki_patient_appnt_id_seq OWNER TO postgres;

--
-- Name: tbl_patient_appnt_pki_patient_appnt_id_seq; Type: SEQUENCE OWNED BY; Schema: hospital; Owner: postgres
--

ALTER SEQUENCE tbl_patient_appnt_pki_patient_appnt_id_seq OWNED BY tbl_patient_appnt.pki_patient_appnt_id;


SET search_path = public, pg_catalog;

--
-- Name: tbl_alerts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_alerts (
    pki_alert_id integer NOT NULL,
    fki_from_user_id bigint NOT NULL,
    fki_from_user_type_id bigint NOT NULL,
    fki_to_user_id bigint NOT NULL,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    vc_token_number character varying(100) NOT NULL,
    i_alert_type integer DEFAULT 0 NOT NULL,
    t_description text
);


ALTER TABLE public.tbl_alerts OWNER TO postgres;

--
-- Name: tbl_alerts_pki_alert_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_alerts_pki_alert_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_alerts_pki_alert_id_seq OWNER TO postgres;

--
-- Name: tbl_alerts_pki_alert_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_alerts_pki_alert_id_seq OWNED BY tbl_alerts.pki_alert_id;


--
-- Name: tbl_country; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_country (
    pki_country_id integer NOT NULL,
    vc_country_name character varying(100) NOT NULL,
    vc_country_code character varying(50) NOT NULL
);


ALTER TABLE public.tbl_country OWNER TO postgres;

--
-- Name: tbl_country_pki_country_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_country_pki_country_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_country_pki_country_id_seq OWNER TO postgres;

--
-- Name: tbl_country_pki_country_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_country_pki_country_id_seq OWNED BY tbl_country.pki_country_id;


--
-- Name: tbl_favourites; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_favourites (
    pki_favourite_id integer NOT NULL,
    fki_provider_user_id bigint NOT NULL,
    fki_provider_user_type_id bigint NOT NULL,
    fki_consumer_user_id bigint NOT NULL,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    t_description text
);


ALTER TABLE public.tbl_favourites OWNER TO postgres;

--
-- Name: tbl_favourites_pki_favourite_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_favourites_pki_favourite_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_favourites_pki_favourite_id_seq OWNER TO postgres;

--
-- Name: tbl_favourites_pki_favourite_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_favourites_pki_favourite_id_seq OWNED BY tbl_favourites.pki_favourite_id;


--
-- Name: tbl_images; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_images (
    pki_image_id integer NOT NULL,
    vc_image_url character varying(100) NOT NULL,
    b_is_primary boolean,
    i_image_order integer,
    fki_user_id bigint NOT NULL
);


ALTER TABLE public.tbl_images OWNER TO postgres;

--
-- Name: tbl_images_pki_image_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_images_pki_image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_images_pki_image_id_seq OWNER TO postgres;

--
-- Name: tbl_images_pki_image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_images_pki_image_id_seq OWNED BY tbl_images.pki_image_id;


--
-- Name: tbl_state; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_state (
    pki_state_id integer NOT NULL,
    vc_state_name character varying(100) NOT NULL,
    vc_state_code character varying(50) NOT NULL
);


ALTER TABLE public.tbl_state OWNER TO postgres;

--
-- Name: tbl_state_pki_state_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_state_pki_state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_state_pki_state_id_seq OWNER TO postgres;

--
-- Name: tbl_state_pki_state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_state_pki_state_id_seq OWNED BY tbl_state.pki_state_id;


--
-- Name: tbl_trust_stories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_trust_stories (
    pki_trust_stories_id integer NOT NULL,
    fki_provider_parent_id bigint,
    fki_provider_id bigint NOT NULL,
    fki_consumer_id bigint,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    t_description text,
    i_rating integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.tbl_trust_stories OWNER TO postgres;

--
-- Name: tbl_trust_stories_pki_trust_stories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_trust_stories_pki_trust_stories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_trust_stories_pki_trust_stories_id_seq OWNER TO postgres;

--
-- Name: tbl_trust_stories_pki_trust_stories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_trust_stories_pki_trust_stories_id_seq OWNED BY tbl_trust_stories.pki_trust_stories_id;


SET search_path = restaurant, pg_catalog;

--
-- Name: tbl_rest_food_category; Type: TABLE; Schema: restaurant; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_rest_food_category (
    pki_rest_food_category_id integer NOT NULL,
    vc_rest_food_category_name character varying(100) NOT NULL,
    t_description text,
    i_rating integer DEFAULT 0
);


ALTER TABLE restaurant.tbl_rest_food_category OWNER TO postgres;

--
-- Name: tbl_rest_food_category_pki_rest_food_category_id_seq; Type: SEQUENCE; Schema: restaurant; Owner: postgres
--

CREATE SEQUENCE tbl_rest_food_category_pki_rest_food_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurant.tbl_rest_food_category_pki_rest_food_category_id_seq OWNER TO postgres;

--
-- Name: tbl_rest_food_category_pki_rest_food_category_id_seq; Type: SEQUENCE OWNED BY; Schema: restaurant; Owner: postgres
--

ALTER SEQUENCE tbl_rest_food_category_pki_rest_food_category_id_seq OWNED BY tbl_rest_food_category.pki_rest_food_category_id;


--
-- Name: tbl_rest_food_types; Type: TABLE; Schema: restaurant; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_rest_food_types (
    pki_rest_food_types_id integer NOT NULL,
    vc_food_type_name character varying(100) NOT NULL,
    fki_rest_food_category_id bigint,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    t_description text,
    i_rating integer DEFAULT 0
);


ALTER TABLE restaurant.tbl_rest_food_types OWNER TO postgres;

--
-- Name: tbl_rest_food_types_pki_rest_food_types_id_seq; Type: SEQUENCE; Schema: restaurant; Owner: postgres
--

CREATE SEQUENCE tbl_rest_food_types_pki_rest_food_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurant.tbl_rest_food_types_pki_rest_food_types_id_seq OWNER TO postgres;

--
-- Name: tbl_rest_food_types_pki_rest_food_types_id_seq; Type: SEQUENCE OWNED BY; Schema: restaurant; Owner: postgres
--

ALTER SEQUENCE tbl_rest_food_types_pki_rest_food_types_id_seq OWNED BY tbl_rest_food_types.pki_rest_food_types_id;


--
-- Name: tbl_rest_order; Type: TABLE; Schema: restaurant; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_rest_order (
    pki_rest_order_id integer NOT NULL,
    fki_rest_id bigint NOT NULL,
    fki_user_id bigint NOT NULL,
    dt_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    dt_delivery_time timestamp without time zone,
    vc_token_number character varying(100) NOT NULL,
    fki_rest_food_types_id bigint NOT NULL,
    i_confirmation_status integer DEFAULT 0 NOT NULL,
    vc_reference_name character varying(75),
    fki_reference_user bigint,
    vc_order_status character varying(200),
    vc_delivery_boy_name character varying(100)
);


ALTER TABLE restaurant.tbl_rest_order OWNER TO postgres;

--
-- Name: tbl_rest_order_pki_rest_order_id_seq; Type: SEQUENCE; Schema: restaurant; Owner: postgres
--

CREATE SEQUENCE tbl_rest_order_pki_rest_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurant.tbl_rest_order_pki_rest_order_id_seq OWNER TO postgres;

--
-- Name: tbl_rest_order_pki_rest_order_id_seq; Type: SEQUENCE OWNED BY; Schema: restaurant; Owner: postgres
--

ALTER SEQUENCE tbl_rest_order_pki_rest_order_id_seq OWNED BY tbl_rest_order.pki_rest_order_id;


SET search_path = usermanagement, pg_catalog;

--
-- Name: tbl_address; Type: TABLE; Schema: usermanagement; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_address (
    pki_address_id integer NOT NULL,
    fki_user_id integer NOT NULL,
    vc_location character varying(75) NOT NULL,
    vc_location_lattitude character varying(75) NOT NULL,
    vc_location_longitude character varying(75) NOT NULL,
    i_location_buffer integer,
    vc_pin_code character varying(50),
    i_is_primary integer NOT NULL
);


ALTER TABLE usermanagement.tbl_address OWNER TO postgres;

--
-- Name: tbl_address_pki_address_id_seq; Type: SEQUENCE; Schema: usermanagement; Owner: postgres
--

CREATE SEQUENCE tbl_address_pki_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermanagement.tbl_address_pki_address_id_seq OWNER TO postgres;

--
-- Name: tbl_address_pki_address_id_seq; Type: SEQUENCE OWNED BY; Schema: usermanagement; Owner: postgres
--

ALTER SEQUENCE tbl_address_pki_address_id_seq OWNED BY tbl_address.pki_address_id;


--
-- Name: tbl_role; Type: TABLE; Schema: usermanagement; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_role (
    pki_role_id integer NOT NULL,
    uvc_role_name character varying(50) NOT NULL
);


ALTER TABLE usermanagement.tbl_role OWNER TO postgres;

--
-- Name: tbl_role_pki_role_id_seq; Type: SEQUENCE; Schema: usermanagement; Owner: postgres
--

CREATE SEQUENCE tbl_role_pki_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermanagement.tbl_role_pki_role_id_seq OWNER TO postgres;

--
-- Name: tbl_role_pki_role_id_seq; Type: SEQUENCE OWNED BY; Schema: usermanagement; Owner: postgres
--

ALTER SEQUENCE tbl_role_pki_role_id_seq OWNED BY tbl_role.pki_role_id;


--
-- Name: tbl_subscription; Type: TABLE; Schema: usermanagement; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_subscription (
    pki_sub_id integer NOT NULL,
    fki_user_id integer NOT NULL,
    dt_from_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    dt_to_date timestamp without time zone,
    i_status integer DEFAULT 1 NOT NULL,
    vc_description character varying(100),
    dt_created_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    dt_updated_dt timestamp without time zone
);


ALTER TABLE usermanagement.tbl_subscription OWNER TO postgres;

--
-- Name: tbl_subscription_pki_sub_id_seq; Type: SEQUENCE; Schema: usermanagement; Owner: postgres
--

CREATE SEQUENCE tbl_subscription_pki_sub_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermanagement.tbl_subscription_pki_sub_id_seq OWNER TO postgres;

--
-- Name: tbl_subscription_pki_sub_id_seq; Type: SEQUENCE OWNED BY; Schema: usermanagement; Owner: postgres
--

ALTER SEQUENCE tbl_subscription_pki_sub_id_seq OWNED BY tbl_subscription.pki_sub_id;


--
-- Name: tbl_user; Type: TABLE; Schema: usermanagement; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_user (
    pki_user_id integer NOT NULL,
    fki_role_id integer NOT NULL,
    fki_user_type_id integer NOT NULL,
    fki_parent_user_id integer,
    uvc_username character varying(50) NOT NULL,
    vc_password character varying(75) NOT NULL,
    i_user_status integer DEFAULT 0 NOT NULL,
    dt_created_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    dt_updated_dt timestamp without time zone
);


ALTER TABLE usermanagement.tbl_user OWNER TO postgres;

--
-- Name: tbl_user_details; Type: TABLE; Schema: usermanagement; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_user_details (
    pki_user_det_id integer NOT NULL,
    fki_user_id integer NOT NULL,
    vc_f_name character varying(50) NOT NULL,
    vc_l_name character varying(50) NOT NULL,
    uvc_p_email_address character varying(75) NOT NULL,
    vc_s_email_address character varying(75),
    vc_device_token character varying(100),
    i_device_type integer DEFAULT 0 NOT NULL,
    vc_phone_1 character varying(75) NOT NULL,
    vc_phone_2 character varying(75),
    vc_phone_3 character varying(75),
    dt_created_date timestamp without time zone DEFAULT timezone('utc'::text, now()),
    dt_updated_dt timestamp without time zone,
    fki_country_id bigint
);


ALTER TABLE usermanagement.tbl_user_details OWNER TO postgres;

--
-- Name: tbl_user_details_pki_user_det_id_seq; Type: SEQUENCE; Schema: usermanagement; Owner: postgres
--

CREATE SEQUENCE tbl_user_details_pki_user_det_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermanagement.tbl_user_details_pki_user_det_id_seq OWNER TO postgres;

--
-- Name: tbl_user_details_pki_user_det_id_seq; Type: SEQUENCE OWNED BY; Schema: usermanagement; Owner: postgres
--

ALTER SEQUENCE tbl_user_details_pki_user_det_id_seq OWNED BY tbl_user_details.pki_user_det_id;


--
-- Name: tbl_user_pki_user_id_seq; Type: SEQUENCE; Schema: usermanagement; Owner: postgres
--

CREATE SEQUENCE tbl_user_pki_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermanagement.tbl_user_pki_user_id_seq OWNER TO postgres;

--
-- Name: tbl_user_pki_user_id_seq; Type: SEQUENCE OWNED BY; Schema: usermanagement; Owner: postgres
--

ALTER SEQUENCE tbl_user_pki_user_id_seq OWNED BY tbl_user.pki_user_id;


--
-- Name: tbl_user_type; Type: TABLE; Schema: usermanagement; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_user_type (
    pki_user_type_id integer NOT NULL,
    uvc_user_type_name character varying(50) NOT NULL
);


ALTER TABLE usermanagement.tbl_user_type OWNER TO postgres;

--
-- Name: tbl_user_type_pki_user_type_id_seq; Type: SEQUENCE; Schema: usermanagement; Owner: postgres
--

CREATE SEQUENCE tbl_user_type_pki_user_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermanagement.tbl_user_type_pki_user_type_id_seq OWNER TO postgres;

--
-- Name: tbl_user_type_pki_user_type_id_seq; Type: SEQUENCE OWNED BY; Schema: usermanagement; Owner: postgres
--

ALTER SEQUENCE tbl_user_type_pki_user_type_id_seq OWNED BY tbl_user_type.pki_user_type_id;


SET search_path = hospital, pg_catalog;

--
-- Name: pki_appnt_setting_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_appnt_setting ALTER COLUMN pki_appnt_setting_id SET DEFAULT nextval('tbl_appnt_setting_pki_appnt_setting_id_seq'::regclass);


--
-- Name: pki_appnt_setting_det_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_appnt_setting_det ALTER COLUMN pki_appnt_setting_det_id SET DEFAULT nextval('tbl_appnt_setting_det_pki_appnt_setting_det_id_seq'::regclass);


--
-- Name: pki_doctor_qualif_link_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_doctor_qualif_link ALTER COLUMN pki_doctor_qualif_link_id SET DEFAULT nextval('tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq'::regclass);


--
-- Name: pki_doctor_qualif_master_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_doctor_qualif_master ALTER COLUMN pki_doctor_qualif_master_id SET DEFAULT nextval('tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq'::regclass);


--
-- Name: pki_document_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_document ALTER COLUMN pki_document_id SET DEFAULT nextval('tbl_document_pki_document_id_seq'::regclass);


--
-- Name: pki_document_type_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_document_type ALTER COLUMN pki_document_type_id SET DEFAULT nextval('tbl_document_type_pki_document_type_id_seq'::regclass);


--
-- Name: pki_hos_dept_type_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_dept_type ALTER COLUMN pki_hos_dept_type_id SET DEFAULT nextval('tbl_hos_dept_type_pki_hos_dept_type_id_seq'::regclass);


--
-- Name: pki_hos_notification_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_notification ALTER COLUMN pki_hos_notification_id SET DEFAULT nextval('tbl_hos_notification_pki_hos_notification_id_seq'::regclass);


--
-- Name: pki_hos_post_consultation_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_post_consultation ALTER COLUMN pki_hos_post_consultation_id SET DEFAULT nextval('tbl_hos_post_consultation_pki_hos_post_consultation_id_seq'::regclass);


--
-- Name: pki_hos_test_report_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_test_report ALTER COLUMN pki_hos_test_report_id SET DEFAULT nextval('tbl_hos_test_report_pki_hos_test_report_id_seq'::regclass);


--
-- Name: pki_patient_appnt_id; Type: DEFAULT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_patient_appnt ALTER COLUMN pki_patient_appnt_id SET DEFAULT nextval('tbl_patient_appnt_pki_patient_appnt_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- Name: pki_alert_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_alerts ALTER COLUMN pki_alert_id SET DEFAULT nextval('tbl_alerts_pki_alert_id_seq'::regclass);


--
-- Name: pki_country_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_country ALTER COLUMN pki_country_id SET DEFAULT nextval('tbl_country_pki_country_id_seq'::regclass);


--
-- Name: pki_favourite_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_favourites ALTER COLUMN pki_favourite_id SET DEFAULT nextval('tbl_favourites_pki_favourite_id_seq'::regclass);


--
-- Name: pki_image_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_images ALTER COLUMN pki_image_id SET DEFAULT nextval('tbl_images_pki_image_id_seq'::regclass);


--
-- Name: pki_state_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_state ALTER COLUMN pki_state_id SET DEFAULT nextval('tbl_state_pki_state_id_seq'::regclass);


--
-- Name: pki_trust_stories_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_trust_stories ALTER COLUMN pki_trust_stories_id SET DEFAULT nextval('tbl_trust_stories_pki_trust_stories_id_seq'::regclass);


SET search_path = restaurant, pg_catalog;

--
-- Name: pki_rest_food_category_id; Type: DEFAULT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_food_category ALTER COLUMN pki_rest_food_category_id SET DEFAULT nextval('tbl_rest_food_category_pki_rest_food_category_id_seq'::regclass);


--
-- Name: pki_rest_food_types_id; Type: DEFAULT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_food_types ALTER COLUMN pki_rest_food_types_id SET DEFAULT nextval('tbl_rest_food_types_pki_rest_food_types_id_seq'::regclass);


--
-- Name: pki_rest_order_id; Type: DEFAULT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_order ALTER COLUMN pki_rest_order_id SET DEFAULT nextval('tbl_rest_order_pki_rest_order_id_seq'::regclass);


SET search_path = usermanagement, pg_catalog;

--
-- Name: pki_address_id; Type: DEFAULT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_address ALTER COLUMN pki_address_id SET DEFAULT nextval('tbl_address_pki_address_id_seq'::regclass);


--
-- Name: pki_role_id; Type: DEFAULT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_role ALTER COLUMN pki_role_id SET DEFAULT nextval('tbl_role_pki_role_id_seq'::regclass);


--
-- Name: pki_sub_id; Type: DEFAULT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_subscription ALTER COLUMN pki_sub_id SET DEFAULT nextval('tbl_subscription_pki_sub_id_seq'::regclass);


--
-- Name: pki_user_id; Type: DEFAULT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user ALTER COLUMN pki_user_id SET DEFAULT nextval('tbl_user_pki_user_id_seq'::regclass);


--
-- Name: pki_user_det_id; Type: DEFAULT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user_details ALTER COLUMN pki_user_det_id SET DEFAULT nextval('tbl_user_details_pki_user_det_id_seq'::regclass);


--
-- Name: pki_user_type_id; Type: DEFAULT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user_type ALTER COLUMN pki_user_type_id SET DEFAULT nextval('tbl_user_type_pki_user_type_id_seq'::regclass);


SET search_path = hospital, pg_catalog;

--
-- Data for Name: tbl_appnt_setting; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_appnt_setting (pki_appnt_setting_id, fki_hospital_id, fki_doctor_id, dt_date, i_no_of_appnts_per_day, fki_hos_dept_type_id, t_description, vc_time_slot) FROM stdin;
\.


--
-- Data for Name: tbl_appnt_setting_det; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_appnt_setting_det (pki_appnt_setting_det_id, fki_appnt_setting_id, vc_day, vc_time_slot, t_description) FROM stdin;
\.


--
-- Name: tbl_appnt_setting_det_pki_appnt_setting_det_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_appnt_setting_det_pki_appnt_setting_det_id_seq', 1, false);


--
-- Name: tbl_appnt_setting_pki_appnt_setting_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_appnt_setting_pki_appnt_setting_id_seq', 1, false);


--
-- Data for Name: tbl_doctor_qualif_link; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_doctor_qualif_link (pki_doctor_qualif_link_id, fki_doctor_qualif_master_id, fki_doctor_id) FROM stdin;
\.


--
-- Name: tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_doctor_qualif_link_pki_doctor_qualif_link_id_seq', 1, false);


--
-- Data for Name: tbl_doctor_qualif_master; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_doctor_qualif_master (pki_doctor_qualif_master_id, uvc_qualif_name, t_description) FROM stdin;
\.


--
-- Name: tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_doctor_qualif_master_pki_doctor_qualif_master_id_seq', 1, false);


--
-- Data for Name: tbl_document; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_document (pki_document_id, vc_document_name, fki_document_type_id, vc_document_url, fki_user_id, t_text) FROM stdin;
\.


--
-- Name: tbl_document_pki_document_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_document_pki_document_id_seq', 1, false);


--
-- Data for Name: tbl_document_type; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_document_type (pki_document_type_id, vc_document_type_name) FROM stdin;
\.


--
-- Name: tbl_document_type_pki_document_type_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_document_type_pki_document_type_id_seq', 1, false);


--
-- Data for Name: tbl_hos_dept_type; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_hos_dept_type (pki_hos_dept_type_id, vc_hos_dept_type_name, fki_parent_dept_type_id) FROM stdin;
\.


--
-- Name: tbl_hos_dept_type_pki_hos_dept_type_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_hos_dept_type_pki_hos_dept_type_id_seq', 1, false);


--
-- Data for Name: tbl_hos_notification; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_hos_notification (pki_hos_notification_id, fki_hospital_id, fki_doctor_id, dt_from_date, dt_to_date, fki_hos_dept_type_id, t_description) FROM stdin;
\.


--
-- Name: tbl_hos_notification_pki_hos_notification_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_hos_notification_pki_hos_notification_id_seq', 1, false);


--
-- Data for Name: tbl_hos_post_consultation; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_hos_post_consultation (pki_hos_post_consultation_id, fki_patient_id, fki_doctor_id, fki_hospital_id, fki_patient_appnt_id, dt_date, dt_next_consultation_date, vc_medicines_prescribed, t_description) FROM stdin;
\.


--
-- Name: tbl_hos_post_consultation_pki_hos_post_consultation_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_hos_post_consultation_pki_hos_post_consultation_id_seq', 1, false);


--
-- Data for Name: tbl_hos_test_report; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_hos_test_report (pki_hos_test_report_id, vc_hos_test_report_name, fki_user_id, dt_date, vc_hos_test_report_code, vc_op_number, fki_patient_appnt_id, fki_parent_dept_type_id) FROM stdin;
\.


--
-- Name: tbl_hos_test_report_pki_hos_test_report_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_hos_test_report_pki_hos_test_report_id_seq', 1, false);


--
-- Data for Name: tbl_patient_appnt; Type: TABLE DATA; Schema: hospital; Owner: postgres
--

COPY tbl_patient_appnt (pki_patient_appnt_id, vc_token_number, i_confirmation_status, fki_hospital_id, fki_doctor_id, fki_patient_id, dt_date, vc_op_number, fki_hos_dept_type_id, i_appnt_type, dt_appnt_time, vc_reference_name, fki_reference_patient, vc_description, i_consultation_type, i_consession_type) FROM stdin;
\.


--
-- Name: tbl_patient_appnt_pki_patient_appnt_id_seq; Type: SEQUENCE SET; Schema: hospital; Owner: postgres
--

SELECT pg_catalog.setval('tbl_patient_appnt_pki_patient_appnt_id_seq', 1, false);


SET search_path = public, pg_catalog;

--
-- Data for Name: tbl_alerts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_alerts (pki_alert_id, fki_from_user_id, fki_from_user_type_id, fki_to_user_id, dt_date, vc_token_number, i_alert_type, t_description) FROM stdin;
\.


--
-- Name: tbl_alerts_pki_alert_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_alerts_pki_alert_id_seq', 1, false);


--
-- Data for Name: tbl_country; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_country (pki_country_id, vc_country_name, vc_country_code) FROM stdin;
\.


--
-- Name: tbl_country_pki_country_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_country_pki_country_id_seq', 1, false);


--
-- Data for Name: tbl_favourites; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_favourites (pki_favourite_id, fki_provider_user_id, fki_provider_user_type_id, fki_consumer_user_id, dt_date, t_description) FROM stdin;
\.


--
-- Name: tbl_favourites_pki_favourite_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_favourites_pki_favourite_id_seq', 1, false);


--
-- Data for Name: tbl_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_images (pki_image_id, vc_image_url, b_is_primary, i_image_order, fki_user_id) FROM stdin;
\.


--
-- Name: tbl_images_pki_image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_images_pki_image_id_seq', 1, false);


--
-- Data for Name: tbl_state; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_state (pki_state_id, vc_state_name, vc_state_code) FROM stdin;
\.


--
-- Name: tbl_state_pki_state_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_state_pki_state_id_seq', 1, false);


--
-- Data for Name: tbl_trust_stories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tbl_trust_stories (pki_trust_stories_id, fki_provider_parent_id, fki_provider_id, fki_consumer_id, dt_date, t_description, i_rating) FROM stdin;
\.


--
-- Name: tbl_trust_stories_pki_trust_stories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_trust_stories_pki_trust_stories_id_seq', 1, false);


SET search_path = restaurant, pg_catalog;

--
-- Data for Name: tbl_rest_food_category; Type: TABLE DATA; Schema: restaurant; Owner: postgres
--

COPY tbl_rest_food_category (pki_rest_food_category_id, vc_rest_food_category_name, t_description, i_rating) FROM stdin;
\.


--
-- Name: tbl_rest_food_category_pki_rest_food_category_id_seq; Type: SEQUENCE SET; Schema: restaurant; Owner: postgres
--

SELECT pg_catalog.setval('tbl_rest_food_category_pki_rest_food_category_id_seq', 1, false);


--
-- Data for Name: tbl_rest_food_types; Type: TABLE DATA; Schema: restaurant; Owner: postgres
--

COPY tbl_rest_food_types (pki_rest_food_types_id, vc_food_type_name, fki_rest_food_category_id, dt_date, t_description, i_rating) FROM stdin;
\.


--
-- Name: tbl_rest_food_types_pki_rest_food_types_id_seq; Type: SEQUENCE SET; Schema: restaurant; Owner: postgres
--

SELECT pg_catalog.setval('tbl_rest_food_types_pki_rest_food_types_id_seq', 1, false);


--
-- Data for Name: tbl_rest_order; Type: TABLE DATA; Schema: restaurant; Owner: postgres
--

COPY tbl_rest_order (pki_rest_order_id, fki_rest_id, fki_user_id, dt_date, dt_delivery_time, vc_token_number, fki_rest_food_types_id, i_confirmation_status, vc_reference_name, fki_reference_user, vc_order_status, vc_delivery_boy_name) FROM stdin;
\.


--
-- Name: tbl_rest_order_pki_rest_order_id_seq; Type: SEQUENCE SET; Schema: restaurant; Owner: postgres
--

SELECT pg_catalog.setval('tbl_rest_order_pki_rest_order_id_seq', 1, false);


SET search_path = usermanagement, pg_catalog;

--
-- Data for Name: tbl_address; Type: TABLE DATA; Schema: usermanagement; Owner: postgres
--

COPY tbl_address (pki_address_id, fki_user_id, vc_location, vc_location_lattitude, vc_location_longitude, i_location_buffer, vc_pin_code, i_is_primary) FROM stdin;
\.


--
-- Name: tbl_address_pki_address_id_seq; Type: SEQUENCE SET; Schema: usermanagement; Owner: postgres
--

SELECT pg_catalog.setval('tbl_address_pki_address_id_seq', 1, false);


--
-- Data for Name: tbl_role; Type: TABLE DATA; Schema: usermanagement; Owner: postgres
--

COPY tbl_role (pki_role_id, uvc_role_name) FROM stdin;
1	PROVIDER
\.


--
-- Name: tbl_role_pki_role_id_seq; Type: SEQUENCE SET; Schema: usermanagement; Owner: postgres
--

SELECT pg_catalog.setval('tbl_role_pki_role_id_seq', 1, true);


--
-- Data for Name: tbl_subscription; Type: TABLE DATA; Schema: usermanagement; Owner: postgres
--

COPY tbl_subscription (pki_sub_id, fki_user_id, dt_from_date, dt_to_date, i_status, vc_description, dt_created_date, dt_updated_dt) FROM stdin;
\.


--
-- Name: tbl_subscription_pki_sub_id_seq; Type: SEQUENCE SET; Schema: usermanagement; Owner: postgres
--

SELECT pg_catalog.setval('tbl_subscription_pki_sub_id_seq', 1, false);


--
-- Data for Name: tbl_user; Type: TABLE DATA; Schema: usermanagement; Owner: postgres
--

COPY tbl_user (pki_user_id, fki_role_id, fki_user_type_id, fki_parent_user_id, uvc_username, vc_password, i_user_status, dt_created_date, dt_updated_dt) FROM stdin;
1	1	1	\N	jinesh	password	0	2016-08-10 10:08:56.083	2016-08-10 15:38:56.083
\.


--
-- Data for Name: tbl_user_details; Type: TABLE DATA; Schema: usermanagement; Owner: postgres
--

COPY tbl_user_details (pki_user_det_id, fki_user_id, vc_f_name, vc_l_name, uvc_p_email_address, vc_s_email_address, vc_device_token, i_device_type, vc_phone_1, vc_phone_2, vc_phone_3, dt_created_date, dt_updated_dt, fki_country_id) FROM stdin;
\.


--
-- Name: tbl_user_details_pki_user_det_id_seq; Type: SEQUENCE SET; Schema: usermanagement; Owner: postgres
--

SELECT pg_catalog.setval('tbl_user_details_pki_user_det_id_seq', 1, false);


--
-- Name: tbl_user_pki_user_id_seq; Type: SEQUENCE SET; Schema: usermanagement; Owner: postgres
--

SELECT pg_catalog.setval('tbl_user_pki_user_id_seq', 1, true);


--
-- Data for Name: tbl_user_type; Type: TABLE DATA; Schema: usermanagement; Owner: postgres
--

COPY tbl_user_type (pki_user_type_id, uvc_user_type_name) FROM stdin;
1	HOSPITAL
\.


--
-- Name: tbl_user_type_pki_user_type_id_seq; Type: SEQUENCE SET; Schema: usermanagement; Owner: postgres
--

SELECT pg_catalog.setval('tbl_user_type_pki_user_type_id_seq', 1, true);


SET search_path = hospital, pg_catalog;

--
-- Name: tbl_appnt_setting_det_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_appnt_setting_det
    ADD CONSTRAINT tbl_appnt_setting_det_pkey PRIMARY KEY (pki_appnt_setting_det_id);


--
-- Name: tbl_appnt_setting_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_appnt_setting
    ADD CONSTRAINT tbl_appnt_setting_pkey PRIMARY KEY (pki_appnt_setting_id);


--
-- Name: tbl_doctor_qualif_link_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_doctor_qualif_link
    ADD CONSTRAINT tbl_doctor_qualif_link_pkey PRIMARY KEY (pki_doctor_qualif_link_id);


--
-- Name: tbl_doctor_qualif_master_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_doctor_qualif_master
    ADD CONSTRAINT tbl_doctor_qualif_master_pkey PRIMARY KEY (pki_doctor_qualif_master_id);


--
-- Name: tbl_document_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_document
    ADD CONSTRAINT tbl_document_pkey PRIMARY KEY (pki_document_id);


--
-- Name: tbl_document_type_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_document_type
    ADD CONSTRAINT tbl_document_type_pkey PRIMARY KEY (pki_document_type_id);


--
-- Name: tbl_hos_dept_type_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_hos_dept_type
    ADD CONSTRAINT tbl_hos_dept_type_pkey PRIMARY KEY (pki_hos_dept_type_id);


--
-- Name: tbl_hos_dept_type_vc_hos_dept_type_name_key; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_hos_dept_type
    ADD CONSTRAINT tbl_hos_dept_type_vc_hos_dept_type_name_key UNIQUE (vc_hos_dept_type_name);


--
-- Name: tbl_hos_notification_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_hos_notification
    ADD CONSTRAINT tbl_hos_notification_pkey PRIMARY KEY (pki_hos_notification_id);


--
-- Name: tbl_hos_post_consultation_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_hos_post_consultation
    ADD CONSTRAINT tbl_hos_post_consultation_pkey PRIMARY KEY (pki_hos_post_consultation_id);


--
-- Name: tbl_hos_test_report_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_hos_test_report
    ADD CONSTRAINT tbl_hos_test_report_pkey PRIMARY KEY (pki_hos_test_report_id);


--
-- Name: tbl_hos_test_report_vc_hos_test_report_name_key; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_hos_test_report
    ADD CONSTRAINT tbl_hos_test_report_vc_hos_test_report_name_key UNIQUE (vc_hos_test_report_name);


--
-- Name: tbl_patient_appnt_pkey; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_pkey PRIMARY KEY (pki_patient_appnt_id);


--
-- Name: tbl_patient_appnt_vc_token_number_key; Type: CONSTRAINT; Schema: hospital; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_vc_token_number_key UNIQUE (vc_token_number);


SET search_path = public, pg_catalog;

--
-- Name: tbl_alerts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_alerts
    ADD CONSTRAINT tbl_alerts_pkey PRIMARY KEY (pki_alert_id);


--
-- Name: tbl_alerts_vc_token_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_alerts
    ADD CONSTRAINT tbl_alerts_vc_token_number_key UNIQUE (vc_token_number);


--
-- Name: tbl_country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_country
    ADD CONSTRAINT tbl_country_pkey PRIMARY KEY (pki_country_id);


--
-- Name: tbl_favourites_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_favourites
    ADD CONSTRAINT tbl_favourites_pkey PRIMARY KEY (pki_favourite_id);


--
-- Name: tbl_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_images
    ADD CONSTRAINT tbl_images_pkey PRIMARY KEY (pki_image_id);


--
-- Name: tbl_state_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_state
    ADD CONSTRAINT tbl_state_pkey PRIMARY KEY (pki_state_id);


--
-- Name: tbl_trust_stories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_trust_stories
    ADD CONSTRAINT tbl_trust_stories_pkey PRIMARY KEY (pki_trust_stories_id);


SET search_path = restaurant, pg_catalog;

--
-- Name: tbl_rest_food_category_pkey; Type: CONSTRAINT; Schema: restaurant; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_rest_food_category
    ADD CONSTRAINT tbl_rest_food_category_pkey PRIMARY KEY (pki_rest_food_category_id);


--
-- Name: tbl_rest_food_category_vc_rest_food_category_name_key; Type: CONSTRAINT; Schema: restaurant; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_rest_food_category
    ADD CONSTRAINT tbl_rest_food_category_vc_rest_food_category_name_key UNIQUE (vc_rest_food_category_name);


--
-- Name: tbl_rest_food_types_pkey; Type: CONSTRAINT; Schema: restaurant; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_rest_food_types
    ADD CONSTRAINT tbl_rest_food_types_pkey PRIMARY KEY (pki_rest_food_types_id);


--
-- Name: tbl_rest_food_types_vc_food_type_name_key; Type: CONSTRAINT; Schema: restaurant; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_rest_food_types
    ADD CONSTRAINT tbl_rest_food_types_vc_food_type_name_key UNIQUE (vc_food_type_name);


--
-- Name: tbl_rest_order_pkey; Type: CONSTRAINT; Schema: restaurant; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_rest_order
    ADD CONSTRAINT tbl_rest_order_pkey PRIMARY KEY (pki_rest_order_id);


--
-- Name: tbl_rest_order_vc_token_number_key; Type: CONSTRAINT; Schema: restaurant; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_rest_order
    ADD CONSTRAINT tbl_rest_order_vc_token_number_key UNIQUE (vc_token_number);


SET search_path = usermanagement, pg_catalog;

--
-- Name: tbl_role_pkey; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_role
    ADD CONSTRAINT tbl_role_pkey PRIMARY KEY (pki_role_id);


--
-- Name: tbl_role_uvc_role_name_key; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_role
    ADD CONSTRAINT tbl_role_uvc_role_name_key UNIQUE (uvc_role_name);


--
-- Name: tbl_subscription_pkey; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_subscription
    ADD CONSTRAINT tbl_subscription_pkey PRIMARY KEY (pki_sub_id);


--
-- Name: tbl_user_details_pkey; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_user_details
    ADD CONSTRAINT tbl_user_details_pkey PRIMARY KEY (pki_user_det_id);


--
-- Name: tbl_user_details_uvc_p_email_address_key; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_user_details
    ADD CONSTRAINT tbl_user_details_uvc_p_email_address_key UNIQUE (uvc_p_email_address);


--
-- Name: tbl_user_pkey; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_pkey PRIMARY KEY (pki_user_id);


--
-- Name: tbl_user_type_pkey; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_user_type
    ADD CONSTRAINT tbl_user_type_pkey PRIMARY KEY (pki_user_type_id);


--
-- Name: tbl_user_type_uvc_user_type_name_key; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_user_type
    ADD CONSTRAINT tbl_user_type_uvc_user_type_name_key UNIQUE (uvc_user_type_name);


--
-- Name: tbl_user_uvc_username_key; Type: CONSTRAINT; Schema: usermanagement; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_uvc_username_key UNIQUE (uvc_username);


SET search_path = hospital, pg_catalog;

--
-- Name: tbl_appnt_setting_det_fki_appnt_setting_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_appnt_setting_det
    ADD CONSTRAINT tbl_appnt_setting_det_fki_appnt_setting_id_fkey FOREIGN KEY (fki_appnt_setting_id) REFERENCES tbl_appnt_setting(pki_appnt_setting_id);


--
-- Name: tbl_appnt_setting_fki_doctor_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_appnt_setting
    ADD CONSTRAINT tbl_appnt_setting_fki_doctor_id_fkey FOREIGN KEY (fki_doctor_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_appnt_setting_fki_hos_dept_type_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_appnt_setting
    ADD CONSTRAINT tbl_appnt_setting_fki_hos_dept_type_id_fkey FOREIGN KEY (fki_hos_dept_type_id) REFERENCES tbl_hos_dept_type(pki_hos_dept_type_id);


--
-- Name: tbl_appnt_setting_fki_hospital_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_appnt_setting
    ADD CONSTRAINT tbl_appnt_setting_fki_hospital_id_fkey FOREIGN KEY (fki_hospital_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_doctor_qualif_link_fki_doctor_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_doctor_qualif_link
    ADD CONSTRAINT tbl_doctor_qualif_link_fki_doctor_id_fkey FOREIGN KEY (fki_doctor_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_doctor_qualif_link_fki_doctor_qualif_master_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_doctor_qualif_link
    ADD CONSTRAINT tbl_doctor_qualif_link_fki_doctor_qualif_master_id_fkey FOREIGN KEY (fki_doctor_qualif_master_id) REFERENCES tbl_doctor_qualif_master(pki_doctor_qualif_master_id);


--
-- Name: tbl_document_fki_document_type_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_document
    ADD CONSTRAINT tbl_document_fki_document_type_id_fkey FOREIGN KEY (fki_document_type_id) REFERENCES tbl_document_type(pki_document_type_id);


--
-- Name: tbl_document_fki_user_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_document
    ADD CONSTRAINT tbl_document_fki_user_id_fkey FOREIGN KEY (fki_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_hos_dept_type_fki_parent_dept_type_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_dept_type
    ADD CONSTRAINT tbl_hos_dept_type_fki_parent_dept_type_id_fkey FOREIGN KEY (fki_parent_dept_type_id) REFERENCES tbl_hos_dept_type(pki_hos_dept_type_id);


--
-- Name: tbl_hos_notification_fki_doctor_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_notification
    ADD CONSTRAINT tbl_hos_notification_fki_doctor_id_fkey FOREIGN KEY (fki_doctor_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_hos_notification_fki_hos_dept_type_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_notification
    ADD CONSTRAINT tbl_hos_notification_fki_hos_dept_type_id_fkey FOREIGN KEY (fki_hos_dept_type_id) REFERENCES tbl_hos_dept_type(pki_hos_dept_type_id);


--
-- Name: tbl_hos_notification_fki_hospital_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_notification
    ADD CONSTRAINT tbl_hos_notification_fki_hospital_id_fkey FOREIGN KEY (fki_hospital_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_hos_post_consultation_fki_doctor_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_post_consultation
    ADD CONSTRAINT tbl_hos_post_consultation_fki_doctor_id_fkey FOREIGN KEY (fki_doctor_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_hos_post_consultation_fki_hospital_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_post_consultation
    ADD CONSTRAINT tbl_hos_post_consultation_fki_hospital_id_fkey FOREIGN KEY (fki_hospital_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_hos_post_consultation_fki_patient_appnt_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_post_consultation
    ADD CONSTRAINT tbl_hos_post_consultation_fki_patient_appnt_id_fkey FOREIGN KEY (fki_patient_appnt_id) REFERENCES tbl_patient_appnt(pki_patient_appnt_id);


--
-- Name: tbl_hos_post_consultation_fki_patient_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_post_consultation
    ADD CONSTRAINT tbl_hos_post_consultation_fki_patient_id_fkey FOREIGN KEY (fki_patient_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_hos_test_report_fki_parent_dept_type_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_test_report
    ADD CONSTRAINT tbl_hos_test_report_fki_parent_dept_type_id_fkey FOREIGN KEY (fki_parent_dept_type_id) REFERENCES tbl_hos_dept_type(pki_hos_dept_type_id);


--
-- Name: tbl_hos_test_report_fki_patient_appnt_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_test_report
    ADD CONSTRAINT tbl_hos_test_report_fki_patient_appnt_id_fkey FOREIGN KEY (fki_patient_appnt_id) REFERENCES tbl_patient_appnt(pki_patient_appnt_id);


--
-- Name: tbl_hos_test_report_fki_user_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_hos_test_report
    ADD CONSTRAINT tbl_hos_test_report_fki_user_id_fkey FOREIGN KEY (fki_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_patient_appnt_fki_doctor_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_fki_doctor_id_fkey FOREIGN KEY (fki_doctor_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_patient_appnt_fki_hos_dept_type_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_fki_hos_dept_type_id_fkey FOREIGN KEY (fki_hos_dept_type_id) REFERENCES tbl_hos_dept_type(pki_hos_dept_type_id);


--
-- Name: tbl_patient_appnt_fki_hospital_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_fki_hospital_id_fkey FOREIGN KEY (fki_hospital_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_patient_appnt_fki_patient_id_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_fki_patient_id_fkey FOREIGN KEY (fki_patient_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_patient_appnt_fki_reference_patient_fkey; Type: FK CONSTRAINT; Schema: hospital; Owner: postgres
--

ALTER TABLE ONLY tbl_patient_appnt
    ADD CONSTRAINT tbl_patient_appnt_fki_reference_patient_fkey FOREIGN KEY (fki_reference_patient) REFERENCES usermanagement.tbl_user(pki_user_id);


SET search_path = public, pg_catalog;

--
-- Name: tbl_alerts_fki_from_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_alerts
    ADD CONSTRAINT tbl_alerts_fki_from_user_id_fkey FOREIGN KEY (fki_from_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_alerts_fki_from_user_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_alerts
    ADD CONSTRAINT tbl_alerts_fki_from_user_type_id_fkey FOREIGN KEY (fki_from_user_type_id) REFERENCES usermanagement.tbl_user_type(pki_user_type_id);


--
-- Name: tbl_alerts_fki_to_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_alerts
    ADD CONSTRAINT tbl_alerts_fki_to_user_id_fkey FOREIGN KEY (fki_to_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_favourites_fki_consumer_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_favourites
    ADD CONSTRAINT tbl_favourites_fki_consumer_user_id_fkey FOREIGN KEY (fki_consumer_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_favourites_fki_provider_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_favourites
    ADD CONSTRAINT tbl_favourites_fki_provider_user_id_fkey FOREIGN KEY (fki_provider_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_favourites_fki_provider_user_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_favourites
    ADD CONSTRAINT tbl_favourites_fki_provider_user_type_id_fkey FOREIGN KEY (fki_provider_user_type_id) REFERENCES usermanagement.tbl_user_type(pki_user_type_id);


--
-- Name: tbl_images_fki_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_images
    ADD CONSTRAINT tbl_images_fki_user_id_fkey FOREIGN KEY (fki_user_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_trust_stories_fki_consumer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_trust_stories
    ADD CONSTRAINT tbl_trust_stories_fki_consumer_id_fkey FOREIGN KEY (fki_consumer_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_trust_stories_fki_provider_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_trust_stories
    ADD CONSTRAINT tbl_trust_stories_fki_provider_id_fkey FOREIGN KEY (fki_provider_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_trust_stories_fki_provider_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_trust_stories
    ADD CONSTRAINT tbl_trust_stories_fki_provider_parent_id_fkey FOREIGN KEY (fki_provider_parent_id) REFERENCES usermanagement.tbl_user(pki_user_id);


SET search_path = restaurant, pg_catalog;

--
-- Name: tbl_rest_food_types_fki_rest_food_category_id_fkey; Type: FK CONSTRAINT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_food_types
    ADD CONSTRAINT tbl_rest_food_types_fki_rest_food_category_id_fkey FOREIGN KEY (fki_rest_food_category_id) REFERENCES tbl_rest_food_category(pki_rest_food_category_id);


--
-- Name: tbl_rest_order_fki_reference_user_fkey; Type: FK CONSTRAINT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_order
    ADD CONSTRAINT tbl_rest_order_fki_reference_user_fkey FOREIGN KEY (fki_reference_user) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_rest_order_fki_rest_food_types_id_fkey; Type: FK CONSTRAINT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_order
    ADD CONSTRAINT tbl_rest_order_fki_rest_food_types_id_fkey FOREIGN KEY (fki_rest_food_types_id) REFERENCES tbl_rest_food_types(pki_rest_food_types_id);


--
-- Name: tbl_rest_order_fki_rest_id_fkey; Type: FK CONSTRAINT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_order
    ADD CONSTRAINT tbl_rest_order_fki_rest_id_fkey FOREIGN KEY (fki_rest_id) REFERENCES usermanagement.tbl_user(pki_user_id);


--
-- Name: tbl_rest_order_fki_rest_id_fkey1; Type: FK CONSTRAINT; Schema: restaurant; Owner: postgres
--

ALTER TABLE ONLY tbl_rest_order
    ADD CONSTRAINT tbl_rest_order_fki_rest_id_fkey1 FOREIGN KEY (fki_rest_id) REFERENCES usermanagement.tbl_user(pki_user_id);


SET search_path = usermanagement, pg_catalog;

--
-- Name: fk_country_id; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user_details
    ADD CONSTRAINT fk_country_id FOREIGN KEY (fki_country_id) REFERENCES public.tbl_country(pki_country_id);


--
-- Name: tbl_address_fki_user_id_fkey; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_address
    ADD CONSTRAINT tbl_address_fki_user_id_fkey FOREIGN KEY (fki_user_id) REFERENCES tbl_user(pki_user_id);


--
-- Name: tbl_subscription_fki_user_id_fkey; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_subscription
    ADD CONSTRAINT tbl_subscription_fki_user_id_fkey FOREIGN KEY (fki_user_id) REFERENCES tbl_user(pki_user_id);


--
-- Name: tbl_user_details_fki_user_id_fkey; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user_details
    ADD CONSTRAINT tbl_user_details_fki_user_id_fkey FOREIGN KEY (fki_user_id) REFERENCES tbl_user(pki_user_id);


--
-- Name: tbl_user_fki_parent_user_id_fkey; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_fki_parent_user_id_fkey FOREIGN KEY (fki_parent_user_id) REFERENCES tbl_user(pki_user_id);


--
-- Name: tbl_user_fki_role_id_fkey; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_fki_role_id_fkey FOREIGN KEY (fki_role_id) REFERENCES tbl_role(pki_role_id);


--
-- Name: tbl_user_fki_user_type_id_fkey; Type: FK CONSTRAINT; Schema: usermanagement; Owner: postgres
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_fki_user_type_id_fkey FOREIGN KEY (fki_user_type_id) REFERENCES tbl_user_type(pki_user_type_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

