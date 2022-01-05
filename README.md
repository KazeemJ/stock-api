# Java Spring Stock Api

this repo is to showcase skills using spring boot, jpa, and api development

## How to run MYSQL docker instance

in order to run you need to have Docker installed.

There is a docker compose file located in the db folder inside of the stockapi package

run this command 

```java
docker-compose -f docker-compose.yml up
```

This will start the docker mysql image

Next run the spring boot **Application** class

## Using the API

The api currently has 3 endpoints

1. GET `localhost:8080/symbols`
    1. returns all stock symbols saved in the database
2. GET `localhost:8080/{symbol}`l
    1. Uses RestTemplate to query alphavantage stock api to get open and close market data. Then saves updated stock info in database. eg. [http://localhost:8080/symbols/GOOGL](http://localhost:8080/symbols/GOOGL)
3. DELETE `localhost:8080/{id}`
    1. deletes a record from the db by it’s uuid.

## TODO

1. Work on finishing unit test
2. Exception handling. Should bubble up to the http response
