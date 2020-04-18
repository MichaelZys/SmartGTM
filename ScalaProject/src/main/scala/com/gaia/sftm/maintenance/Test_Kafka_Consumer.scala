package com.gaia.sftm.maintenance

import java.util.{Collections, Properties}
import java.time.Duration
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object Test_Kafka_Consumer {
  def main(args: Array[String]): Unit = {
    // 配置信息
    val prop = new Properties
    prop.put("bootstrap.servers", "192.168.0.103:6667")
    // 指定消费者组
    prop.put("group.id", "ting1")
    // 指定消费位置: earliest/latest/none
    prop.put("auto.offset.reset", "earliest")
    // 指定消费的key的反序列化方式
    prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    // 指定消费的value的反序列化方式
    prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    //enable.auto.commit means that offsets are committed automatically with a frequency controlled by the config auto.commit.interval.ms.
    prop.put("enable.auto.commit", "true")
    prop.put("auto.commit.interval.ms","1000")
    prop.put("session.timeout.ms", "30000")
    // 得到Consumer实例
    val kafkaConsumer = new KafkaConsumer[String, String](prop)
    // 首先需要订阅topic
    kafkaConsumer.subscribe(Collections.singletonList("linys"))
    // 开始消费数据
    while (true) {
      // 如果Kafak中没有消息，会隔timeout这个值读一次。比如上面代码设置了2秒，也是就2秒后会查一次。
      // 如果Kafka中还有消息没有消费的话，会马上去读，而不需要等待。
      // kafkaConsumer.poll(2000) 已经被迭代
      val msgs: ConsumerRecords[String, String] = kafkaConsumer.poll(2000)
//      val msgs: ConsumerRecords[String, String] = kafkaConsumer.poll(Duration.ofMillis(2000))
      println(msgs.count())
      val it = msgs.iterator()
      while (it.hasNext) {
        val msg = it.next()
        println(s"partition: ${msg.partition()}, offset: ${msg.offset()}, key: ${msg.key()}, value: ${msg.value()}, time: ${msg.timestamp()}")
      }
    }
  }
}
