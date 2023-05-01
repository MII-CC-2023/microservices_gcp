# Google App Engine y Cloud Functions

En este repositorio encontrarás el código para desplegar en Google App engine y Cloud Functions.


### Requisitos

- Cloud Storage: crea un Bucket en tu proyecto que tenga acceso de visualización (viewer) para todos los usuarios (allUsers)

- Base de datos SQL: No crees una para no incurrir en gastos, solicita credenciales al profesor para no tener que crear una instancia en tu proyecto

- App engine: Activa el servicio App Engine en tu proyecto. Esto creará una cuenta de servicio con permisos de Editor a los servicios del cloud.

En concreto se han creado los siguientes códigos

    1. Comments
    2. Upload
    3. Function

## Comments

Es una App en Spring que permite visualizar las imágenes, del bucket previamente guardas en la base de datos, y añadir comentarios a estas.
Se desplagará como el servicio por defecto (<b>default</b>) de App Engine.

Antes de desplegar la aplicación es necesario crear el fichero env_variables.yaml con la configuración de la Base de datos:

```
env_variables:
  DB_HOST: ...
  DB_NAME: ...
  DB_USER: ...
  DB_PASS: ...
```
Luego puedes proceder con el despliegue con:

```
$ cd comments
$ gcloud app deploy --version v1
```
Durante el despliegue

## Upload

Es una App en PHP, con un formulario, que permite subir imágenes al Bucket, añadiéndole como metatados un título y una descripción.
Se desplegará como servicio <b>upload</b> en App Engine.

Requiere que la base de datos contenga las tablas: images y comments

```
mysql> describe images;
+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int          | NO   | PRI | NULL    | auto_increment |
| bucket  | varchar(255) | YES  |     | NULL    |                |
| descrip | varchar(255) | YES  |     | NULL    |                |
| name    | varchar(255) | YES  |     | NULL    |                |
| title   | varchar(255) | YES  |     | NULL    |                |
+---------+--------------+------+-----+---------+----------------+

mysql> describe comments;
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| id       | int          | NO   | PRI | NULL    | auto_increment |
| texto    | varchar(255) | YES  |     | NULL    |                |
| image_id | int          | YES  | MUL | NULL    |                |
+----------+--------------+------+-----+---------+----------------+

```
Si aún no has ejecutado la aplicación anterior (comments) deberás crear las tablas con un cliente mysql, con un comando del tipo:

```
CREATE TABLE images (
     id INT NOT NULL AUTO_INCREMENT,
     bucket CHAR(255) NOT NULL,
	 name CHAR(100) NOT NULL,
	 title CHAR(100),
	 descrip CHAR(255),
     PRIMARY KEY (id)
);

CREATE TABLE comments (
     id INT NOT NULL AUTO_INCREMENT,
     texto CHAR(255) NOT NULL,
	 image_id INT NOT NULL,
     PRIMARY KEY (id),
	 FOREIGN KEY (image_id) REFERENCES images(id)
);
```


Una vez tengas las tablas, puedes desplegar con:

```
$ cd upload
$ gcloud app deploy --version v1
```

## Function

Es una función lambda, escrita en Python, que se activará cada vez que se cargue una nueva imagen en el Bucket, guardando la información en la base de datos.
Es necesario asignarle una cuenta de servicio que tenga acceso de escritura al bucket, por ejemplo la de la App Engine.

https://cloud.google.com/sdk/gcloud/reference/functions/deploy
```
gcloud functions deploy check-upload-bucket \
    --region europe-west6 \
    --runtime python311 \
    --entry-point insert_data \
    --trigger-event google.storage.object.finalize \
    --trigger-resource bucket-cc2023 \
    --service-account cloud-computing-384514@appspot.gserviceaccount.com \
    --env-vars-file env_variables.yaml
```
