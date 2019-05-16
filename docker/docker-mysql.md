### docker mysql�̳�

* ��װ
```text
docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
```
```text
-p 3306:3306���������� 3306 �˿�ӳ�䵽������ 3306 �˿ڡ�

-v -v $PWD/conf:/etc/mysql/conf.d����������ǰĿ¼�µ� conf/my.cnf ���ص������� /etc/mysql/my.cnf��

-v $PWD/logs:/logs����������ǰĿ¼�µ� logs Ŀ¼���ص������� /logs��

-v $PWD/data:/var/lib/mysql ����������ǰĿ¼�µ�dataĿ¼���ص������� /var/lib/mysql ��

-e MYSQL_ROOT_PASSWORD=123456����ʼ�� root �û������롣
```
* �ͻ�������ʧ�ܵ�bug
```text
client does not support authentication consider upgrading
```
```text
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '�������';
```
* mysql ���ñ���utf8
```text
1.��������: docker exec -it <����Id> /bin/bash 

2.�������Ŀ¼: cd /etc/mysql

3.�༭ vim my.cnf
��[mysqld] ���������������
character-set-server=utf8

ע: ���vim ����ʹ�ã��밴�����²�����а�װ.

��ʹ��docker����ʱ����ʱ�����û�а�װvim����vim����ʱ��ʾ˵��vim: command not found�����ʱ�����Ҫ��װvim�����ǵ�����apt-get install vim����ʱ����ʾ��
Reading package lists... Done
Building dependency tree       
Reading state information... Done
E: Unable to locate package vim
��ʱ����Ҫ�ã�apt-get update���������������ǣ�ͬ�� /etc/apt/sources.list �� /etc/apt/sources.list.d ���г���Դ���������������ܻ�ȡ�����µ��������
�ȸ�������Ժ��������apt-get install vim����ɡ�

apt-get update && apt-get install vim
apt-get update && apt-get install cron
```

* ����mysql ��̨����
```text
mysql -u �û��� -p֮������mysql���룬����mysql����̨
```

### ��������
* ����֮�乲������
```text
docker run --name mysql2 --volumes-from mysql -d mysql:5.7

mysql2 ����mysql����Ŀ¼
```
* ����,Ǩ��, �ָ�
```text
����
docker run --rm --volumes-from mysql -v $(pwd):/backup mysql:5.7 tar cvf /backup/backup.tar /var/lib/mysql

�ָ�
docker run --rm --volumes-from mysql -v $(pwd):/backup mysql:5.7 bash -c "cd /var/lib/mysql && tar xvf /backup.tar --strip 1"

--rm ��������һ�������꼴������������
--volumes-from ������һ�����������Ϲ��صľ���ص��´�����������
```

[mysql��װ�̳�](https://www.linuxidc.com/Linux/2016-09/135288.htm)

[docker mysql ����](https://www.binss.me/blog/learn-docker-with-me-about-volume/)
