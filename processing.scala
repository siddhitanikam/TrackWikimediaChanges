import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.kafka.common.serialization.StringDeserializer

object DBUtils {
  def getConnection(): java.sql.Connection = {
    val url = "jdbc:sqlserver://localhost:1433;databaseName=wikimedia;"
    val username = "wikimedia_user"
    val password = "wikimedia_pwd"
    DriverManager.getConnection(url, username, password)
  }
}

object KafkaSparkStreaming {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("KafkaSparkStreaming").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "myGroup",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("wikimediaChange")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    val eventCounts = stream
      .map(record => (record.value.split(",")(0), 1))
      .reduceByKey(_ + _)

    eventCounts.foreachRDD { rdd =>
      rdd.foreachPartition { partitionOfRecords =>
        val connection = DBUtils.getConnection() // Assuming DBUtils is a utility class to manage JDBC connection
        partitionOfRecords.foreach { record =>
          val stmt = connection.createStatement()
          stmt.executeUpdate(s"INSERT INTO event_counts (event_type, count) VALUES ('${record._1}', ${record._2})")
          stmt.close()
        }
        connection.close()
      }
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
