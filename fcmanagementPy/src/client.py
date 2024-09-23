import pika


def callback(ch, method, properties, body):
    print(f"Mensagem recebida: {body.decode()}")


def receive():
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

        print("Awaiting messages... to leave, press CTRL+C")
        channel.start_consuming()

    except pika.exceptions.AMQPConnectionError as e:
        print(f"Connection error: {e}")
    except KeyboardInterrupt:
        print("Finished by user.")
    finally:
        if 'connection' in locals() and connection.is_open:
            connection.close()


if __name__ == '__main__':
    receive()
