$A = 4, 10, 15, 20, 22, 25, 27, 30, 32, 35, 37, 40
$B = 4, 10, 15, 20, 22, 25, 27, 30, 32, 35, 37, 40

foreach ($element in $A) {
  $fileA = "C:\Users\jantosovic\Documents\KOP\data\2\ZKW"+ $element +"_sol.dat"
  $input = "C:\Users\jantosovic\Documents\KOP\data\2\ZKW"+ $element+ "_inst.dat"

  java -jar KOP.jar $input $fileA
}

