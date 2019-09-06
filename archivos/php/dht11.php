<?php
 // dht11.php
 //Importamos la configuracion
// require("config.php");
 $dbhost = "localhost";
 $dbuser = "proyecto";
 $dbpass = "root1234";
 $dbname = "proyecto";
 //Conexion con la base de datos
 $con = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
if (!$con) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}
echo "conexion establecida<br />";

 // Leemos los valores que nos llegan por GET
 $duracion_riego = $_GET['duracion_riego'];
 $dispositivo = $_GET['dispositivo'];
 // EInsertamos los valores en la tabla
 $query = "INSERT INTO sistema_riego(fecha, hora, duracion_riego, dispositivo) VALUES(NOW(),NOW(),'$duracion_riego','$dispositivo')";
 // Ejecutamos la instruccion
 mysqli_query($con, $query);
 mysqli_close($con);
 echo "Pagina para subir los datos<br />";
?>
