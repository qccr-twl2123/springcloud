### docker ��������
* docker ��װ
```text
ɾ���ɰ汾
yum remove -y docker docker-common docker-selinux docker-engine

��װ
yum install docker 

����
service docker start
chkconfig docker on

��docker ת�Ƶ������Ŀ¼
mkdir /data/dockerData/
mv /var/lib/docker /data/dockerData/
ln -s /data/dockerData/docker /var/lib/docker

#LCTT ��ע���˴������˾�ʽ�� sysv �﷨�������CentOS 7��֧�ֵ���ʽ systemd �﷨�����£�
systemctl  start docker.service
systemctl  enable docker.service
systemctl restart docker

�й����������
vi  /etc/docker/daemon.json
#��Ӻ�
{
    "registry-mirrors": ["https://registry.docker-cn.com"],
    "live-restore": true
}

```
* ���񹹽���������
```text
mvn package docker:build

docker build --rm -t user-service .
```
* ɾ��ȫ���Ѿ�ֹͣ������
```text
1.List all exited containers
docker ps -aq -f status=exited


2.Remove stopped containers
docker ps -aq --no-trunc -f status=exited | xargs docker rm
```
* ɾ��none����
```text
docker rmi $(docker images | grep "none" | awk '{print $3}') 
```

