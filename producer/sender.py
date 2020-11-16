import datetime
import re
import time
import random
import pika
import json
import time

patient_ids = [35, 46, 56, 65, 13]


def read_activities(filename):
    file = open(filename, 'r')
    lines = file.readlines()

    activities = []

    for line in lines:
        words = re.split(r'\t+', line)

        start = int(time.mktime(datetime.datetime
                                .strptime(words[0],
                                          "%Y-%m-%d %H:%M:%S").timetuple())) * 1000
        end = int(time.mktime(datetime.datetime.strptime(words[1],
                                                         "%Y-%m-%d  %H:%M:%S")
                              .timetuple())) * 1000
        activity = words[2]
        patient_id = random.choice(patient_ids)

        activity_data = {"patientId": str(patient_id),
                         "activity": activity,
                         "start": start,
                         "end": end}

        activities.append(activity_data)

    return activities


def connect():
    connection = pika.BlockingConnection(
        pika.URLParameters('amqps://bhkvvipl:8xp9j8hFYhykEKHwOdIyhjHByGqOvtis@roedeer'
                           '.rmq.cloudamqp.com/bhkvvipl'))
    channel = connection.channel()

    channel.queue_declare(queue='sensor-activities-queue')

    return channel, connection


def send_data(channel, data):
    for data_piece in data:
        json_data = json.dumps(data_piece)
        channel.basic_publish(exchange='apothecary-exchange',
                              routing_key='activity.sleeping',
                              body=str(json_data))
        print("Sent", json_data)
        time.sleep(1)


def close_connection(connection):
    connection.close()


if __name__ == '__main__':
    data = read_activities("activity.txt")
    channel, connection = connect()
    send_data(channel, data)
    close_connection(connection)

    # body = '{"patientId": "35",' \
    #        '"activity": "Sleeping",' \
    #        '"start": 2,' \
    #        '"end": 5}'
    #
    # connection = pika.BlockingConnection(
    #     pika.ConnectionParameters(host='localhost'))
    # channel = connection.channel()
    #
    # channel.queue_declare(queue='sensor-activities-queue')
    #
    # channel.basic_publish(exchange='apothecary-exchange', routing_key='activity.sleep',
    #                       body=body)
    # print(" [x] Sent 'Hello World!'")
    # connection.close()
