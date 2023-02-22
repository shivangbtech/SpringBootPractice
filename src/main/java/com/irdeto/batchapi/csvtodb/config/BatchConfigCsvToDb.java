package com.irdeto.batchapi.csvtodb.config;

import com.irdeto.batchapi.csvtodb.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfigCsvToDb {

//    public BatchConfigCsvToDb(JobBuilderFactory jbf, ){
//
//    }

    @Autowired
    @Lazy
    private JobBuilderFactory jbf;

    @Autowired
    @Lazy
    private StepBuilderFactory sbf;

    @Bean
    public Step step() {
        return sbf.get("s1")
                .<Product, Product>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job() {
        return jbf.get("j1")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public ItemReader<Product> reader() {

        // To read flat file live csv file and convert it into given type Product
        FlatFileItemReader<Product> flatFileItemReader = new FlatFileItemReader<>();
//        flatFileItemReader.setResource(new ClassPathResource("com/irdeto/batchapi/csvtodb/data/products.csv"));
        flatFileItemReader.setResource(new PathResource("/Users/shivang.goel/Code/SpringBootPractice/src/main/java/com/irdeto/batchapi/csvtodb/data/products.csv"));

        // To parse the line and by default comma is delimeter
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("id", "name", "description", "price");

        // To set values read by DelimitedLineTokenizer in a Bean
        BeanWrapperFieldSetMapper<Product> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Product.class);

        //Mapping each line into the product
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper();
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    @Bean
    public ItemProcessor<Product, Product> processor() {
        return p -> {
            p.setPrice(p.getPrice() - p.getPrice() * 10 / 100);
            return p;
        };
    }

    @Bean
    public ItemWriter<Product> writer() {
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource());
        // Use to read Product bean
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
        writer.setSql("INSERT INTO PRODUCT(ID,NAME,DESCRIPTION,PRICE) VALUES(:id,:name,:description,:price)");
        return writer;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

}
