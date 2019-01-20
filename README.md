# _MongoDB_ transaction demonstration

This repository contains examples, that demonstrate the usage of [multi document transactions in _MongoDB_](https://docs.mongodb.com/master/core/transactions/) within Java. These examples require a running instance of MongoDB at `localhost:27017` with an initiated replica set and an existing collection `data`.

### Setting up _MongoDB_

#### Manually

Start the server and create a replica set:
```
> mongod --dbpath <path_to_data_folder> --replSet rs

Connect with a client to the server and initiate the replica set and the collection:
```
> mongo
...
> rs.initiate()
> config = rs.config()
> config.members[0].host = "localhost:27017"
> rs.reconfig(config)
> use test
> db.createCollection("data")
```

#### Docker Compose

The folder `src/main/resources` contains a `docker-copose.yml`, which does the setup from above by starting _Docker_ containers.