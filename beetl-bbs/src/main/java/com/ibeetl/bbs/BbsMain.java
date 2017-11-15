package com.ibeetl.bbs;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.ibeetl.bbs.common.Const;
import com.ibeetl.bbs.dao.BbsModuleDao;
import com.ibeetl.bbs.util.Functions;
import com.ibeetl.bbs.util.lucene.LuceneUtil;


@SpringBootApplication
@EnableCaching
public class BbsMain extends SpringBootServletInitializer  {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BbsMain.class);
    }
	
	public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(BbsMain.class);
        app.setBannerMode(Banner.Mode.OFF);
//        PageQuery.DEFAULT_PAGE_SIZE =10 ;
        app.run(args);

    }

    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration(@Qualifier("functions") Functions fn, final BbsModuleDao moduleDao) {

        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

        try {
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(BbsMain.class.getClassLoader(),"templates/");
            beetlGroupUtilConfiguration.setResourceLoader(cploder);

            beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
            Map<String, Object> functionPackages = new HashMap<String, Object>();
            functionPackages.put("c", fn);
            beetlGroupUtilConfiguration.setFunctionPackages(functionPackages);
            beetlGroupUtilConfiguration.setSharedVars(new HashMap<String, Object>() {{
                put("v", Const.TIMESTAMP);
                put("moduleList", moduleDao.all());
            }});
            
            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

    @Bean(name = "beetlSqlScannerConfigurer")
    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer(@Qualifier("sqlManagerFactoryBean") SqlManagerFactoryBean fb) {
        BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
        conf.setBasePackage("com.ibeetl.bbs.dao");
        conf.setDaoSuffix("Dao");
        conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");

        SQLManager sql;
        try {
            sql = (SQLManager) fb.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 
        return conf;
    }

    @Bean(name = "sqlManagerFactoryBean")
    @Primary
    public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();

        BeetlSqlDataSource source = new BeetlSqlDataSource();
        source.setMasterSource(datasource);
        ;
        factory.setCs(source);
        factory.setDbStyle(new MySqlStyle());
        factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
        factory.setNc(new UnderlinedNameConversion());
        factory.setSqlLoader(new ClasspathLoader("/sql"));


        return factory;
    }



    @Bean(name = "datasource")
	public DataSource druidDataSource(Environment env) {
		DruidDataSource druidDataSource = new DruidDataSource();
		
		druidDataSource.setDriverClassName(env.getProperty("bbs.driver"));
		druidDataSource.setUrl(env.getProperty("bbs.dbUrl"));
		druidDataSource.setUsername(env.getProperty("bbs.dbUserName"));
		druidDataSource.setPassword(env.getProperty("bbs.dbPassowrd"));
		druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
		druidDataSource.setInitialSize(5);
		druidDataSource.setMaxActive(10);
		return druidDataSource;
	}

    @Bean(name = "luceneUtil")
    public LuceneUtil luceneUtil(Environment env){
    	LuceneUtil luceneUtil = new LuceneUtil();
    	luceneUtil.setIndexDer(env.getProperty("lucene.indexder"));
    	return luceneUtil;
    }
    
}