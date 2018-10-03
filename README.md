# liquibase-dbmsoutput
Oracle DBMS_OUTPUT Reader

Usage
========

using maven
===========
1. Add to pom.xml

```
<build>
    <plugins>
        <plugin>
            <groupId>org.liquibase</groupId>
            <artifactId>liquibase-maven-plugin</artifactId>
            <version>${liquibase.version}</version>
            <configuration>
                ...
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.liquibase.ext</groupId>
                    <artifactId>liquibase-dbmsoutput</artifactId>
                    <version>0.1-SNAPSHOT</version>
                </dependency>
            </dependencies>
            <executions>
                <execution>
                    <phase>process-resources</phase>
                    <goals>
                        <goal>update</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
2. Add to databaseChangeLog
```    
<changeSet author="pjasion" id="DbmsOutputTest">
    <ora:dbmsOutput action="enable" bufferSize="150000"/>
    <sql splitStatements="false" stripComments="false" endDelimiter="\n/">
        BEGIN
            DBMS_OUTPUT.PUT_LINE('Hello From Oracle!');
        END;
    </sql>
    <ora:dbmsOutput action="show" numLines="10000" outType="std" logPrefix="dbms_output"/>
</changeSet>
```

3. See in logs
```
dbms_output: Hello From Oracle!
```
Have fun!
====
