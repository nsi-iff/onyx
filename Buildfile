repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://maven.restlet.org'

JUNIT = 'junit:junit:jar:4.10'
HAMCREST = 'org.hamcrest:hamcrest-all:jar:1.1'
MOCKITO = 'org.mockito:mockito-all:jar:1.9.0'
RESTLET = 'org.restlet.jse:org.restlet:jar:2.1-M3'
COMMONS_IO = 'commons-io:commons-io:jar:2.4'

define 'image_search_server' do
  project.version = '0.1.0'
  compile.with RESTLET, COMMONS_IO
  test.with JUNIT, HAMCREST, MOCKITO, COMMONS_IO
  package :jar
end