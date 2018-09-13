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
<repositories>
    <repository>
        <id>oss</id>
	<name>oss</name>
	<url>https://oss.sonatype.org/content/groups/public</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.stalary</groupId>
    <artifactId>lightmqclient</artifactId>
    <version>1.1-SNAPSHOT</version>
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
5. 在application.properties中进行配置
```
com.stalary.lightmq.group=webflux // 消费分组，不配置则为消费默认分组
com.stalary.lightmq.url=http://120.24.5.178:8001 // 使用服务端的地址，建议自行构建lightmq
com.stalary.lightmq.consumer=false // 是否开启消费者，默认开启，适用于集群中的单点消费
com.stalary.lightmq.block=false // 是否阻塞模式消费，默认非阻塞
com.stalary.lightmq.order=false // 是否顺序生产消息，默认无序，采用异步消费
```
