package:
	mvn package

exec:
	mvn exec:java -Dexec.mainClass=jips.Main

run:
	java -jar target/jips.jar

clean:
	mvn clean

mdpdf-install:
	npm install mdpdf -g

readme:
	mdpdf README.md

sonar-docker:
	docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube

sonar:
	mvn sonar:sonar