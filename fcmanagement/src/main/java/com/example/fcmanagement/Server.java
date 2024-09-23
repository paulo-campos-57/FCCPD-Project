package com.example.fcmanagement;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

public class Server {

    private final static String QUEUE = "messages";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // change to RabbitMQ server address

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE, false, false, false, null);

            System.out.println("Server started. Type the message: ");

            Scanner in = new Scanner(System.in);
            String message;

            while (true) {
                System.out.println("Type the message to the client (or type exit to leave)...");
                message = in.nextLine();

                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("Server Finished");
                    break;
                }
            }

            in.close();
        }
    }
}
