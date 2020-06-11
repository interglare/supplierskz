<?php

require "init.php";
$userId=$_GET["userId"];

$sql="select * from orders where userId = '$userId' and seen=0";
$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>0){
	$status="exist";
}
else{
	$status="not exist";
}
echo json_encode(array("response"=>$status));
mysqli_close($con);

?>