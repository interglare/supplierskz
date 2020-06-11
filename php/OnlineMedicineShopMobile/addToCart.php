<?php

require "init.php";

$userId=$_GET["userId"];
$stockId=$_GET["stockId"];
$name=$_GET["name"];
$qtn=$_GET["qtn"];
$cost=$_GET["cost"];

// $sql="select * from cart where userId = '$userId' and stockId = '$stockId'";
// $result=mysqli_query($con,$sql);
// if(mysqli_num_rows($result)>0){
// 	$status="exist";
// }
// else{
	$sql="insert into cart (userId,stockId,name,qtn,cost) 
	values('$userId','$stockId','$name','$qtn','$cost');";
	if(mysqli_query($con, $sql)){
		$status="inserted";
	}
	else{
		$status="not inserted";
	}
// }
echo json_encode(array("response"=>$status));
mysqli_close($con);

?>