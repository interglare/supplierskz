<?php

require "init.php";
$email=$_GET["email"];
$password=$_GET["password"];

$sql="select * from users where email = '$email' and password='$password'";
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