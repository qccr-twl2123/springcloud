#!/bin/bash

BM_Path='/var/www/BranchManager'
shell_log='/var/log/BranchManager.log'

cd $BM_Path
cat /dev/null >/tmp/gitlog.txt
echo -e "\n### Script exe at `date +%F/%T` by `who am i|awk '{print $1" "$2" "$5}'` ###\n" >>$shell_log

#回退提交
git checkout .
git remote update origin --prune

read -p "【BranchManager环境更新--】请输入需要切换的分支:" BRANCH

if [ "$BRANCH" == "" ] ;then
        echo -e "没有输入分支，使用默认分支master"
        BRANCH='master'
fi

git branch | grep $BRANCH > /tmp/gitlog.txt

if [[ -s /tmp/gitlog.txt ]]
 then
   echo -e "分支$BRANCH已存在，现在进行更新" | tee -a $shell_log
   git checkout $BRANCH
   git pull origin $BRANCH | tee -a $shell_log
 else
   echo -e "分支$BRANCH不存在，现在进行创建" | tee -a $shell_log
   git checkout -b $BRANCH origin/$BRANCH | tee -a $shell_log
fi

if [ $? -eq 0 ]
 then
   echo  -e "\e[32;1m OK\e[0m GIT update" | tee -a $shell_log
 else
        echo  -e "\e[32;1m fail\e[0m GIT update" | tee -a $shell_log
        git add .
        git commit -m '提交合并冲突'
        git checkout master
        git branch -D $BRANCH
        git checkout $BRANCH
        echo "delete done,exit" | tee -a $shell_log
        exit 1
fi

p=`cat uwsgi.pid`
if [ -n "`ps aux |grep $p |grep -v 'grep'`" ];then
    kill -9 $p >/dev/null 2>&1
    echo -e "\e[32;1m OK\e[0m stop uwsgi"
    else
    echo -e "uwsgi not run"
fi

sleep 2
#拷贝配置文件
\cp -f /var/www/configbak/{wsgi.py,__init__.py,settings.py} $BM_Path/BranchManager

#更新用户权限
chown -R www-data:www-data $BM_Path
if [ $? -eq 0 ]
 then
   echo  -e "\e[32;1m OK\e[0m Modify files Owner" |tee -a $shell_log
 else
   echo  -e "\e[31;5m Fail\e[0m Modify files Owner" |tee -a $shell_log
fi

#启动uwsgi,已设置自动切换虚拟环境
#source /var/www/VENVBranch/bin/activate
source /var/www/VENV2.7/bin/activate
uwsgi --ini uwsgi.ini |tee -a $shell_log

p=`cat uwsgi.pid`
if [ `netstat -tnpl | grep $p | wc -l` -gt 0 ]
  then
    echo  -e "\e[32;1m OK\e[0m start uwsgi" |tee -a $shell_log
  else
    echo  -e "\e[31;5m Fail\e[0m start uwsgi" |tee -a $shell_log
fi



