<?php

	require "init.php";

	$sql="select * from cart";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'userId'=>$row['userId'],'stockId' =>$row['stockId'],'name' =>$row['name'],'qtn' =>$row['qtn'],'cost' =>$row['cost']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>