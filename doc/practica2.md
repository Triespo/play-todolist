PRACTICA 2

Esta practica trata de realizar los test de toda la practica anterior (los tres features), y debemos
implementar una nueva feature llamada categoria, en la cual, debemos asignar esta para formar grupos
de tareas.

FEATURE 1

Se comprueban todos los casos de uso de las diferentes funcionalidades de esta feature, es decir,
comprobamos si funciona bien mediante test y las diferentes salidas que tendra en caso de que
no se realice bien la comprobacion o bien no se encuentre en la base de datos. En esta rama se 
controla crear una tarea, consultar la tarea, listar todas las tareas y borrar la tarea especificada.

Realizamos los test a partir de dos archivos. El archivo ApplicationSpec en el cual controla todas las
entradas, las salidas, estado, etc en el controlador o incluso una capa mas por encima en la tabla de 
routes. El archivo ModelSpec en el cual controla todo lo relacionado con el archivo modelo, ya sea
la entrada que le metemos como parametro, las salidas de las consultas o ejecuciones y comprobar los
estados de estas acciones. 