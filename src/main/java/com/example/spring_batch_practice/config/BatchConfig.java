package com.example.spring_batch_practice.config;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {
// DefaultBatchConfiguration: spring5부터 도입, 스프링 배치의 핵심 컴포넌트들을 자동으로 구성

  @Bean
  public DataSource dataSource() { // 스프링 배치는 job과 step의 실행정보(메타데이터)를 데이터베이스에 저장 -> DataSource설정 필요
    // addScript(): H2 데이터베이스용 스프링 배치 테이블 스키마 초기화용 스크립트 경로
    // Spring batch가 내부적으로 사용하는 Job Repository용 테이블들(BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_STEP_EXCUTION, 등)을 생성하는 SQL파일

    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("org/springframework/batch/core/schema-h2.sql")
        .build();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }
}
