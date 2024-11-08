CREATE SCHEMA IF NOT EXISTS dh_ingress;
CREATE SCHEMA IF NOT EXISTS dh_account;
CREATE SCHEMA IF NOT EXISTS dh_customer;
CREATE SCHEMA IF NOT EXISTS dh_classifier;

CREATE TABLE dh_classifier."Segregation_Lookup" (
    "Customer_Nbr" BIGINT[] NULL,
    "LOB_Code" INT NULL,
    "Secure_Data_Protection_Ind" BOOLEAN NULL,
    "Secure_Data_Protection_Typ" VARCHAR[] NULL,
    "Secure_Data_Segregation_Typ" VARCHAR[] NULL,
    "Secure_Data_Segregation_Subtyp" VARCHAR[] NULL
);

INSERT INTO dh_classifier."Segregation_Lookup" (
    "Customer_Nbr", 
    "LOB_Code", 
    "Secure_Data_Protection_Ind", 
    "Secure_Data_Protection_Typ", 
    "Secure_Data_Segregation_Typ", 
    "Secure_Data_Segregation_Subtyp"
) VALUES
(ARRAY[1001], 321, TRUE, ARRAY['CU Protected'], ARRAY['Enterprise'], ARRAY['Commercial']),
(ARRAY[1002], 312, FALSE, ARRAY['CUI Non Protected'], ARRAY['Enterprise'], ARRAY['Commercial']),
(ARRAY[1003], 123, TRUE, ARRAY['CU Protected'], ARRAY['Public Sector'], ARRAY['SLED']),
(ARRAY[1004], 231, TRUE, ARRAY['CU Protected'], ARRAY['Public Sector'], ARRAY['FED']);

