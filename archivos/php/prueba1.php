<?php
 // dht11.php
 //Importamos la configuracion
 require("config.php");
 // Leemos los valores que nos llegan por GET
 $id =mysqli_real_escape_string($con, $_GET['id']);
 $nombre_sector = mysqli_real_escape_string($con, $_GET['nombre_sector']);
 $tarifa_m3_agua = mysqli_real_escape_string($con, $_GET['tarifa_m3_agua']);
 // EInsertamos los valores en la tabla
 $query = "INSERT INTO Sector(id, nombre_sector,tarifa_m3_agua) VALUES ('$id','$nombre_sector','$tarifa_m3_agua')";
 // Ejecutamos la instruccion
 mysqli_query($con, $query);
 mysqli_close($con);
 echo "Pagina para subir los datos<br />";
?>

