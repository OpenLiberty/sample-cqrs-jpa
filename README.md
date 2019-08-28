# sample-cqrs-jpa
Create a CQRS pattern application

# Pull docker image for mySQL
`docker pull mysql`

# Run image
`docker run --name jpa-mysql -e MYSQL_ROOT_PASSWORD=example -d -p 3306:3306 mysql:latest`

# Exec into image
`docker exec -it jpa-mysql mysql -uroot -p`
# type in password example 
`example`

# start services by running 
`mvn liberty:start-server` 
