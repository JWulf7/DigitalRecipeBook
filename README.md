# DigitalRecipeBook
Digital recipe book API

To Switch DB Source:
- (un)comment files:
	- pom.xml
	- application.properties
	
	if you get this error after switching datasource:
		***************************
		APPLICATION FAILED TO START
		***************************
		
		Description:
		
		Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
		
		Reason: Failed to determine a suitable driver class
	- run command: mvn clean install
