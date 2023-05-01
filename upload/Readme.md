# App Engine en PHP

Este código se corresponde con una aplicación en PHP que consiste en un formulario para subir imágenes a un Bucket de Google Cloud Storage. La aplicación será desplegada como un servicio de Google App Engine (llamado upload). Requerira que la cuenta de servicio asociada a App Engine tenga permisos para escribir en los buckets.

### Estructura de la aplicación

```
|--- static
|      | 
|      `- main.css (hoja de estilos propia)
`- app.yaml (configuración para App Engine)
`- composer.json (fichero de dependencias para PHP)
`- form.php (formulario)
`- index.php (Página principal)
`- upload.php (fichero para enviar la información y la imagen)
`- Readme.md (este fichero)
```

## app.yaml

```yaml
service: upload
runtime: php81

handlers:
- url: /static
  static_dir: static

manual_scaling:
  instances: 1
resources:
  cpu: 1
  memory_gb: 0.5
  disk_size_gb: 10
```

## index.php

```php
<?php
switch (@parse_url($_SERVER['REQUEST_URI'])['path']) {
    case '/':
        require 'form.php';
        break;
    case '/upload.php':
        require 'upload.php';
        break;        
    default:
        http_response_code(404);
        exit('Not Found');
}
?>
```
## form.php

```php
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FORM: Upload files</title>
    <link type="text/css" rel="stylesheet" href="/static/main.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous" />
</head>
<body>

<div class="col-4 mx-auto">
    
<h1>Upload Images</h1>

<form action="upload.php" method="POST" enctype="multipart/form-data">

    <div class="form-outline mb-4">
        <label class="form-label" for="titulo">Title</label>
        <input class="form-control" type="text" id="titulo" name="titulo" required />
    </div>
    <div class="form-outline mb-4">
        <label class="form-label" for="desc">Description</label>
        <textarea class="form-control" id="desc" name="desc" id="desc" rows="3"></textarea>
    </div>
    <div class="mb-2">
        <label for="formFile" class="form-label">Select image to upload:</label>
        <input class="form-control" type="file" name="fileToUpload" id="fileToUpload" required />
    </div>
    
    <input class="btn btn-primary btn-block" type="submit" value="Send" name="submit" />

</form>
</div>
</body>
</html>
```

## upload.php

```php
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FORM: Upload files</title>
    <link type="text/css" rel="stylesheet" href="/static/main.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous" />
</head>
<body>

<div class="col-4 mx-auto">

<h1> Upload file with PHP on Google Storage</h1>

<?php

require_once 'vendor/autoload.php';

use Google\Cloud\Storage\StorageClient;

$permitted_chars = '0123456789abcdefghijklmnopqrstuvwxyz';
$prefijo = substr(str_shuffle($permitted_chars), 0, 10);

$titulo = $_POST['titulo'];
$desc = $_POST['desc'];

if ($_FILES['fileToUpload']['error'] != 4) {
    printf("<p>Uploading file ");
    $fileContent = file_get_contents($_FILES["fileToUpload"]["tmp_name"]);
    $cloudPath = $prefijo . '-' . $_FILES["fileToUpload"]["name"];
    printf($cloudPath . "...</p>");

    try {
        $storage = new StorageClient();
    
        $bucketName = 'bucket-cc2023';
    
        $bucket = $storage->bucket($bucketName);
    
        $storageObject = $bucket->upload(
            $fileContent,
            ['name' => $cloudPath,
            ]);

        $storageObject->update([
                'metadata' => ['title' => $titulo, 'desc' => $desc],
            ]);
    } catch (Exception $e) {
        // maybe invalid private key ?
        print '<p>' . $e . '</p>';
    }
    
} else {
    printf("<p>Error: You must choose a file to upload</p>");
}

if ($storageObject != null) {
    printf("<p>OK: file uploads successly Title:".$titulo.".</p>");
} else {
    printf("<p>Error: upload file fails.</p>");
}


?>

<p><a class="btn btn-secondary btn-block" href="/">Back to home</a></p>

</div>
</body>
</html>
```
