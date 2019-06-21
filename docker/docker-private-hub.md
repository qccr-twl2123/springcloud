### docker ˽�вֿ�
* ˽�п�
```text
1.���ؾ���
  docker pull registry
2.��������
  docker run -d -p 5000:5000 --restart=always --name=registry-srv -v /mydata/dockerRegistry:/var/lib/registry registry
  ��������:
     -d����̨����
     -p����������5000�˿�ӳ�䵽��������5000�˿�
     --restart��docker������������������������
     --name������������
     -v���������ڵ�/var/lib/registryӳ�䵽��������/mydata/dockerRegistryĿ¼
3.��֤
   http://ip:5000/v2/_catalog
4.ָ��˽�п�
   ִ��: vi /etc/docker/daemon.json
   ����: "insecure-registries":["47.105.131.1:5000"]    
   
5.����docker: systemctl restart docker      
```
* ˽�п�webҳ��
```text
1.���ؾ���
    docker pull hyper/docker-registry-web
2.��������
    docker run -it -p 8080:8080 --restart=always --name registry-web --link registry-srv -e REGISTRY_URL=http://registry-srv:5000/v2 -e REGISTRY_NAME=localhost:5000 hyper/docker-registry-web
    ��������:
         -it: �Խ���ģʽ����
         --link��������������(registry-srv)���ڴ������У�ʹ��registry-srv��ͬ��registry-srv�����ľ�������ַ
         -e�����û�������
```

* �ϴ�����˽�п�
```text
1. �Ƚ�������������: ip:5000/<imageName>
   docker tag cbbing/hcharts 192.168.1.87:5000/cbbing/hcharts
2. docker push <��������ľ���>
```