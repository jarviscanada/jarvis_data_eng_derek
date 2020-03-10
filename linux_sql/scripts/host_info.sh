#! /bin/bash

#Assign CLI arguments to variables
psql_host=$1
psql_port=$2
psql_dbname=$3
psql_user=$4
psql_password=$5

#Reuse lscpu command
lscpu_out=$(lscpu)

#Parse and collect host hardware specifications
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "Architecture" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "Model name" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "CPU MHz" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "L2 cache" | awk -F '[ K]*' '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | egrep "MemTotal" | awk '{print $2}' | xargs)
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

#Construct the INSERT statement
insert_statement=$(cat << EOF
insert into host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, "timestamp")
values ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp')
EOF
)

export PGPASSWORD='password'

psql -h $psql_host -p $psql_port -d $psql_dbname -U $psql_user -c "$insert_statement"

echo "Data is inserted successfully!"

exit 0
