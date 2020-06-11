<?php

require "init.php";
$orderid=$_GET["orderid"];
$msg=$_GET["msg"];

$sql="insert into review (orderid,msg) 
values('$orderid','$msg');";
if(mysqli_query($con, $sql)){
	$status="inserted";
}
else{
	$status="not inserted";
}

echo json_encode(array("response"=>$status));
mysqli_close($con);

?>