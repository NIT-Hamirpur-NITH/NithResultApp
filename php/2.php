<?php
$conn = new mysqli("localhost","root","justgoogleit","results");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$branch = $_POST['branch'];
$order = $_POST['order'];
$year = $_POST['year'];

//$year='2';
//$branch='7';
//$order="rank";
if($year=='1'){
	$year = '5';
} else if($year == '2'){
	$year='4';
} else if($year=='3'){
	$year='3';
} else if($year == '4'){
	$year='2';
} else{
	die("invalid year");
}
$iiitu=0;
if($branch=='5'){
	$iiitu = '1';
}
if($branch=='4'){
	$iiitu='2';
}
if($order=="rank"){
	
	$query = "SELECT * FROM students WHERE (roll_no like '__$branch%' or roll_no like '__MI$branch%' or roll_no like 'IIITU__$iiitu%')AND (roll_no LIKE '_$year%' or roll_no like 'IIITU1$year%')ORDER BY cgpi desc";

} else if($order == "roll"){
	$query = "SELECT * FROM students WHERE (roll_no like '__$branch%' or roll_no like '__MI$branch%' or roll_no like 'IIITU__$iiitu%')AND (roll_no LIKE '_$year%' or roll_no like 'IIITU1$year%')ORDER BY roll_no asc";

}
$result = $conn->query($query);
if($result){
	$res = array();
	$i=1;
	while($row = $result->fetch_assoc()){
		$temp = array("rank"=>$i,"rollno"=>$row['roll_no'],"CGPI" => $row['cgpi'],"name"=>$row['name']);
		$i++;
		array_push($res, $temp);
	}
	$f_res = array("result" => $res);
	echo json_encode($f_res);
	
} else{
	echo "error in fetching";
}


?>
