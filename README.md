# Requirements

This application template was built and tested on Ubuntu 14.04. [Docker-engine](https://docs.docker.com/engine/installation/linux/ubuntulinux/) and [docker-compose](https://docs.docker.com/compose/install/) should be installed.

# Getting started

Clone the repository including all the submodules (--recursive option):
```
git clone --recursive https://github.com/earthquakesan/scala-spark-devkit
cd scala-spark-devkit
```

Another way to init submodules is to use ```git submodule init``` and ```git submodule update``` commands.

Create "hadoop" network and start the HDFS/Spark Workbench:
```
docker network create hadoop
make start-workbench
```

This will start HDFS/Spark workbench in foreground with logging available in your terminal. As the starting order of containers are not ensured, datanode1 and datanode2 might fail to connect to namenode. In such a case restart them using:
```
docker restart datanode1 datanode2
```

In a new terminal you can run template application now:
```
make
```

This command will execute ```sbt package``` first and then submit the jar to the running HDFS/Spark Workbench.
The default application will create a file in HDFS and count 'h' and 'b' characters per line, output should be (among tons of logging messages):
```
Lines with h: 0, Lines with b: 0
```

To upload your custom data to HDFS you can use HDFS FileBrowser (navigate to http://yourdockerhost:8088/home).
Or start interactive docker container with /home/ivan/mydatafolder mounted at /data as follows:
```
docker run -it --rm --net hadoop -e CORE_CONF_fs_defaultFS=hdfs://namenode:8020 --name hadoop-load --volume /home/ivan/mydatafolder/:/data/ earthquakesan/hadoop-spark bash
```
And upload files using hadoop CLI:
```
cd /data
hadoop fs -copyFromLocal dwtc-001.json-with-headers.json /user/hue/
```

# Customization
## build.sbt

The build.sbt definition is setup to work with Spark 1.6.1 and Hadoop 2.7.1. The same versions are running inside HDFS/Spark workbench. You might add dependencies to other scala libraries to .sbt definition (refer to [sbt documentation](http://www.scala-sbt.org/))

## Makefile

The variables at the top of Makefile, in particularly application_jar have to be adapted when you change the project name:
```
CORE_CONF_fs_defaultFS = hdfs://namenode:8020
container_name = spark-submit-application
docker_network = hadoop
docker_hadoop_spark_image = earthquakesan/hadoop-spark
application_jar = /data/target/scala-2.10/scala-spark-devkit_2.10-0.1.0.jar
```

## Port bindings

To change port bindings for HDFS FileBrowser and other services edit docker-hadoop-spark-workbench/docker-compose.yml.

# Contributors

Ivan Ermilov [twitter](https://twitter.com/earthquakesan)
