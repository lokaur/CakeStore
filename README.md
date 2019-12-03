# Cakes Store

Training project that uses the following technologies:

* Kotlin
* Spring Boot
* Maven
* GRpc
* Flyway
* Mybatis
* PostgreSQL
* Docker
* Docker Compose

### Running
```bash
mvmw install
docker-compose up -d
```

### Test GRpc Client
```bash
npm install -g grpcc
cd assortment-service/src/main/proto
grpcc -i -p ./CakesAssortment.proto -a localhost:6565
```

Or you can make a service discovery from **Eureka** (open http://localhost:8761) and replace **localhost:6565**

###Example service call through grpcc
```bash
var md = cm({Login: "admin", Password: "securepassword"})
client.addCake({name: "Napoleon", price: 600, cookingTime: 240000}, md, printReply)
```