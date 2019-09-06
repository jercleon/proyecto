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

$correo = $_GET['correo'];
$password = $_GET['password'];
$consulta = "SELECT * FROM Usuario WHERE correo = '$correo' AND password = '$password'";
$resultado = $con -> query($consulta);

while($fila=$resultado -> fetch_array()){
	$producto[] = array_map('utf8_encode',$fila);
}

echo json_encode($producto);
$resultado -> close();

?>
