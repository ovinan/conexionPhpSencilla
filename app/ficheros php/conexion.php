<?php
    $conexion=mysqli_connect("localhost","root","","tabla");
    if(!$c){
    	echo mysqli_error($conexion);
    	return;
    }
    else{echo ("OK");}
    
?>
