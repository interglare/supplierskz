<?php

	require "init.php";
	
	$id=$_GET["id"];
	$name=$_GET["name"];
	$email=$_GET["email"];
	$password=$_GET["password"];
	$contact=$_GET["contact"];
	$address=$_GET["address"];

	$sql="update users set name='$name',email='$email',password='$password',contact='$contact',address='$address' where id='$id'"; 
	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>