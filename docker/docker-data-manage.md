### docker ���ݹ���
```text
docker �������������ݾ���صĲ���
-v: ����һ�����ص�ַ,[host-dir]:[container-dir]:[rw|ro]
-volumes-from: mount all volumes from a given container
```
* ������ʽ
```text
1. ֱ���������ڲ�����,��ָ��host-dir(û������ָ��,hostĿ¼Ҳ��ɾ��)
docker run -v /test1

2.����HostĿ¼��Ϊ���ݾ� 
docker run -v /data/volumes:/volumes <ImageName> /bin/bash
 
```