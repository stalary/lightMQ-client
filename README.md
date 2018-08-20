# lightMQ-client
lightMQ的客户端
使用步骤:
1. 注册topic或者group(默认生成master分组)
```
http://120.24.5.178:8001/registerTopic?topic=test
http://120.24.5.178:8001/registerGroup?topic=test&group=slave
```
2. 在需要使用的项目中使用maven引入
```
<dependency>
    <groupId>com.stalary</groupId>
    <artifactId>lightmqclient</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
3. 实现MQConsumer接口，自定义消息处理，@MQListener设置需要监听的topic
```
@Component
public class MyConsumer implements MQConsumer {
    @Override
    @MQListener(topics = {"test"})
    public void process(MessageDto messageDto) {
        System.out.println("receive message: " + messageDto);
    }
}
```
4. 注入Producer生产者，进行消息的发送
```
@Resource
private Producer producer;
producer.send("test", "123");
```

