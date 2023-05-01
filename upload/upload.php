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