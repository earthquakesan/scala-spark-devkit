CORE_CONF_fs_defaultFS = hdfs://namenode:8020
container_name = spark-submit-application
docker_network = hadoop
docker_hadoop_spark_image = earthquakesan/hadoop-spark
application_jar = /data/target/scala-2.10/scala-spark-devkit_2.10-0.1.0.jar

default:
	sbt package
	docker run -it --rm --net $(docker_network) -e CORE_CONF_fs_defaultFS=$(CORE_CONF_fs_defaultFS) --name $(container_name) --volume $(shell pwd):/data/ $(docker_hadoop_spark_image) ./bin/spark-submit --class main.Main --master spark://spark-master:7077 $(application_jar)

start-workbench:
	docker network create hadoop
	docker-compose -f docker-hadoop-spark-workbench/docker-compose.yml up
