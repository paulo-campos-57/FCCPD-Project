import pika
import utils
import time

def callback(ch, method, properties, body):
    print(f"Received message: {body.decode()}")

def receive():
    try:
        connection = pika.BlockingConnection(
            pika.ConnectionParameters(
                host='prawn-01.rmq.cloudamqp.com',
                virtual_host='ahpmdfzr',
                credentials=pika.PlainCredentials('ahpmdfzr', 'peFA7z9DlvjLU6N_IgUGg2U6g_r9xo-f')
            )
        )
        channel = connection.channel()

        # Declare a headers exchange
        channel.exchange_declare(exchange='headers_logs', exchange_type='headers')

        # Create a queue for the consumer
        result = channel.queue_declare('', exclusive=True)
        queue_name = result.method.queue

        utils.print_menu()
        
        choice = int(input("Type the correspondent number to select a role: "))
        
        roles = {1: "player", 2: "coach", 3: "medical", 4: "janitor", 5: "social_media", 6: "financial"}
        role = roles.get(choice)
        
        if role:
            binding_key = {"role": role}  # Define the binding key
            channel.queue_bind(exchange='headers_logs', queue=queue_name, arguments=binding_key)
            print(f"Bound to role: {role}")
            time.sleep(2)
        else:
            print("Invalid role.")
            return

        utils.clear_terminal()

        print(f"{role} - Awaiting messages... To leave, press CTRL+C")
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
