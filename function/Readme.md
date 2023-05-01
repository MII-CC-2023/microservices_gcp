# Cloud Functions con Python 

Este código se corresponde con una function en Python que se activará cada vez que se guarde un nuevo objeto en un Bucket y procederá a insertar los datos en la base de datos.
Requerira que tengamos un Bucket en Google Cloud Storage y un fichero env_variables.yaml con la configuración de la base de datos.

### Estructura

```
|- main.py (código de la función)
`- requirements.py (fichero con las dependencias Python)
`- env_variables.yaml (no incluido, debes crearlo con la configuración de la BD)
`- Readme.md (este fichero)
```

## requirements.txt

```
google-cloud-storage
mysql-connector-python
```

## main.py

```python
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
```

## env_variables.yaml

No incluido para evitar exponer información sensible en un repositorio. Debes crearlo con:

```yaml
DB_HOST: "..."
DB_NAME: "..."
DB_USER: "..."
DB_PASS: "..."
```