import pika
import utils

def callback(ch, method, properties, body):
    print(f"Received message: {body.decode()}")


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

        # Declara uma exchange do tipo 'topic'
        channel.exchange_declare(exchange='topic_logs', exchange_type='topic')

        # Cria uma fila exclusiva para o consumidor
        result = channel.queue_declare('', exclusive=True)
        queue_name = result.method.queue

        utils.print_menu()
        
        choice = int(input("Type the correspondent number to select a role: "))
        
        roles = {1: "Player", 2: "Coach", 3: "Medical", 4: "Janitors", 5: "Social Media", 6: "Financial"}
        role = roles.get(choice)
        
        if role:
            binding_key = f"{role.lower()}.*"  # Define um padrão de chave de roteamento baseado no papel
            channel.queue_bind(exchange='topic_logs', queue=queue_name, routing_key=binding_key)
            print(f"Bound to role: {role}")
        else:
            print("Invalid role.")
            return

        utils.clear_terminal()

        print("Awaiting messages... To leave, press CTRL+C")
        channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)
        channel.start_consuming()

    except pika.exceptions.AMQPConnectionError as e:
        print(f"\nConnection error: {e}")
    except KeyboardInterrupt:
        print("\nOperation finished by user.")
    finally:
        if 'connection' in locals() and connection.is_open:
            connection.close()


if __name__ == '__main__':
    receive()
