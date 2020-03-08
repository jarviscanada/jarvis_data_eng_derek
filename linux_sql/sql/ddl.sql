--Drop host_agent database if exists to avoid error
drop 
  database if exists host_agent;
--Create host_agent dabase
create database host_agent;
--Switch to host_agent by \c
\c host_agent 
--Create table host_info
create table public.host_info (
  id SERIAL PRIMARY KEY, 
  hostname VARCHAR NOT NULL UNIQUE, 
  cpu_number INT2 NOT NULL, 
  cpu_architecture VARCHAR NOT NULL, 
  cpu_model VARCHAR NOT NULL, 
  cpu_mhz FLOAT8 NOT NULL, 
  l2_cache INT4 NOT NULL, 
  "timestamp" TIMESTAMP NOT NULL, 
  total_mem INT4 NOT NULL 
);
--Create table host_usage
create table public.host_usage (
  "timestamp" TIMESTAMP NOT NULL, 
  host_id SERIAL NOT NULL, 
  memory_free INT4 NOT NULL, 
  cpu_idle INT2 NOT NULL, 
  cpu_kernel INT2 NOT NULL, 
  disk_io INT4 NOT NULL, 
  disk_available INT4 NOT NULL, 
  FOREIGN KEY (host_id) REFERENCES host_info (id)
);

