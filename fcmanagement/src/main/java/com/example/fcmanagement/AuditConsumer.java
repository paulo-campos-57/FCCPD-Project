package com.example.fcmanagement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class AuditConsumer extends Thread {

    private final static String QUEUE = "audit_queue"; // Nome personalizado para a fila
    private final static String EXCHANGE = "headers_logs"; // Nome do exchange
    private final Channel channel;

    public AuditConsumer(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {

            // Declaração da fila com tipo de header
            Map<String, Object> headers = new HashMap<>();
            headers.put("x-match", "any"); // Pode ser "any" ou "all" dependendo da lógica de filtragem
            headers.put("role", "audit"); // Configuração do cabeçalho para a fila de auditoria

            channel.queueDeclare(QUEUE, true, false, false, null);
            channel.queueBind(QUEUE, EXCHANGE, "", headers); // Associar a fila ao exchange com os headers

            // Callback para processar mensagens recebidas
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received message for audit: " + message);

                // Registrar a mensagem em um arquivo de log
                logMessage(message);
            };

            // Consumir mensagens da fila
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {
            });
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
