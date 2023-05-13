#!/bin/bash
#===========================================================================================
# System Configuration
#===========================================================================================
cygwin=false
darwin=false
os400=false
case "`uname`" in
CYGWIN*) cygwin=true;;
Darwin*) darwin=true;;
OS400*) os400=true;;
esac
error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/opt/taobao/java
[ ! -e "$JAVA_HOME/bin/java" ] && unset JAVA_HOME

if [ -z "$JAVA_HOME" ]; then
  if $darwin; then

    if [ -x '/usr/libexec/java_home' ] ; then
      export JAVA_HOME=`/usr/libexec/java_home`

    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      export JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
  else
    JAVA_PATH=`dirname $(readlink -f $(which javac))`
    if [ "x$JAVA_PATH" != "x" ]; then
      export JAVA_HOME=`dirname $JAVA_PATH 2>/dev/null`
    fi
  fi
  if [ -z "$JAVA_HOME" ]; then
        error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
  fi
fi
#===========================================================================================
# JVM Configuration
#===========================================================================================
export SERVER="openjob-server"
export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`cd $(dirname $0)/..; pwd`
export CUSTOM_ADDITIONAL_LOCATION=${OPENJOB_CONFIG_PATH:="file:${BASE_DIR}/conf/"}

JVM_XMX=$JVM_XMX
JVM_XMS=$JVM_XMS
JVM_XMN=$JVM_XMN
JVM_XSS=$JVM_XSS
JVM_MetaspaceSize=$JVM_MetaspaceSize
JVM_MaxMetaspaceSize=$JVM_MaxMetaspaceSize
JVM_MaxDirectMemorySize=$JVM_MaxDirectMemorySize

JAVA_OPT="${JAVA_OPT} -server -Xmx${JVM_XMX:="2048m"} -Xms${JVM_XMS:="2048m"} -Xmn${JVM_XMN:="1024m"} -Xss${JVM_XSS:="512k"} -XX:MetaspaceSize=${JVM_MetaspaceSize:="128m"} -XX:MaxMetaspaceSize=${JVM_MaxMetaspaceSize:="256m"} -XX:MaxDirectMemorySize=${JVM_MaxDirectMemorySize:=1024m} -XX:-OmitStackTraceInFastThrow -XX:-UseAdaptiveSizePolicy"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"

JAVA_MAJOR_VERSION=$($JAVA -version 2>&1 | sed -E -n 's/.* version "([0-9]*).*$/\1/p')
if [[ "$JAVA_MAJOR_VERSION" -ge "9" ]] ; then
  JAVA_OPT="${JAVA_OPT} -Xlog:gc*:file=${BASE_DIR}/logs/openjob_gc.log:time,tags:filecount=10,filesize=100m"
else
  JAVA_OPT="${JAVA_OPT} -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8  -XX:-UseParNewGC"
  JAVA_OPT_EXT_FIX="-Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${JAVA_HOME}/lib/ext"
  JAVA_OPT="${JAVA_OPT} -Xloggc:${BASE_DIR}/logs/openjob_gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
fi

JAVA_OPT="${JAVA_OPT} -Dopenjob.home=${BASE_DIR}"
JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/target/${SERVER}.jar"
JAVA_OPT="${JAVA_OPT} ${JAVA_OPT_EXT}"
JAVA_OPT="${JAVA_OPT} --spring.config.additional-location=${CUSTOM_ADDITIONAL_LOCATION}"
JAVA_OPT="${JAVA_OPT} --logging.config=${BASE_DIR}/conf/logback.xml"
JAVA_OPT="${JAVA_OPT} --server.max-http-header-size=524288"

# check logs path
if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

echo "$JAVA $JAVA_OPT_EXT_FIX ${JAVA_OPT}"
echo "openjob is starting"

# check the start.out log output file
if [ ! -f "${BASE_DIR}/logs/start.out" ]; then
  touch "${BASE_DIR}/logs/start.out"
fi

# start
echo "$JAVA $JAVA_OPT_EXT_FIX ${JAVA_OPT}" > ${BASE_DIR}/logs/start.out 2>&1 &
if [[ "$JAVA_OPT_EXT_FIX" == "" ]]; then
  nohup "$JAVA" ${JAVA_OPT} openjob.openjob >> ${BASE_DIR}/logs/start.out 2>&1 &
else
  nohup "$JAVA" "$JAVA_OPT_EXT_FIX" ${JAVA_OPT} openjob.openjob >> ${BASE_DIR}/logs/start.out 2>&1 &
fi

echo "openjob is starting，you can check the ${BASE_DIR}/logs/start.out"