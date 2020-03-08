--Group hosts by hardware info
select 
  cpu_number, 
  id as host_id, 
  total_mem 
from 
  host_info 
order by 
  cpu_number, 
  total_mem DESC;

--Average memory usage
select 
  host_usage.host_id, 
  host_info.hostname, 
  DATE_TRUNC('hour', host_usage.timestamp) + interval '5 min' * (
    DATE_PART('minute', host_usage.timestamp)/ 5
  ):: int as time, 
  cast(
    avg(
      100 *(
        host_info.total_mem - host_usage.memory_free
      ) / host_info.total_mem
    ) as decimal(10, 0)
  ) as avg_used_mem_percentage 
from 
  host_usage 
  inner join host_info on host_info.id = host_usage.host_id 
group by 
  host_usage.host_id, 
  host_info.hostname, 
  host_info.total_mem, 
  DATE_TRUNC('hour', host_usage.timestamp) + interval '5 min' * (
    DATE_PART('minute', host_usage.timestamp)/ 5
  ):: int 
order by 
  host_usage.host_id;
