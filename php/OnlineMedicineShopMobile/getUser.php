<?php

	require "init.php";
	$email=$_GET["email"];
	$password=$_GET["password"];
	$sql="select * from users where email='$email' and password='$password'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'name' =>$row['name'],'email' =>$row['email'],
	  'password'=>$row['password'],'contact'=>$row['contact'],'address'=>$row['address']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>