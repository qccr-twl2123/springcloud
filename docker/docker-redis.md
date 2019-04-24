### docker redis ʹ�ý̳�
* docker redis 
```text
docker pull  redis:3.2
```

* docker redis install
```text
docker run -p 6379:6379 -v $PWD/data:/data  -d redis:3.2 redis-server --appendonly yes

docker run -p 6379:6379 --privileged=true --name myredis -v /home/redis/data/:/data -v /home/redis/redis.conf:/etc/redis/redis.conf -d redis:3.2 redis-server /etc/redis/redis.conf --appendonly yes

install
redis-server --appendonly yes : ?????????redis-server????????????redis????????
```

* redis-cli enter into console
```text
docker exec -it  <containerId> redis-cli
```

