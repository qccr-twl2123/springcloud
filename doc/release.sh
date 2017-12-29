#!/bin/bash
shell_log="/var/log/jkweb_shell.log"

echo -e "\n###################   `date +%F_%T`   ###############\n" >>$shell_log
cd /home/cfd/trj-jk-web

cat /dev/null >/tmp/gitlog.txt
echo -e "\n### Script exe at `date +%F/%T` by `who am i|awk '{print $1" "$2" "$5}'` ###\n" >>$shell_log

git remote update origin --prune

read -p "【jk-web测试环境更新--】请输入需要切换的分支:" BRANCH
if [ "$BRANCH" == "" ] ;then
		git checkout test >/tmp/gitlog.txt
        git pull origin test >/tmp/gitlog.txt
  elif  echo $BRANCH ;then
		git checkout $BRANCH
        git pull origin $BRANCH >/tmp/gitlog.txt
  else
        echo "输入内容不符合要求,程序退出."
        exit 1
fi

if [ $? -eq 0 ]
 then
   cat /tmp/gitlog.txt | tee -a $shell_log
   echo  -e "\e[32;1m OK\e[0m GIT update" |tee -a $shell_log
 else
   cat /tmp/gitlog.txt | tee -a $shell_log
   echo  -e "\e[31;5m Fail\e[0m GIT update" |tee -a $shell_log
   exit 1
fi

\cp -f /home/configbak/application.yml src/main/resources/
#\cp -f /home/configbak/generatorConfig.xml src/main/resources/
p1=`cat pidf`
p=`expr $p1 - 1`
if [ -n "`ps aux |grep $p |grep -v 'grep'`" ];then
    kill -9 $p >/dev/null 2>&1
    echo -e "\e[32;1m OK\e[0m stop jkweb-server-8080"
    else
    echo -e "jkweb-server-8080 not run"
fi

sleep 2
#nohup mvn spring-boot:run > ../log 2>&1 &
export MAVEN_OPTS="-Xms512m -Xmx2048m -XX:PermSize=256m -XX:MaxPermSize=512m"
nohup mvn clean spring-boot:run | tee ../log 2>&1 &

echo $!>pidf
p1=`cat pidf`
p=`expr $p1 - 1`
echo "sleeping about 20 seconds for starting jkweb-server ........"
sleep 20

#remove changed files
git checkout src/main/resources/application.yml

if [ `netstat -tnpl | grep $p | wc -l` -gt 0 ]
  then
    echo  -e "\e[32;1m OK\e[0m start jkweb-server-8080" # |tee -a $admlogs
  else
    echo  -e "\e[31;5m Fail\e[0m start jkweb-server-8080" # |tee -a $admlogs
fi