PRACTICA 2

Esta practica trata de realizar los test de toda la practica anterior (los tres features), y debemos
implementar una nueva feature llamada categoria, en la cual, debemos asignar esta para formar grupos
de tareas.

Realizamos los test a partir de dos archivos. El archivo ApplicationSpec en el cual controla todas las
entradas, las salidas, estado, etc en el controlador o incluso una capa mas por encima en la tabla de 
routes. El archivo ModelSpec en el cual controla todo lo relacionado con el archivo modelo, ya sea
la entrada que le metemos como parametro, las salidas de las consultas o ejecuciones y comprobar los
estados de estas acciones.

Basicamente se utiliza los test para comprobar sobretodo el estado y lo que contiene dicho resultado
de la accion bien sea en formato json o en cadena en el caso del controlador. Para las entradas bien 
se puede llamar directamente a los metodos de la clase controlador o bien llamar desde una capa mas 
arriba en el archivo routes.

En el caso del modelspec es igual pero llamo directamente desde la capa de modelo e interactuo con 
la base de datos creando diferentes tareas o lo que pidan las siguientes features:


FEATURE 1

Se comprueban todos los casos de uso de las diferentes funcionalidades de esta feature, es decir,
comprobamos si funciona bien mediante test y las diferentes salidas que tendra en caso de que
no se realice bien la comprobacion o bien no se encuentre en la base de datos. En esta rama se 
controla crear una tarea, consultar la tarea, listar todas las tareas y borrar la tarea especificada.

FEATURE 2 

En el siguiente caso debemos cumplir que funcione con el usuario todas las implementaciones, asi que,
los test ya se complican y formamos cadenas mas largas.

FEATURE 3

En este caso debemos realizar los mismo que en el feature2 pero con la fecha que la unimos a los test
mas los atributos de las anteriores.

CATEGORIA

Como dice la practica debemos crear una tabla categoria en la cual cada tarea tenga una categoria
pudiendo tener esta categoria muchas tareas y segun entendi en el enunciado una tarea solo puede tener
una categoria. 

En mi caso los test que implemento son modificar y listar la lista de categorias, creando ya una categoria
por defecto por si no se especificara que es "descatalogados", registro algunas categorias mas de ejemplo
como en el caso de usuario.

En los test, compruebo que la tarea tiene la categoria que se busca con usuario o sin usuario (controlo
ambas opciones) con la opcion GET. En el caso de registrarlas y modificarlas seria con la opcion POST
ya sea con usuario o sin este. 

CAMBIOS EN LA BASE DE DATOS

La relacion que he hecho es de 3 tablas (task, task_user, categoria) en las cuales las 3 se relacionan entre si,
con cada una con su respectiva id que se ira incrementando gracias a la secuencia especifica que se crea.

NOTA: Hay una prueba especifica en fecha que raramente falla la primera vez que se le pasan los test pero luego 
al volver a pasarlos si que la pasa.