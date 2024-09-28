import pika
import sys


def callback(ch, method, properties, body):
    print(f"Received message: {body.decode()}")


def receive_messages(name, role):
    try:
        connection = pika.BlockingConnection(
            pika.ConnectionParameters(
                host='prawn-01.rmq.cloudamqp.com',
                virtual_host='ahpmdfzr',
                credentials=pika.PlainCredentials(
                    'ahpmdfzr', 'peFA7z9DlvjLU6N_IgUGg2U6g_r9xo-f')
            )
        )
        channel = connection.channel()

        channel.queue_declare(queue='messages')

        channel.basic_consume(
            queue='messages', on_message_callback=callback, auto_ack=True)

        print(
            f"Hello, {name}. Here you will receive messages related to your role: {role}")
        print("Awaiting messages... Press CTRL+C to exit.")
        channel.start_consuming()

    except pika.exceptions.AMQPConnectionError as e:
        print(f"Connection error: {e}")
    except KeyboardInterrupt:
        print("Operation finished by user.")
    finally:
        if 'connection' in locals() and connection.is_open:
            connection.close()


if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Usage: python receiver.py <name> <role>")
    else:
        name = sys.argv[1]
        role = sys.argv[2]
        receive_messages(name, role)
