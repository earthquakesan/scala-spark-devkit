default:
	sbt package
	docker run -it --rm --net hadoop -e CORE_CONF_fs_defaultFS=hdfs://namenode:8020 --name spark-submit-application --volume $(shell pwd):/data/ bde2020/hadoop-spark ./bin/spark-submit --class main.Main --master spark://spark-master:7077 /data/target/scala-2.10/taipan_2.10-0.1.0.jar

load-data:
	docker run -it --rm --net hadoop -e CORE_CONF_fs_defaultFS=hdfs://namenode:8020 --name hadoop-load --volume /home/ivan/datahdd/tables/:/data/ bde2020/hadoop-spark bash
	#hadoop fs -copyFromLocal dwtc-001.json-with-headers.json /user/hue/
