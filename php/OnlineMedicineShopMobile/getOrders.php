<?php

	require "init.php";

	$userId=$_GET["userId"];
	$status=$_GET["status"];

	$sql="select * from orders where userId='$userId' and status='$status'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'userId'=>$row['userId'],
	  	'qtn'=>$row['qtn'],'cost'=>$row['cost'],'orderDate' =>$row['orderDate'],'deliveryDate' =>$row['deliveryDate'],'deliverySystem' =>$row['deliverySystem'],'payment' =>$row['payment'],'status' =>$row['status'],'seen' =>$row['seen']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>