package com.example.fcmanagement;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.FileWriter;
import java.io.IOException;

public class AuditConsumer extends Thread {
    private final static String QUEUE = "messages";
    private final Channel channel;

    public AuditConsumer(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            // Callback para processar mensagens recebidas
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received message for audit: " + message);

                // Registrar a mensagem em um arquivo de log
                logMessage(message);
            };

            // Consumir mensagens da fila
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para registrar as mensagens em um arquivo de log
    private void logMessage(String message) {
        try (FileWriter writer = new FileWriter("audit_log.txt", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
