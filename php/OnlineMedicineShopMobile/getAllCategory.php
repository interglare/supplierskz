<?php

	require "init.php";

	$sql="select * from category";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'name' =>$row['name'],
	  	'image' =>base64_encode($row['image'])));
	}
	echo json_encode($response);
	mysqli_close($con);

?>