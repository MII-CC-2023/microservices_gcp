import mysql.connector
from google.cloud import storage

  
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

    
    mydb = mysql.connector.connect(host='34.29.161.26',
                            database='cc2023db',
                            user='alumno',
                            password='alumno')
    mycursor = mydb.cursor()
    sql = "INSERT INTO images (bucket, name, title, descrip) VALUES (%s, %s, %s, %s)"
    val = (bucket_name, blob_name, title, desc)
    mycursor.execute(sql, val)

    mydb.commit()
    mydb.close()