### mongodb 

* install 
```text
docker run -p 27017:27017 -v $PWD/db:/data/db -d mongo:3.2
```
* set username and password
```text
docker exec -it mongometeor /bin/bash


1. mongo

2. db.createUser({user: "gyj",pwd: "secret",roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]})

3.db.auth("gyj","secret")
```

* give db auth to user
```text
mongo admin -u gyj -p secret

db.createUser(
 {
  user: "meteorblog",
  pwd: "secret",
  roles: [
     { role: "readWrite", db: "test" }
  ]
 }
)
```
