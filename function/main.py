import mysql.connector
from google.cloud import storage
import os

# ATTENTION 
# You must create the file env_variables.yaml with the next contents: 
# DB_HOST = ... (IP or DNS host)
# DB_NAME = ... (Database name)
# DB_USER = ... (Database user)
# DB_PASS = ... (Database password)
  
def insert_data(data, context):

    bucket_name = data['bucket']
    blob_name = data['name']

    client = storage.Client()
    bucket = client.get_bucket(bucket_name)
    blob = bucket.get_blob(blob_name)

    metadata = blob.metadata

    title = metadata['title']
    desc = metadata['desc']

    print("DATOS: {}, {}, {}".format(bucket_name, blob_name, title))

    host = os.environ["DB_HOST"]
    database = os.environ["DB_NAME"]
    user = os.environ["DB_USER"]
    password = os.environ["DB_PASS"]

    print("Connect DB: jdbc:mysql://{}:3306/{}, user:{}".format(host, database, user))
    
    mydb = mysql.connector.connect(host=host,
                            database=database,
                            user=user,
                            password=password)
    mycursor = mydb.cursor()
    sql = "INSERT INTO images (bucket, name, title, descrip) VALUES (%s, %s, %s, %s)"
    val = (bucket_name, blob_name, title, desc)
    mycursor.execute(sql, val)

    mydb.commit()
    mydb.close()