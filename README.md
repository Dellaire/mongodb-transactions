#### Setting up _MongoDB_

Start the server and create a replica set:
```
> mongod --dbpath <path_to_data_folder> --replSet rs
```

Or just start a _Docker_ container:
```
> docker run -p 27017:27017 mongo:4.0.4 --replSet rs
```

Connect with a client to the server, initiate the replica set and the collection:
```
> mongo
> rs.initiate()
> use test
> db.createCollection("data")
```