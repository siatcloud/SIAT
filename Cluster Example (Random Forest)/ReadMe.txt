Step 1: Add a plugin in pom.xml

				<plugin>
					<artifactId>maven-compiler-plugin</artifactId>
					<version>3.1</version>
					<configuration>
						<fork>true</fork>
						<executable>C:\Program Files\Java\jdk1.8.0_191\bin\javac.exe</executable>
					</configuration>
				</plugin>


Step 2: Run as --> Maven Build Clean
Step 3: Run as --> Maven Build Install

Run in Cluster
Upload file in HDFS 
if data link do not found by the program then copy every data to the same naming directory
master node - Desktop/abir/data
node 1 		- Desktop/abir/data
node 2		- Desktop/abir/data

siat.cluster.RandomForestApps
Run Command
./bin/spark-submit --class siat.cluster.RandomForestApps.java --master yarn-cluster --num-executors 3 --driver-memory 512m --executor-memory 512m --executor-cores 3 siat/abirJars/Cluster-0.0.1-SNAPSHOT.jar

./bin/spark-submit --class siat.cluster.RandomForestApps --master yarn-cluster 
--num-executors 7 --driver-memory 1G --executor-memory 1G --executor-cores 2 siat/abirJars/Cluster-0.0.1-SNAPSHOT.jar




************** Console Link ************** 
http://node1.siat.kr/console/
userName: node1
password: Siat#123
******************************************


************** Spark Path ******************
cd /usr/hdp/current/spark2-client
********************************************

************** Run Command ************** 
./bin/spark-submit --class siat.cluster.RandomForestApps --master yarn-cluster --num-executors 7 --driver-memory 1G --executor-memory 1G --executor-cores 3 hdfs:///
data/abir/Cluster-0.0.1-SNAPSHOT.jar
*****************************************

