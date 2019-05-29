package ecoin.database;

//@Configuration
//@ComponentScan
//@EnableJpaRepositories
//@EnableTransactionManagement
//class ApplicationConfig {
//
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		dataSource.setUsername("user");
//		dataSource.setPassword("password");
//		dataSource.setUrl(
//				"jdbc:mysql://localhost:3306/bank?serverTimezone=UTC&amp;useSSL=false;createDatabaseIfNotExist=true");
//
//		return dataSource;
//	}
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setDatabase(Database.HSQL);
//		vendorAdapter.setGenerateDdl(true);
//
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setJpaVendorAdapter(vendorAdapter);
//		em.setPackagesToScan(getClass().getPackage().getName());
//		em.setDataSource(dataSource());
//		em.setJpaProperties(additionalProperties());
//
//		return em;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(emf);
//
//		return transactionManager;
//	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	Properties additionalProperties() {
//		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", "update");
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//
//		return properties;
//	}
//}
