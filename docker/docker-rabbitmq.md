### docker rabbitmq �̳�

* install
```text
docker run -d --name myrabbitmq -p 5673:5672 -p 15673:15672 docker.io/rabbitmq:3-management
testing: http://ip:15367  
```
* enter docker container
```text
docker exec -it rabbitmq /bin/bash
```