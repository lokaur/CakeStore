# Cakes Store

## Description
Order cakes service for the candy store.

Create two microservices:
### Cake Assortment Service
Microservice with private GRpc API:

* AddCake(name, price, cookingTime)
* RemoveCake(name)
* GetCake(name)

Every cake has name, price and cooking time. The list of cakes should be stored in the database.
Make simple authentication for this service.

### Cake Order Service
Microservice with public GRpc API:

* OrderCake(name)
* CheckOrderStatus(statusId)

A list of the orders should be stored in the database. You can see the status of the order.
The cake is selected by name (inside will be a lookup from the Assortment of Cakes service).
Cake baking is performed in the background. Each service has its own database.

On application start, the baking process for new and unfinished orders restarts.

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
docker-compose up -d --build
```

You can check running service instances in Eureka web interface: http://localhost:8761/

### GRpc Client
Firstly, install grpcc npm package:
```bash
npm install -g grpcc
```

Then, you can call order service grpc api:
```bash
cd order-proto/src/main/proto/
grpcc -i -p ./CakeOrder.proto -a localhost:9090

client.orderCake({cakeName: "Butter"}, pr) // will return orderId

client.checkOrderStatus({orderId: YOUR_ORDER_ID}, pr) // will return order status
```


Or you can edit available cakes list:
```bash
cd assortment-proto/src/main/proto/
grpcc -i -p ./CakesAssortment.proto -a localhost:6565

client.addCake({name: "Napoleon", price: 1000, cookingTime: 180000}, pr)
client.removeCake({name: "Napoleon"}, pr)
client.getCake({name: "Napoleon"}, pr)
client.getCake({id: 1}, pr)
```
