# cURL commands for testing of REST controller


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

