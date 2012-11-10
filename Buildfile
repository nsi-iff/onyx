repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://maven.restlet.org'

JUNIT = 'junit:junit:jar:4.10'
HAMCREST = 'org.hamcrest:hamcrest-all:jar:1.1'
RESTLET = 'org.restlet.jse:org.restlet:jar:2.1-M3'

define 'image_search_server' do
  project.version = '0.1.0'
  compile.with RESTLET
  test.with JUNIT, HAMCREST
  package :jar
end