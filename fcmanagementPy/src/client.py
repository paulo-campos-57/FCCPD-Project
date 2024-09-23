import pika

url = "amqps://ahpmdfzr:peFA7z9DlvjLU6N_IgUGg2U6g_r9xo-f@prawn.rmq.cloudamqp.com/ahpmdfzr"
params = pika.URLParameters(url)

connection = pika.BlockingConnection(params)
channel = connection.channel()