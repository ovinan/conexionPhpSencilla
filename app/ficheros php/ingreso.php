<?php
	include 'conexion.php';

	$nombre=$_GET["nombre"];
	$apellido=$_GET["apellido"];
	$direccion=$_GET["direccion"];
	$telefono=$_GET["telefono"];
	$correo=$_GET["correo"];
	$sexo=$_GET["sexo"];
	//$fecha=$_GET["fecha"];
	$consulta = "INSERT INTO usuarios(nombre, apellido, direccion, telefono, correo, sexo) VALUES ('$nombre', '$apellido', '$direccion', '$telefono', '$correo', '$sexo')";
	$q=mysqli_query($conexion,$consulta);

if($q){
    echo "Registro insertado exitoso";
}
else{
    echo "Fallo en el registro";
}
?>
