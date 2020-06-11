<?php

	require "init.php";

	$sql="select * from stock";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'title'=>$row['name'],
	  	'image' =>base64_encode($row['image']),'description' =>$row['info']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>