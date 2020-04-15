package com.poplar.session;

import com.poplar.config.Configuration;
import com.poplar.database.DataSource;
import com.poplar.config.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Create BY poplar ON 2020/4/14
 * 1.初始化过程完成Configuration的初始化
 * 2. 用来上产SqlSession的工厂
 */
public class SqlSessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactory.class);

    //由于Configuration对象在内存中是很大的，我们需要保证他只有唯一的实列
    private final Configuration configuration = new Configuration();

    public SqlSessionFactory() {
        //此处完成Configuration的赋值
        loadDB();
        loadMappersInfo();
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }


    private static final String MAPPER_CONFIG_LOCATION = "mapper";

    private static final String DB_FILE = "db.properties";

    //加载指定文件下的mapper.xml
    private void loadMappersInfo() {
        URL source = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(source.getFile());
        if (mappers.isDirectory()) {
            File[] listFiles = mappers.listFiles();
            //遍历mapper下所有的mapper.xml文件,解析后注册至Configuration
            for (File file : listFiles) {
                loadMapper(file);
            }
        }
    }

    //加载指定的mapper.xml文件
    private void loadMapper(File file) {
        //创建saxReader对象
        SAXReader reader = new SAXReader();
        //通过read方法读取一一个文件转换成Document对象
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            // TODO Auto- generated catch block
            e.printStackTrace();
        }
        //获取根节点元索对象<mapper>
        Element root = document.getRootElement();
        //获取命名空间
        String namespace = root.attribute("namespace").getData().toString();
        //获取select子节点列表
        List<Element> selects = root.elements("select");
        //遍历select节点，将信息记录到MapperStatement对象，并在记到configuration对象中
        for (Element element : selects) {
            MapperStatement mapperStatement = new MapperStatement();//实例化mappedstatement
            String id = element.attribute("id").getData().toString();//读取id属性
            String resultType = element.attribute("resultType").getData().toString();//读取resultType属性
            String sql = element.getData().toString();//读取SQL语句信息
            String sourceId = namespace + "." + id;
            //给MapperStatement属性赋值
            mapperStatement.setSourceId(sourceId);
            mapperStatement.setResultType(resultType);
            mapperStatement.setSql(sql);
            mapperStatement.setNameSpace(namespace);
            //注册到configuration对象中
            configuration.getMapperStatementMap().put(sourceId, mapperStatement);
        }
        logger.info("完成mapper.xml的解析并赋值{}",configuration);
    }


    //加载db.properties并设置DataSource的值
    private void loadDB() {
        InputStream stream = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_FILE);
        try {
            Properties prop = new Properties();
            prop.load(stream);
            DataSource ds = new DataSource();
            ds.setDriver(prop.getProperty("driver"));
            ds.setUrl(prop.getProperty("url"));
            ds.setUserName(prop.getProperty("username"));
            ds.setPassWord(prop.getProperty("password"));
            configuration.setDataSource(ds);
            logger.info("完成数据库配置赋值{}",ds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
