repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://maven.restlet.org'
repositories.remote << 'http://www.sparkjava.com/nexus/content/repositories/spark/'

JUNIT = 'junit:junit:jar:4.10'
HAMCREST = 'org.hamcrest:hamcrest-all:jar:1.1'
SPARK = 'spark:spark:jar:0.9.9.3-SNAPSHOT'
COMMONS_IO = 'commons-io:commons-io:jar:2.4'
LUCENE = 'org.apache.lucene:lucene-core:jar:3.0.2'
LUCENE_ANALYZERS = 'org.apache.lucene:lucene-analyzers:jar:3.0.2'
HTTP_CLIENT_FLUENT = 'org.apache.httpcomponents:fluent-hc:jar:4.2.2'
JACKSON = %w(core databind annotations).map {|s| "com.fasterxml.jackson.core:jackson-#{s}:jar:2.1.1" }
COMMONS_CODEC = 'commons-codec:commons-codec:jar:1.7'
SERVLET_API = 'javax.servlet:servlet-api:jar:2.5'

ALL = transitive [Dir['lib/*.jar'], SPARK, LUCENE, LUCENE_ANALYZERS, 
    JACKSON, COMMONS_CODEC, SERVLET_API].flatten
    
PROVIDED = [SERVLET_API]

define 'onyx' do
  project.version = '0.1.0'
  compile.with ALL
  test.with transitive(JUNIT, HAMCREST, COMMONS_IO, HTTP_CLIENT_FLUENT)
  package(:war).libs -= artifacts(SERVLET_API)
end