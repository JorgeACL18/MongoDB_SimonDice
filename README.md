# SharedPreferences en el juego Simón Dice

Para realizar esta tarea, tuvimos que hacer que nuestro juego de Simón dice guarde una serie de datos, en este caso nuestro mejor nivel y cuando se se alcanzó aquel nivel.

Con SharedPreferences se pueden guardar dichos datos.

## Clase Datos.kt:
Dentro de esta clase solo tenemos que crear las variables que guardará nuestro SharedPreferences:

<img width="435" height="210" alt="Captura desde 2025-12-09 09-06-54" src="https://github.com/user-attachments/assets/be495851-7626-4408-9170-ed06b7e57648" />

## MyViewModel:
Dentro de esta clase es donde tendremos todo lo que hace que nuestro programa funcione, por lo tanto aquí crearemos las funciones del SharedPreferences.

Lo primero que hay que hacer es crear la clase MyViewModelFactory, lo que permite la utilización del SharedPreferences.

<img width="1122" height="203" alt="Captura desde 2025-12-09 09-22-17" src="https://github.com/user-attachments/assets/64eb5476-3025-4602-9fc9-528b2bf67304" />


Después, creamos las variables `_recordNivel` y `_recordFecha` como MutableStateFlow para almacenar el nivel y la fecha y creamos otras variables `recordNivel` y `recordFecha` que llaman a las variables anteriores para poder usarlas en la IU.

<img width="1122" height="187" alt="Captura desde 2025-12-09 09-23-00" src="https://github.com/user-attachments/assets/4238609c-9124-4aee-91d7-35c835278555" />


Por último, dentro de la función `perderJuego()` se guardan los nuevos datos de nuestro nuevo récord, editando las variables que teníamos anteriormente.

<img width="1015" height="362" alt="Captura desde 2025-12-09 09-24-01" src="https://github.com/user-attachments/assets/3ae160e1-1d04-4fb4-9f53-6bd3b6646921" />


## IU
Para poder ver cual es nuestro récord cuando queramos jugar, tenemos que añadir las variables que creamos (`recordNivel` y `recordFecha`) en el MyViewModel dentro de la clase IU.

Es una tarea sencilla, solo tenemos que introducir estas variables dentro de la clase pirncipal `IU()` y después llamarlas.

<img width="1058" height="531" alt="Captura desde 2025-12-09 09-30-24" src="https://github.com/user-attachments/assets/ba7be7ef-9ca3-4fee-851e-96c973aec5c2" />

## MainActivity
Para acabar nuestro SharedPreferences, solo tenemos que ir a la MainActivity y hacer que use la clase MyViewModelFactory.

<img width="929" height="289" alt="Captura desde 2025-12-09 09-32-44" src="https://github.com/user-attachments/assets/43e7b0b8-77de-47df-a204-715a2daf0f4b" />

## Resultado:
El juego se debería ver ahora así:

- Antes de empezar el juego:

  
<img width="390" height="835" alt="Captura desde 2025-12-09 08-59-41" src="https://github.com/user-attachments/assets/3cb0872a-071a-4818-be0a-eab1021c9364" />


- Después de jugar y obtener un nuevo récord:


<img width="390" height="835" alt="Captura desde 2025-12-09 09-00-14" src="https://github.com/user-attachments/assets/6fb18892-8671-4089-aa41-9a19b2a3fd6c" />


- Al obtener el nuevo récord, si vamos al SharedPreferences, se debe ver algo así:


<img width="555" height="113" alt="Captura desde 2025-12-09 09-35-33" src="https://github.com/user-attachments/assets/12d1f551-b157-4f2c-b344-036c7e364c41" />





