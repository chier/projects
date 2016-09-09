
#!/bin/bash
tomcatpath="/home/zhangzl/webapp/webportal"


restart(){
        echo "restart $tomcatpath"
        kill -9 `ps -ef | grep "$tomcatpath" | grep -v grep | awk '{print ($2)}'`
}

restart