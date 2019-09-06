<?php

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

  $nombres = $_POST['nombres'];
  $correo = $_POST['correo'];
  $password = $_POST['password'];
  $telefono = $_POST['telefono'];
  $id_sector = $_POST['id_sector'];

  $query= "INSERT INTO Usuario (nombres, correo, password,telefono,id_sector) VALUES ('$nombres','$correo','$password','$telefono','$id_sector')";
echo $query;
mysqli_query($con, $query);
  mysqli_close($con);
echo "HOLA";
?>
