# <h1>Linux Cluster Monitoring Agent

## <h2>Introduction
Linux Cluster Monitoring Agent aims to collect server hardware specifications and monitor node resource usages, such as CPU and Memory, and store data in PostgreSQL database.

## <h2>Architecture
The diagram below shows the overview of the project:
  
![image](https://raw.githubusercontent.com/jarviscanada/jarvis_data_eng_derek/feature_linux_sql/linux_sql/Picture/1.png)

## <h3>*scripts:*

The bash agent collects server usage data, then insert into PostgreSQL instance. The agent is installed to all the hosts/servers/nodes, and it consists of two bash scripts:

  **host_info.sh:** collects server hardware specifications and insert into host_info table in host_agent database, and it is only run once when installing.\
  **host_usage.sh:** collect current host usage and insert into host_usage table in host_agent database, and crontab job will make it run every minute.
  Besides,**psql_docker.sh:** starts,stops or delete psql container in Docker.\

## <h3>*sql:*
 
 **ddl.sql:** creates host_agent database if not exists, and create host_info and host_usage tables in this database.\ 
 **queries.sql:** collects and process data that is stored in host_agent database for Linux Cluster Administration team.\
 
 ## <h2>Instructions
  command: ./scripts/psql_docker.sh start|stop|delete [db_password]\
  to start, stop or delete psql in Docker.\
  command: psql -h localhost -U postgres -W -f sql/ddl.sql\
  command: bash scripts/host_info.sh localhost 5432 host_agent postgres password\
  command: bash scripts/host_usage.sh localhost 5432 host_agent postgres password\
  command: crontab -e\
  type: * * * * * bash /scripts/hostusage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log\
  
  
