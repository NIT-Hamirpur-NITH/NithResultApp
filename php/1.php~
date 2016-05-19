<?php
$conn = new mysqli("localhost","root","justgoogleit","results");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$roll = $_POST['roll'];
$sem = $_POST['semester'];
//$roll="IIITU14113";
//$sem = "IIITU141132";
$query = "select * from subjects where roll_no='$roll' and semester_no = '$sem';";
$result = $conn->query($query);

if($result){
	$count = 0;
	$res = array();
	while($row = $result->fetch_assoc()){
		$temp = array("subject" => $row['subject_name'], "ocr"=>$row['ObtainCR'], "tcr"=>$row['TotalCR']);
		array_push($res, $temp);
		$count++;
		
	}
	$f_res = array("result" => $res);
	echo json_encode($f_res);
	
} else{
	echo "error in fetching";
}

?>
