//package com.gaia.dataSource.common;
//
//
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//
//public class KafkaAppender extends AppenderBase<ILoggingEvent> {
//private String topic;
//private String brokerList;
//private Producer<String,String> producer;
//
//    @Override
//    public void start() {
//        super.start();
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
//
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        // 如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性
//        props.put(ProducerConfig.RETRIES_CONFIG, 0);
//
//        // Request发送请求，即Batch批处理，以减少请求次数，该值即为每次批处理的大小
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//
//        /**
//         * 这将指示生产者发送请求之前等待一段时间，希望更多的消息填补到未满的批中。这类似于TCP的算法，例如上面的代码段，
//         * 可能100条消息在一个请求发送，因为我们设置了linger(逗留)时间为1毫秒，然后，如果我们没有填满缓冲区，
//         * 这个设置将增加1毫秒的延迟请求以等待更多的消息。 需要注意的是，在高负载下，相近的时间一般也会组成批，即使是
//         * linger.ms=0。在不处于高负载的情况下，如果设置比0大，以少量的延迟代价换取更少的，更有效的请求。
//         */
//        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
//
//        /**
//         * 控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。
//         * 当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阈值通过max.block.ms设定， 之后它将抛出一个TimeoutException。
//         */
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
//
//        // 用于配置send数据或partitionFor函数得到对应的leader时，最大的等待时间，默认值为60秒
//        // HH 警告：如无法连接 kafka 会导致程序卡住，尽量不要设置等待太久
//        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 0);
//
//
//        // 消息发送的最长等待时间
//        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 100);
//
//        // 0：不保证消息的到达确认，只管发送，低延迟但是会出现消息的丢失，在某个server失败的情况下，有点像TCP
//        // 1：发送消息，并会等待leader 收到确认后，一定的可靠性
//        // -1：发送消息，等待leader收到确认，并进行复制操作后，才返回，最高的可靠性
//        props.put(ProducerConfig.ACKS_CONFIG, "0");
//
//        this.producer = new KafkaProducer<String, String>(props);
//    }
//
//    @Override
//    public void stop() {
//        super.stop();
//        this.producer.close();
//    }
//
//    @Override
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    protected void append(ILoggingEvent iLoggingEvent)
//    {
//        String message = iLoggingEvent.getMessage();
//        producer.send(new ProducerRecord(topic, message));
//    }
//
//    public String getTopic() {
//        return topic;
//    }
//
//    public void setTopic(String topic) {
//        this.topic = topic;
//    }
//
//    public String getBrokerList() {
//        return brokerList;
//    }
//
//    public void setBrokerList(String brokerList) {
//        this.brokerList = brokerList;
//    }
//
//}