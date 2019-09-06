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

$mes= $_GET['mes'];
$m = $mes."-2019";
$consulta = "SELECT * FROM sistema_riego WHERE date_format(fecha, '%m-%Y') = '$m'";
$resultado = $con -> query($consulta);

while($fila=$resultado -> fetch_array()){
        $producto[] = array_map('utf8_encode',$fila);
}

echo json_encode($producto);
$resultado -> close();

?>



