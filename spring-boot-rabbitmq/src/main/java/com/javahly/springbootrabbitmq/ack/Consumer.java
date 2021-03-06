package com.javahly.springbootrabbitmq.ack;

import com.javahly.springbootrabbitmq.quick.RabitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :hly
 * @github :https://github.com/huangliangyun
 * @blog :http://www.javahly.com/
 * @CSDN :blog.csdn.net/Sirius_hly
 * @date :2020/4/3
 * @QQ :1136513099
 * @desc :
 */
public class Consumer {

    private static final String QUEUE_NAME = "first_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接
        Connection connection = RabitMQConnection.getConnection();
        // 2.设置通道
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");

                //手动ACK模式
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println("消费者获取消息:" + msg);
            }
        };
        // 3.fals e表示设置为手动应答，关闭默认应答
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }
}