CREATE OR REPLACE FUNCTION dh_classifier."fn_Get_Segregation_Info"(customer_nbr BIGINT)
RETURNS TABLE (
    Secure_Data_Protection_Ind BOOLEAN,
    Secure_Data_Protection_Typ VARCHAR[],
    Secure_Data_Segregation_Typ VARCHAR[],
    Secure_Data_Segregation_Subtyp VARCHAR[]
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        "Secure_Data_Protection_Ind",
        "Secure_Data_Protection_Typ",
        "Secure_Data_Segregation_Typ",
        "Secure_Data_Segregation_Subtyp"
    FROM dh_classifier."Segregation_Lookup"
    WHERE customer_nbr = ANY("Customer_Nbr");
END;
$$ LANGUAGE plpgsql;


select * from dh_classifier."fn_Get_Segregation_Info"(1001);


-- Recreate Ingest_Customer_Account table in dh_ingress schema
CREATE TABLE dh_ingress."Ingest_Customer_Account" (
    "Customer_Account_Id" SERIAL PRIMARY KEY,
    "KafkaTopic" VARCHAR(255) NOT NULL,
    "PayloadJSON" JSONB NOT NULL,
    "ProcessingStatus" VARCHAR(50) NOT NULL,
    "Secure_Data_Protection_Ind" BOOLEAN,
    "Secure_Data_Protection_Typ" VARCHAR(100),
    "Secure_Data_Segregation_Typ" VARCHAR(100),
    "Secure_Data_Segregation_Subtyp" VARCHAR(100)
);

-- Recreate Ingest_Customer_Ultimate table in dh_ingress schema
CREATE TABLE dh_ingress."Ingest_Customer_Ultimate" (
    "Customer_Ultimate_Id" SERIAL PRIMARY KEY,
    "KafkaTopic" VARCHAR(255) NOT NULL,
    "PayloadJSON" JSONB NOT NULL,
    "ProcessingStatus" VARCHAR(50) NOT NULL,
    "Secure_Data_Protection_Ind" BOOLEAN,
    "Secure_Data_Protection_Typ" VARCHAR(100),
    "Secure_Data_Segregation_Typ" VARCHAR(100),
    "Secure_Data_Segregation_Subtyp" VARCHAR(100)
);

-- Recreate Ingest_Customer_LOB table in dh_ingress schema
CREATE TABLE dh_ingress."Ingest_Customer_LOB" (
    "Customer_LOB_Id" SERIAL PRIMARY KEY,
    "KafkaTopic" VARCHAR(255) NOT NULL,
    "PayloadJSON" JSONB NOT NULL,
    "ProcessingStatus" VARCHAR(50) NOT NULL,
    "Secure_Data_Protection_Ind" BOOLEAN,
    "Secure_Data_Protection_Typ" VARCHAR(100),
    "Secure_Data_Segregation_Typ" VARCHAR(100),
    "Secure_Data_Segregation_Subtyp" VARCHAR(100)
);


INSERT INTO dh_ingress."Ingest_Customer_Account" (
    "KafkaTopic", "PayloadJSON", "ProcessingStatus", 
    "Secure_Data_Protection_Ind", "Secure_Data_Protection_Typ", 
    "Secure_Data_Segregation_Typ", "Secure_Data_Segregation_Subtyp"
) VALUES
('customer.account.topic', '{"account_id": 1, "name": "John Doe"}'::jsonb, 'Processed', TRUE, 'CU Protected', 'Enterprise', 'Commercial'),
('customer.account.topic', '{"account_id": 2, "name": "Jane Smith"}'::jsonb, 'Pending', FALSE, 'CUI Non Protected', 'Enterprise', 'Commercial'),
('customer.account.topic', '{"account_id": 3, "name": "Alice Johnson"}'::jsonb, 'Failed', TRUE, 'CU Protected', 'Public Sector', 'SLED');


INSERT INTO dh_ingress."Ingest_Customer_Ultimate" (
    "KafkaTopic", "PayloadJSON", "ProcessingStatus", 
    "Secure_Data_Protection_Ind", "Secure_Data_Protection_Typ", 
    "Secure_Data_Segregation_Typ", "Secure_Data_Segregation_Subtyp"
) VALUES
('customer.ultimate.topic', '{"ultimate_id": 101, "parent_account": 1}'::jsonb, 'Processed', TRUE, 'CU Protected', 'Enterprise', 'Commercial'),
('customer.ultimate.topic', '{"ultimate_id": 102, "parent_account": 2}'::jsonb, 'Pending', FALSE, 'CUI Non Protected', 'Enterprise', 'Commercial'),
('customer.ultimate.topic', '{"ultimate_id": 103, "parent_account": 3}'::jsonb, 'Failed', TRUE, 'CU Protected', 'Public Sector', 'FED');


INSERT INTO dh_ingress."Ingest_Customer_LOB" (
    "KafkaTopic", "PayloadJSON", "ProcessingStatus", 
    "Secure_Data_Protection_Ind", "Secure_Data_Protection_Typ", 
    "Secure_Data_Segregation_Typ", "Secure_Data_Segregation_Subtyp"
) VALUES
('customer.lob.topic', '{"lob_id": 201, "description": "Healthcare"}'::jsonb, 'Processed', TRUE, 'CU Protected', 'Public Sector', 'SLED'),
('customer.lob.topic', '{"lob_id": 202, "description": "Finance"}'::jsonb, 'Pending', FALSE, 'CUI Non Protected', 'Enterprise', 'Commercial'),
('customer.lob.topic', '{"lob_id": 203, "description": "Education"}'::jsonb, 'Failed', TRUE, 'CU Protected', 'Public Sector', 'FED');


CREATE SCHEMA IF NOT EXISTS dh_customer;

CREATE TABLE dh_customer.CUSTOMER (
    customer_obs_id SERIAL PRIMARY KEY,
    customer_id INTEGER,
    customer_key_id INTEGER,
    customer_class_typ VARCHAR(50),
    customer_type_cd VARCHAR(10),
    customer_appl_sys_id INTEGER,
    customer_approval_dt DATE,
    customer_status_cd VARCHAR(10),
    customer_status_reason_cd VARCHAR(50),
    customer_hosted_acct_ind BOOLEAN,
    parent_customer_obs_id INTEGER,
    customer_name VARCHAR(255),
    customer_point_name VARCHAR(255),
    customer_contact_name VARCHAR(255),
    customer_contact_email_addr VARCHAR(255),
    customer_website_addr VARCHAR(255),
    hq_street_addr VARCHAR(255),
    hq_address_bldg VARCHAR(255),
    hq_suit_floor_addr VARCHAR(50),
    hq_address_other_unit_typ VARCHAR(50),
    hq_address_other_unit_value VARCHAR(50),
    hq_city VARCHAR(100),
    hq_state_cd VARCHAR(10),
    hq_postal VARCHAR(20),
    hq_country_name VARCHAR(100),
    address_validation_status_cd VARCHAR(50),
    swbs_repl_id VARCHAR(50),
    naics_code INTEGER,
    naics_desc VARCHAR(255),
    customer_success_position_id INTEGER,
    customer_channel VARCHAR(50),
    customer_status_reason_desc VARCHAR(255),
    division_key_id INTEGER,
    federal_tax_id_num VARCHAR(20),
    legal_entity_typ VARCHAR(50),
    partner_cd VARCHAR(50),
    partner_name VARCHAR(255),
    sales_office_cd VARCHAR(50),
    sales_region_cd VARCHAR(50),
    cpni_notice_create_dt DATE,
    cpni_status_cd VARCHAR(10),
    cpni_status_start_dt DATE,
    cpni_status_removed_dt DATE,
    secure_data_protection_typ VARCHAR(50),
    secure_data_protection_ind BOOLEAN,
    meta_source_system_name VARCHAR(100),
    meta_source_create_dt TIMESTAMP,
    meta_source_create_user_id VARCHAR(50),
    meta_source_update_dt TIMESTAMP,
    meta_source_update_user_id VARCHAR(50),
    meta_load_dt TIMESTAMP,
    meta_secure_company_nbr INTEGER,
    meta_secure_cd INTEGER,
    meta_secure_system_cd VARCHAR(10),
    meta_update_process_typ VARCHAR(50)
);

-- Add foreign key constraints if needed
-- ALTER TABLE dh_customer.CUSTOMER
-- ADD CONSTRAINT fk_parent_customer_obs_id
-- FOREIGN KEY (parent_customer_obs_id) REFERENCES dh_customer.CUSTOMER (customer_obs_id);

-- Add indexes if necessary
CREATE INDEX idx_customer_key_id ON dh_customer.CUSTOMER (customer_key_id);
CREATE INDEX idx_customer_type_cd ON dh_customer.CUSTOMER (customer_type_cd);
CREATE INDEX idx_customer_status_cd ON dh_customer.CUSTOMER (customer_status_cd);
CREATE INDEX idx_hq_state_cd ON dh_customer.CUSTOMER (hq_state_cd);
CREATE INDEX idx_hq_country_name ON dh_customer.CUSTOMER (hq_country_name);


CREATE TABLE dh_customer.CUSTOMER_ATTRIBUTE (
    customer_attribute_obs_id SERIAL PRIMARY KEY,
    customer_obs_id INTEGER NOT NULL,
    customer_attribute_key_id INTEGER NOT NULL,
    attribute_name VARCHAR(255),
    source_attribute_name VARCHAR(255),
    secure_data_protection_typ VARCHAR(50),
    secure_data_protection_ind BOOLEAN,
    secure_data_segregation_typ VARCHAR(50),
    secure_data_segregation_subtyp VARCHAR(50),
    originating_source_system_name VARCHAR(100),
    meta_source_create_dt TIMESTAMP,
    meta_source_create_user_id VARCHAR(50),
    meta_source_update_dt TIMESTAMP,
    meta_source_update_user_id VARCHAR(50),
    meta_load_dt TIMESTAMP,
    meta_secure_company_nbr INTEGER,
    meta_secure_cd INTEGER,
    meta_secure_system_cd VARCHAR(10),
    meta_update_process_typ VARCHAR(50),

    -- Foreign Key Constraint
    FOREIGN KEY (customer_obs_id) REFERENCES dh_customer.CUSTOMER (customer_obs_id)
);

-- Add indexes if necessary
CREATE INDEX idx_customer_obs_id ON dh_customer.CUSTOMER_ATTRIBUTE (customer_obs_id);
CREATE INDEX idx_customer_attribute_key_id ON dh_customer.CUSTOMER_ATTRIBUTE (customer_attribute_key_id);
CREATE INDEX idx_attribute_name ON dh_customer.CUSTOMER_ATTRIBUTE (attribute_name);

CREATE TABLE dh_customer.LOB_HIERARCHY (
    lob_hierarchy_obs_id SERIAL PRIMARY KEY,
    customer_obs_id INTEGER NOT NULL,
    lob_hierarchy_key_id INTEGER NOT NULL,
    lob_hierarchy_name VARCHAR(255),
    sales_channel_name VARCHAR(255),
    external_sub_bus_name VARCHAR(255),
    secure_data_protection_typ VARCHAR(50),
    secure_data_protection_ind BOOLEAN,
    secure_data_segregation_typ VARCHAR(50),
    secure_data_segregation_subtyp VARCHAR(50),
    originating_source_system_name VARCHAR(100),
    meta_source_create_dt TIMESTAMP,
    meta_source_create_user_id VARCHAR(50),
    meta_source_update_dt TIMESTAMP,
    meta_source_update_user_id VARCHAR(50),
    meta_load_dt TIMESTAMP,
    meta_secure_company_nbr INTEGER,
    meta_secure_cd INTEGER,
    meta_secure_system_cd VARCHAR(10),
    meta_update_process_typ VARCHAR(50),

    -- Foreign Key Constraint
    FOREIGN KEY (customer_obs_id) REFERENCES dh_customer.CUSTOMER (customer_obs_id)
);

-- Add indexes if necessary
CREATE INDEX idx_customer_obs_id ON dh_customer.LOB_HIERARCHY (customer_obs_id);
CREATE INDEX idx_lob_hierarchy_key_id ON dh_customer.LOB_HIERARCHY (lob_hierarchy_key_id);
CREATE INDEX idx_lob_hierarchy_name ON dh_customer.LOB_HIERARCHY (lob_hierarchy_name);

ALTER TABLE dh_ingress."Ingest_Customer_Account"
ADD COLUMN meta_load_dt TIMESTAMP,
ADD COLUMN meta_update_dt TIMESTAMP;

ALTER TABLE dh_ingress."Ingest_Customer_LOB"
ADD COLUMN meta_load_dt TIMESTAMP,
ADD COLUMN meta_update_dt TIMESTAMP;

ALTER TABLE dh_ingress."Ingest_Customer_Ultimate"
ADD COLUMN meta_load_dt TIMESTAMP,
ADD COLUMN meta_update_dt TIMESTAMP;

-- Set meta_load_dt to NOW() for Ingest_Customer_Account
UPDATE dh_ingress."Ingest_Customer_Account"
SET meta_load_dt = NOW();

-- Set meta_load_dt to NOW() for Ingest_Customer_LOB
UPDATE dh_ingress."Ingest_Customer_LOB"
SET meta_load_dt = NOW();

-- Set meta_load_dt to NOW() for Ingest_Customer_Ultimate
UPDATE dh_ingress."Ingest_Customer_Ultimate"
SET meta_load_dt = NOW();

CREATE OR REPLACE PROCEDURE dh_ingress.sp_Update_Protection_Info()
LANGUAGE plpgsql
AS $$
BEGIN
    -- Invert Secure_Data_Protection_Ind in Ingest_Customer_Account
    UPDATE dh_ingress."Ingest_Customer_Account"
    SET "Secure_Data_Protection_Ind" = NOT "Secure_Data_Protection_Ind",
        meta_update_dt = NOW()
    WHERE meta_load_dt >= NOW() - INTERVAL '30 days' AND "Secure_Data_Protection_Ind" IS NOT NULL;

    -- Invert Secure_Data_Protection_Ind in Ingest_Customer_LOB
    UPDATE dh_ingress."Ingest_Customer_LOB"
    SET "Secure_Data_Protection_Ind" = NOT "Secure_Data_Protection_Ind",
        meta_update_dt = NOW()
    WHERE meta_load_dt >= NOW() - INTERVAL '30 days' AND "Secure_Data_Protection_Ind" IS NOT NULL;

    -- Invert Secure_Data_Protection_Ind in Ingest_Customer_Ultimate
    UPDATE dh_ingress."Ingest_Customer_Ultimate"
    SET "Secure_Data_Protection_Ind" = NOT "Secure_Data_Protection_Ind",
        meta_update_dt = NOW()
    WHERE meta_load_dt >= NOW() - INTERVAL '30 days' AND "Secure_Data_Protection_Ind" IS NOT NULL;

    -- Log success
    INSERT INTO dh_ingress.Execution_Log (procedure_name, status, message)
    VALUES ('sp_Update_Protection_Info', 'Success', 'Procedure completed successfully');
    
EXCEPTION
    WHEN OTHERS THEN
        -- Log failure with error details
        INSERT INTO dh_ingress.Execution_Log (procedure_name, status, error_detail)
        VALUES ('sp_Update_Protection_Info', 'Failure', SQLERRM);
        RAISE; -- Optionally re-raise the error
END;
$$;

CREATE TABLE dh_ingress.Execution_Log (
    log_id SERIAL PRIMARY KEY,
    procedure_name VARCHAR(255) NOT NULL,
    execution_timestamp TIMESTAMP DEFAULT NOW(),
    status VARCHAR(50) NOT NULL,
    message TEXT,
    error_detail TEXT
);

--- Testing Code

select * from dh_ingress."Ingest_Customer_Account";
select * from dh_ingress.Execution_Log;

CALL dh_ingress.sp_Update_Protection_Info();

select * from dh_classifier."fn_Get_Segregation_Info"(1002);


