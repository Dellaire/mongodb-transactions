#### Setting up _MongoDB_

Start the server and create a replica set:
```
> mongod --dbpath <path_to_data_folder> --replSet rs
```

Connect with a client and initiate the replica set:
```
> mongo
> rs.initiate()
```