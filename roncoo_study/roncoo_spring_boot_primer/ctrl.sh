#!/bin/sh
#生产环境可以参考dubbo官方的控制脚本

SERVICE_DIR=`dirname $0`/target
SERVICE_NAME=roncoo_spring_boot_primer
SPRING_PROFILES_ACTIVE=test
#立即执行shell命令，结果赋值给PROCESS_ID
PROCESS_ID=`ps -ef | grep -w "${SERVICE_DIR}/${SERVICE_NAME}" | grep -w "java" | grep -v "grep" | awk '{print $2}'`

case "$1" in
	start)
		if [ "${PROCESS_ID}" = "" ];
		then
			echo "start ${SERVICE_NAME} ..."
			if [ "$2" != "" ];
			then
				SPRING_PROFILES_ACTIVE=$2
			fi
			echo "spring.profiles.active=${SPRING_PROFILES_ACTIVE}"
			nohup java -Xms128m -Xmx256m -jar ${SERVICE_DIR}/${SERVICE_NAME}-*\.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE} >/dev/null 2>&1 &
			echo "start ${SERVICE_NAME} success"
		else
			echo "${SERVICE_NAME} is start"
		fi
		;;
	stop)
		if [ "${PROCESS_ID}" = "" ];
		then
			echo "${SERVICE_NAME} is stop"
		else
			kill ${PROCESS_ID}
			sleep 1
			#再次查询进程是否存在，不存在则PROCESS_ID为空
            PROCESS_ID=`ps -ef | grep -w "${SERVICE_DIR}/${SERVICE_NAME}" | grep -w "java" | grep -v "grep" | awk '{print $2}'`
			if [ "${PROCESS_ID}" = "" ];
			then
				echo "${SERVICE_NAME} stop success"
			else
				kill -9 ${PROCESS_ID}
				echo "${SERVICE_NAME} stop error"
			fi
		fi
		;;
	restart)
		$0 stop
		sleep 1
		$0 start $2
		;;
	*)
		$0 stop
		sleep 1
		$0 start $2
		;;
esac
