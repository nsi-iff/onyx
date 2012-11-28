repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://maven.restlet.org'
repositories.remote << 'http://www.sparkjava.com/nexus/content/repositories/spark/'

JUNIT = 'junit:junit:jar:4.10'
HAMCREST = 'org.hamcrest:hamcrest-all:jar:1.1'
MOCKITO = 'org.mockito:mockito-all:jar:1.9.0'
SPARK = 'spark:spark:jar:0.9.9.3-SNAPSHOT'
COMMONS_IO = 'commons-io:commons-io:jar:2.4'
LUCENE = 'org.apache.lucene:lucene-core:jar:3.0.2'
LUCENE_ANALYZERS = 'org.apache.lucene:lucene-analyzers:jar:3.0.2'
HTTP_REQUEST = 'com.github.kevinsawicki:http-request:jar:3.0'
JACKSON = %w(core databind).map {|s| "com.fasterxml.jackson.core:jackson-#{s}:jar:2.1.1" }
COMMONS_CODEC = 'commons-codec:commons-codec:jar:1.7'

define 'image_search_server' do
  project.version = '0.1.0'
  compile.with Dir['lib/*.jar'], transitive(SPARK, LUCENE, LUCENE_ANALYZERS, 
    HTTP_REQUEST, JACKSON, COMMONS_CODEC)
  test.with transitive(JUNIT, HAMCREST, MOCKITO, COMMONS_IO)
  package :jar
end