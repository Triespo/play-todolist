PRACTICA 1

La práctica consiste en hacer una API REST en el lenguaje de SCALA en la cual siguiendo la práctica
guiada anterior debemos ampliar las funcionalidades de la lista de tareas situado en la vista. Dividida
en tres features.

FEATURE 1

Debemos consultar una tarea especificada por el usuario. En la pestaña de /conf/routes del proyecto
debemos especificar la ruta específica en la cual le pasaremos los parámetros URL en los cuales
para consultar alguna cosa en la base de datos será de tipo GET y en caso de que queramos guardar
algo en la base de datos se deberá usar POST (deberemos usar POSTMAN para saber el funcionamiento).
En este caso debemos consultar una tarea especifica entonces llamamos desde routes al controlador
mandándole la dirección y se deberá crear una action para controlar el proceso correcto y los errores
el controlador llamará a la funcion creada en el modulo para implementar la acción y a su vez tratará
con la base de datos para hacer las diferentes consultas o modificaciones necesarias.

Entonces si ponemos los parámetros de la url especificados en la route deberá salir la tarea agregada
por el usuario en la vista o url. A continuación se realizará guardar una tarea siguiendo el mismo proceso
anterior pero en este caso es un POST y en la base de datos en vez de una consulta se realizará una
modificación.

Un tema importante es el formato en el que debemos mostrar las tareas. En este caso se debe mostrar en 
JSON en la cual deberemos implementar los atributos de la entidad con JSPath para poder parsearlo.

Por último deberemos borrar una tarea mediante su id que le pasamos donde en routes deberemos especificarla
con el parametro :clave puniendo su valor. DELETE debe ser tratado con POSTMAN ya que en la url del navegador
solo podremos utilizar GET. 

FEATURE 2

En este feature se debe realizar las mismas acciones pero en este caso debemos asignar un usuario por
tanto ya serían dos tablas las que habría que crear como son TAREA creada anteriormente y ahora USUARIO
para identificar de quien es la tarea. En caso de que no se especifique o el usuario la introduzca
mediante el boton de la vista deberá agregarse a un usuario anónimo nombrado como por defecto (habrá
que especificarlo en la base de datos).

FEATURE 3

Creamos un atributo más para la tarea, en este caso es la fecha de pedido. Deberemos
añadir un formato en la fecha para poder parsearlo de cadena al formato exigido en mi caso es yyyy-MM-dd
o viceversa. Primero será de cadena al formato anterior ya que el usuario deberá insertarlo en la url.

Por último aclarar que se deben controlar todas las excepciones mediante try/catch y los códigos de estados
del navegador que en mi caso solo utilizo NOTFOUND.
