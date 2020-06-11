<?php

	require "init.php";
	
	$stockId=$_GET["stockId"];
	$qtn=$_GET["qtn"];

	$sql="update stock set qtn=qtn-'$qtn', sells=sells+'$qtn' where id='$stockId'"; 
	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>