<?php
$conn = new mysqli("localhost","root","justgoogleit","results");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$roll = $_POST['roll'];
//roll="14588";
$query = "select * from semesters where roll_no='$roll'";
$query2 = "select * from students where roll_no='$roll'";
$result = $conn->query($query);
$result2 = $conn->query($query2);
if($result){
	$count = 0;
	$res = array();
	$row2 = $result2->fetch_assoc();
	while($row = $result->fetch_assoc()){
		$temp = array("SGPI" => $row['sgpi'], "CGPI"=>$row['cgpi'], "Semester Number"=>$row['semester_no']);
		array_push($res, $temp);
		$count++;
		
	}
	//print_r($row2);
	$f_res = array("name"=>$row2['name'],"result" => $res);
	echo json_encode($f_res);
	
} else{
	echo "error in fetching";
}

?>
