<?php

require "init.php";
$userId=$_GET["userId"];
$qtn=$_GET["qtn"];
$cost=$_GET["cost"];
$orderDate=$_GET["orderDate"];
$deliveryDate=$_GET["deliveryDate"];
$deliverySystem=$_GET["deliverySystem"];
$payment=$_GET["payment"];
$status=$_GET["status"];
$seen=0;

$sql="insert into orders (userId,qtn,cost,orderDate,deliveryDate,deliverySystem,payment,status,seen) 
values('$userId','$qtn','$cost','$orderDate','$deliveryDate','$deliverySystem','$payment','$status','$seen');";

if(mysqli_query($con, $sql)){
	$status="inserted";
}
else{
	$status="not inserted";
}

echo json_encode(array("response"=>$status));
mysqli_close($con);

?>