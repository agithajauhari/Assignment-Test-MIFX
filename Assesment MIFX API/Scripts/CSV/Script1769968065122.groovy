import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import com.kms.katalon.core.configuration.RunConfiguration

// 1. Send GET request
def response = WS.sendRequest(findTestObject('Get user by id'))

// 2. Parse JSON response
def json = new JsonSlurper().parseText(response.getResponseBodyContent())

// 3. Tentukan path CSV di folder project
def projectDir = RunConfiguration.getProjectDir()
def csvFilePath = projectDir + "/users.csv"  // simpan di root project

// 4. Header CSV
def header = "First Name,Last Name,Email\n"

// 5. Create or overwrite CSV file
Files.write(Paths.get(csvFilePath), header.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)

// 6. Loop through data dan append ke CSV
json.data.each { user ->
    def line = "${user.first_name},${user.last_name},${user.email}\n"
    Files.write(Paths.get(csvFilePath), line.getBytes(), StandardOpenOption.APPEND)
}

println "CSV generated successfully at: ${csvFilePath}"