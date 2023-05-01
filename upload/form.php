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