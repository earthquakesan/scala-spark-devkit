package table

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext

class DresdenTable {
  def getTablesFilePath() : String = {
    val tablesFile = "hdfs://namenode:8020/user/hue/dwtc-001.json-with-headers.json"
    return tablesFile
  }

  def getTablesRDD(sc : SparkContext) : RDD[String] = {
    val tablesFilePath = getTablesFilePath()
    return sc.textFile(tablesFilePath).cache()
  }

  def fetchFirstLine(tables : RDD) : String = {
    
  }
}
