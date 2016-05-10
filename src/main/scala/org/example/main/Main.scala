package main

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

object Main {
  def main(args : Array[String]) : Unit = {
    val appName = "Scala Spark Devkit"
    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)

    write("hdfs://namenode:8020/", "/user/hue/helloworld.txt", "Hello World!".getBytes)
    val logFile = "hdfs://namenode:8020/user/hue/helloworld.txt"
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("h")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with h: %s, Lines with b: %s".format(numAs, numBs))
  }

  def write(uri: String, filePath: String, data: Array[Byte]) = {
    System.setProperty("HADOOP_USER_NAME", "hue")
    val path = new Path(filePath)
    val conf = new Configuration()
    conf.set("fs.defaultFS", uri)
    val fs = FileSystem.get(conf)
    val os = fs.create(path)
    os.write(data)
    fs.close()
  }
}
