<h1>Linux Cluster Monitoring Agent

<h2>Introduction
<h4>Linux Cluster Monitoring Agent aims to collect server hardware specifications and monitor node resource usages, such as CPU and Memory, and store data in PostgreSQL database.

<h2>Architecture
<h4>The diagram below shows the overview of the project:
  
![image](https://raw.githubusercontent.com/jarviscanada/jarvis_data_eng_derek/feature_linux_sql/linux_sql/Picture/1.png)
<h4>The bash agent collects server usage data, then insert into PostgreSQL instance. The agent is installed to all the hosts/servers/nodes, and it consists of two bash scripts:
  host_info.sh collects server hardware specifications and insert into database, and it is only run once when installing.
  host_usage.sh collect current host usage and insert into database, and crontab job will make it run every minute.
  
