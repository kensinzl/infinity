#1. remove the pwd of postgres docker image using docker-compose
#2. run the spring in the docker
FROM postgres:12.1 as db
ENV POSTGRES_USER=kensin  
ENV POSTGRES_PASSWORD=123  
# database name
ENV POSTGRES_DB=DEV_DB