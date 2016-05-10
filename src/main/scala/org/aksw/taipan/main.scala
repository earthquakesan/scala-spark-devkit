package main

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import table.DresdenTable

object Main {
  def main(args : Array[String]) : Unit = {
    val appName = "Taipan"
    //val master = "spark://localhost:7077"
    val conf = new SparkConf().setAppName(appName)//.setMaster(master)
    val sc = new SparkContext(conf)

    val dresdenTable = new DresdenTable()

    //val logFile = "hdfs://namenode:8020/user/hue/EEGID.csv"
    //val logData = sc.textFile(logFile, 2).cache()
    val logData = dresdenTable.getTablesRDD(sc)
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}
