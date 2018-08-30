<?php
$servername = "127.0.0.1";
$username = "s1153631";
$password = "s1153631";
$dbname = "d1153631";
try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
        die("OOPs something went wrong");
    }

?>

