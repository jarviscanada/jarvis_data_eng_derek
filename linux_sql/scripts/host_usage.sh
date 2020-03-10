#! /bin/bash

#Assign CLI argument to variable

psql_host=$1
psql_port=$2
psql_dbname=$3
psql_user=$4
psql_password=$5

#Parse and collect host hardware usage data
hostname=$(hostname -f)
timestamp=$(date '+%Y-%m-%d %H:%M:%S')
memory_free=$(vmstat | egrep -v "procs|r" | awk '{print $4}' | xargs)
cpu_idle=$(vmstat --unit M | egrep -v "procs|r" | awk '{print $15}' | xargs)
cpu_kernel=$(vmstat --unit M | egrep -v "procs|r" | awk '{print $14}' | xargs)
disk_io=$(vmstat --disk-sum | egrep "inprogress" | awk '{print $1}' | xargs)
disk_available=$(df -BM / | egrep -v "Filesystem" | awk -F '[ M]*' '{print $4}' | xargs) 

#Insert data to psql database
insert_statement=$(cat << EOF
insert into host_usage ("timestamp", host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) 
values ('$timestamp', (select id from host_info where hostname='$hostname'), '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available')
EOF
)

export PGPASSWORD='password'
 
psql -h $psql_host -p $psql_port -d $psql_dbname -U $psql_user -c "$insert_statement"

echo "Data is inserted successfully!"

exit 0

