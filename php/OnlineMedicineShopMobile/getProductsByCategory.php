<?php

	require "init.php";
	$type=$_GET["type"];

	$sql="select * from stock where type='$type'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'type'=>$row['type'],'name' =>$row['name'],
	  	'image' =>base64_encode($row['image']),'unit' =>$row['unit'],'qtn' =>$row['qtn'],
	  	'price1' =>$row['price1'],'price2' =>$row['price2'],'sells' =>$row['sells']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>