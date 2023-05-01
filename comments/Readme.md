# App Engine con Spring Boot

Este código se corresponde con una aplicación en Spring que consiste en un visualizador de las imágenes registradas en la base de datos que previamente fueron subidas al Bucket de Google Cloud Storage. La aplicación será desplegada como el servicio por defecto (default) de Google App Engine. 

## Estructura
La estructura se corresponde con una aplicación Spring Boot MVC.

### Modelos
Se han implementado dos entidades: images (src/main/java/edu/cc/carruse/images/) y comments (src/main/java/edu/cc/carruse/images/), con sus capas de acceso a datos (DAO) y de servicios (Service).

### Controlador

Se ha creado un controlador (ImagesController.java) que atenderá las siguientes peticiones:

@GetMapping("/"): obtendrá todas las imágenes de la base de datos y lanzará la vista index.html

@GetMapping("/see/{id}"): localizará la imagen por id y mostrará la vista de detalles see_image.html, que muestra los comentarios y un formulario para añadir comentarios

@PostMapping("/savecomment"): recibirá un comentario para una imagen, lo persitirá en la base de datos y volverá a mostrar la vista de detalles de la imagen

### Vistas

Se ha utilizado Thymeleaf como sistema de plantillas. Se ha utilizado Boostrap.

#### index.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
	<title>Index</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>

<body>
	<div class="col-8 mx-auto">
		<h1>Inicio</h1>
		<p th:text="${mensaje}"></p>

		<div class="d-flex flex-wrap p-2" th:if="${images!=null and !images.empty}">
			<div th:each="img : ${images}" class="card col-2 m-1" style="width: 18rem;">
				<img class="card-img-top" width="200px"
					th:src="@{https://storage.googleapis.com/}+${img.bucket}+@{/}+${img.name}" alt="image cap">
				<div class="card-body">
					<h5 th:text="${img.title}" class="card-title">Card title</h5>
					<p th:text="${img.descrip}" class="card-text">Some quick example text.</p>
					<a th:href="@{/see/} + ${img.id}" th:text="Comments" class="btn btn-primary">Comments</a>
				</div>
			</div>
		</div>
    
		<p th:if="${images==null or images.empty}">No hay Imágenes</p>

	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
		crossorigin="anonymous"></script>
</body>

</html>
```



#### see_image.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Images</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>

<body>

    <div class="col-6 mx-auto">
        <h1>Imagen</h1>
        <a class="btn btn-primary btn-block" th:href="@{/}">Back to index</a>
        <p th:text="${mensaje}"></p>


        <img class="img-thumbnail rounded" width="400px"
            th:src="@{https://storage.googleapis.com/}+${img.bucket}+@{/}+${img.name}" />

        <div class="list-group">
            <!-- <p class="list-group-item">ID: <span th:text="${img.id}">ID: id</span></p> -->
            <p class="bg-dark list-group-item"><span class="d-inline-block col-2 text-warning">Bucket:</span> <span class="text-info" th:text="${img.bucket}">Bucket: bucket</span></p>
            <p class="bg-dark list-group-item"><span class="d-inline-block col-2 text-warning">File:</span> <span class="text-info" th:text="${img.name}">File: file</span></p>
            <p class="bg-dark list-group-item"><span class="d-inline-block col-2 text-warning">Title:</span> <span class="text-info" th:text="${img.title}">File: file</span></p>
            <p class="bg-dark list-group-item"><span class="d-inline-block col-2 text-warning">Description:</span> <span class="text-info" th:text="${img.descrip}">File: file</span></p>
            <p class="bg-dark list-group-item"><span class="d-inline-block col-2 text-warning">URL:</span> <span class="text-info" th:text="@{https://storage.googleapis.com/}+${img.bucket}+@{/}+${img.name}">File: file</span>
            </p>

        </div>

        <hr style="height:2px; width:100%; border-width:0; color:blue; background-color:blue">

        <h2>Comentarios</h2>
        <ul th:if="${comments!=null and !comments.empty}">
            <li class="card text-white bg-success p-1 mb-3" th:each="c : ${comments}">
                <span th:text="${c.texto}">Comment</span>
        </ul>
        <p th:if="${comments==null or comments.empty}">No hay comentarios por ahora!</p>

       
        <hr style="height:2px; width:100%; border-width:0; color:blue; background-color:blue">
        <h2>Añadir Comentario</h2>



        <form th:action="@{/savecomment}" method="post" th:object="${comment}">

            <input type="hidden" name="id" th:field="*{id}" />

            <input type="hidden" name="image_id" th:value="${img.id}" />

            <div class="form-outline mb-4">
                <label class="form-label" for="titulo">Comment</label>
                <input class="form-control" type="text" id="texto" name="texto" th:field="*{texto}" required />
            </div>
            <p><input class="btn btn-primary btn-block" type="submit" value="Enviar" /></p>
        </form>

        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
</body>

</html>
```



