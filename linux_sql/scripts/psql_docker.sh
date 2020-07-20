#! /bin/bash

mode=$1
db_password=$2

case $mode in

	start)	
		if [ "$#" -ne 2 ]; then
		  echo "Wrong input, please follow the format: ./psql_docker start|stop|delete [db_password]"
		  exit 1
		fi

		#Start docker if docker server is not running
		systemctl status docker || systemctl start docker

		#Check if postgres image alreaady exists, if not, download it.
		if sudo docker image ls | grep -q postgres; then 
 		  echo "Postgres image already exists"
		else
		  sudo docker pull postgres
 		fi

		#Set password for default user 'postgres'
		export PGPASSWORD='password'

		#Check if docker volume pgdata already exists, if not, create it.
		if sudo docker volume ls | grep -q pgdata; then
 		  echo "Docker volume pgdata already exists."
		else 
                  sudo docker volume create pgdata
 		  echo "Create docker volume pgdata"
		fi

		#Check if container jrvs-sql exists, if not, create it.
		if sudo docker ps | grep -q jrvs-psql; then
 		  echo "Container jrvs-psql already exists."
		else
		#Create a container using psql image with name=jrvs-psql
		sudo docker run --rm --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
		echo "Container jrvs-psql is successfully created."
		fi

		#Check if postgresql client is installed or not, if not, install it.
		if psql -V | grep -q psql; then 
                  echo "Postgresql client is already installed."
		else
		  sudo yum install -y postgresl
   		fi
		
		#Start running jrvs-psql container
		sudo docker container start jrvs-psql 
		echo "jrvs-psql container is running."
		#Connect to psql instance using psql REPL (read-eval-print loop)
		#psql -h localhost -U postgres -W 
		;;
	stop)	
		#If the number of CLI arguments not equal to 2 (we need both "stop" and "password"), 
		#then display fault information and exit 1, which means exit with error
		if [ "$#" -ne 2 ]; then
		  echo "Wrong input, please follow the format: ./psql_docker start|stop|delete [db_password]"
		  exit 1
		fi
	
   		#Check if jrvs-psql container exists before stopping it.
		#If exist, stop it.
                if sudo docker ps | grep -q jrvs-psql; then
		  sudo docker container stop jrvs-psql
		  echo "jrvs-psql container is stopped."
 		  		
      		#If not exist, show error message and exit
                else
		  echo "Container jrvs-psql does not exist."
		  exit 1
		fi
		;;
        delete) 
		#Remove jrvs-psql container, use with caution
		sudo docker rm -f jrvs-psql
		if sudo docker ps | grep -v jrvs-psql; then
 		  echo "Container jrvs-psql is successfully deleted."
		fi
		;;
	*)      
		echo "Wrong input, please follow the format: ./psql_docker start|stop|delete [db_password]"
		exit 1

esac
exit 0

