# <h1>Linux Cluster Monitoring Agent

## <h2>Introduction
Linux Cluster Monitoring Agent aims to collect server hardware specifications and monitor node resource usages, such as CPU and Memory, and store data in postgres database.

## <h2>Architecture
The diagram below shows the overview of the project:
  
![image](https://raw.githubusercontent.com/jarviscanada/jarvis_data_eng_derek/feature_linux_sql/linux_sql/Picture/1.png)

## <h3>*scripts:*

The bash agent collects server usage data, then insert into postgres instance. The agent is installed to all the hosts/servers/nodes, and it consists of two bash scripts:

+ **host_info.sh:** collects server hardware specifications and insert into database, and it is only run once when installing.
+ **host_usage.sh:** collect current host usage and insert into database, and crontab job will make it run every minute.
+ Besides,**psql_docker.sh:** starts,stops or delete psql container in Docker.

## <h3>*sql:*
 
+ **ddl.sql:** creates host_agent database if not exists, and create host_info and host_usage tables in this database. 
+ **queries.sql:** collects and process data that is stored in host_agent database for Linux Cluster Administration team.
 
 ## <h2>Instructions
  1. Run command: `./scripts/psql_docker.sh start|stop|delete [db_password]`\
     to start, stop or delete psql in Docker.\
  2. Run command: `psql -h localhost -U postgres -W -f sql/ddl.sql`\
     to create host_agent database and create host_info, host_usage tables in this database.
  3. Run command: `bash scripts/host_info.sh localhost 5432 host_agent postgres password`\
     to collect hardware specification and insert into host_info table.
  4. Run command: `bash scripts/host_usage.sh localhost 5432 host_agent postgres password`\
     to collect usage and insert into host_usage table.
  5. Run command: `crontab -e`\
     and type: `* * * * * bash /scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log` to trigger `host_uage.sh` every minute.
##<h2> Improvemetns
+ Add "delete" option in `./scripts/psql_docker.sh start|stop|delete [db_password]`\, so administrator can easily delete psql container easily.
+ The project can be more automated, so all the steps can be run by one script.
+ An usage alert can be set up and inform administrator automaticaly 
