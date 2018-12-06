# cURL commands for testing of REST controller

#### Group.getById:
```
curl -i -X GET http://localhost:8080/pa165/rest/group/{id}
```
#### Group.getByName:
```
curl -i -X GET http://localhost:8080/pa165/rest/group/getByName/{name}
```
#### Group.getAll
```
curl: -i -X GET http://localhost:8080/pa165/rest/group/
```
#### Group.getHeroesFromGroup: 
```
curl -i -X GET http://localhost:8080/pa165/rest/group/getHeroesFromGroup/{groupId}
```
#### Group.addHero:
```
curl -i -X PUT http://localhost:8080/pa165/rest/group/addHero/{groupId}/{heroId}
```
#### Group.removeHero:
```
curl -i -X PUT http://localhost:8080/pa165/rest/group/removeHero/{groupId}/{heroId}
```
#### Hero.getById: 
```
curl -i -X GET http://localhost:8080/pa165/rest/hero/{id}
```
#### Hero.getAll:
```
curl -i -X GET http://localhost:8080/pa165/rest/hero/
```
#### Hero.create: 
```
curl -i -X POST http://localhost:8080/pa165/rest/hero/
```
#### Hero.delete: 
```
curl -i -X DELETE http://localhost:8080/pa165/rest/hero/{id}
```
#### Role.getById:
```
curl -i -X GET http://localhost:8080/pa165/rest/role/{id}
```
#### Role.getByName:
```
curl -i -X GET http://localhost:8080/pa165/rest/role/getByName/{name}
```
#### Role.getAll:
```
curl -i -X GET http://localhost:8080/pa165/rest/role/
```
#### Role.create:
```
curl -i -X POST http://localhost:8080/pa165/rest/role/
```
#### Troop.getById:
```
curl -i -X GET http://localhost:8080/pa165/rest/troop/{id}
```
#### Troop.getByName
```
curl -i -X GET http://localhost:8080/pa165/rest/troop/name/{name}
```
#### Troop.getAll
```
curl -i -X GET http://localhost:8080/pa165/rest/troop
```
#### Troop.create
```
curl -i -X  POST -H "Content-Type: application/json" --data '{"name":"{name}","mission":"{mission}","gold":{gold}}' http://localhost:8080/pa165/rest/troop/create
```
#### Troop.update
```
curl -i -X POST -H "Content-Type: application/json" --data '{"id":{id},"name":"{name}","mission":"{mission}","gold":{gold}}' http://localhost:8080/pa165/rest/troop/update
```
#### Troop.delete
```
Troop.delete: curl -i -X DELETE http://localhost:8080/pa165/rest/troop/{id}
```
