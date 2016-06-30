developer need to know.
1. run SpringApplication BaseApplication
2. databaseConfig you could set at DataSourceConfig.java
   invoke http://localhost:9000/druid/sql.html,it will shows you database info
3. filters you could add to package - com.sinosafe.config


pay attention
1. If the class your want add    @Configuration ,
   please put it to  config package and use xxxxConfig as its name and
   it could not be final and for quick start
