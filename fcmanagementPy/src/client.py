import pika

def callback(ch, method, properties, body):
    print(f"Mensagem recebida: {body.decode()}")

def recieve():
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))  # Use o IP/host do RabbitMQ
    channel = connection.channel()
    
    channel.queue_declare(queue='messages')
    
    channel.basic_consume(queue='messages', on_message_callback=callback, auto_ack=True)
    
    print("Awaiting messages... to leave, press CTRL+C")
    channel.start_consuming()

if __name__ == '__main__':
    recieve()
