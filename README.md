# Cakes Store

## Description
Order cakes service for the candy store.

Create two microservices:
### Cake Assortment Service (Done)
Microservice with private GRpc API:

* AddCake(name, price, cookingTime)
* RemoveCake(name)
* GetCake(name)

Every cake has name, price and cooking time. The list of cakes should be stored in the database.
Make simple authorization for this service.

### Cake Order Service (WIP)
Microservice with public GRpc API:

* OrderCake(name)

A list of the orders should be stored in the database. You can see the status of the order.
The cake is selected by name (inside will be a lookup from the Assortment of Cakes service).
Cake baking is performed in the background. Each service has its own database.

## Technologies

* Kotlin
* Spring Boot
* Maven
* GRpc
* Flyway
* Mybatis
* PostgreSQL
* Docker
* Docker Compose

### Run
```bash
mvmw package
docker-compose up -d
```

### Test GRpc Client
```bash
npm install -g grpcc
cd assortment-proto/src/main/proto/
grpcc -i -p ./CakesAssortment.proto -a localhost:6565
```

Or you can make a service discovery from **Eureka** (open http://localhost:8761) and replace **localhost:6565**

### Example service call through grpcc
```bash
var md = cm({Login: "admin", Password: "securepassword"})
client.addCake({name: "Napoleon", price: 600, cookingTime: 240000}, md, printReply)
```