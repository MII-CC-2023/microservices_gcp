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

```
$ cd comments
$ gcloud app deploy --version v1
```

## Upload

Es una App en PHP, con un formulario, que permite subir imágenes al Bucket, añadiéndole como metatados un título y una descripción.
Se desplegará como servicio <b>upload</b> en App Engine.

```
$ cd upload
$ gcloud app deploy --version v1
```

## Function

Es una función lambda, escrita en Python, que se activará cada vez que se cargue una nueva imagen en el Bucket, guardando la información en la base de datos.
Es necesario asignarle una cuenta de servicio que tenga acceso de escritura al bucket, por ejemplo la de la App Engine.

https://cloud.google.com/sdk/gcloud/reference/functions/deploy
```
gcloud functions deploy check-upload_bucket2 \
    --region europe-west6 \
    --runtime python311 \
    --entry-point insert_data \
    --trigger-event google.storage.object.finalize \
    --trigger-resource bucket-cc2023 \
    --env-vars-file env_variables.yaml
```
