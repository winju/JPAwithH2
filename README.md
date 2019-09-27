# JPAwithH2
This is a sample project demonstarting the use of JPA with in-memory database H2. Used CommandLineRunner and Logger to showcase what is happening in console itself(i.e Eclipse IDE console). 


H2 database , JPA and Hibernate
•	In Java we write Object oriented programming we deal with Objects but RDBMS database like Oracle store data as tables and this is where JPA(Java Persistence APIs) come to the rescue .
•	The fundamentals that objects are made of fields while tables(RDBMS) are made of columns is itself by nature very different.
•	So there is a mismatch between designing Classes and tables which creates lots of issues and problem of persistence comes in and we require JPA(we previously had JDBC,Spring JDBC etc).
o	1st Scenario: A class Task and a table Task will have different names in fields and columns respectively.In class it will be String desc, Boolean isDone etc while in table the columns will be Varchar description,Boolean is _done etc.
o	2nd Scenario : Two classes Employee<-> Task are interdependent ,Employee class having multiple Tasks and a Task having multiple Employess but in RDBMS we will have employee, task and employee_tasks table. So employee_tasks table will map employee and tasks table. So how we will store Class data into such different tables.
o	3rd Scenario: Three classes,  Employee class, Full-time class and Part-time class the last two classes extend Employee class but we will have only one table employee which will have a field employee_type which will distinguish part time with full time employee.
•	Previous persistence frameworks like JDBC,Spring JDBC and MyBatis we used to write queries to do CRUD in database, a Select example below in JDBC. So queries will be very big, complex and thus ,maintaining, understanding them very cumbersome.
 
•	How JPA plays the role? Below diagram.
 
•	Using JPA we will able to directly Map classes to tables i.e Class Task with table TASK etc using EntityManager in JPA
•	3rd Scenario  mentioned 3 points above is solved by using JPA.
 
•	Mentioned above to make the Classes as a single table -> InheritanceType.SINGLE_TABLE and the column which will differentiate both the classes in the table will be EMPLOYEE_TYPE .
•	Hibernate implements JPA, it is like JPA is an interface and Hibernate implements it .
•	JPA is a specification, it defines the annotations,crtiteria queries etc and Hibernate implements all these concepts.
•	Hibernate is a popular ORM-Object Relational Mapping, JPA defines specifications, Hibernate is one of the most popular implementation of JPA.
 
•	We will be building a Java Application to demo JPA. Scenario is create a Class User and expect to store a similar User table in the H2 database.
•	JPA expects a default Constructor for the Class we are planning to Map and thus store in Database. Below is a Class User created which will act as an Entity and will bind to table User. JPA annotations are used @Entity(the Class whose data being stored in DB), @ID(The primary key), @GeneratedValue(Will be auto generated in DB) .
@Entity
public class User {
	
	@Id //Primary Key
	@GeneratedValue
	private long id; 
	
	private String name;
	private String role;
	
	public User() {
		
	}
	
	public User(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", role=" + role + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
•	We will create a DAO(Data Access Object) Service to Map it to the table. We will SAVE, RETREIVE Users from database(User table) using this Service.
•	In SpringBoot the services that manage the database are denoted as @Repository.
•	Whenever we interact with the database we have to create a Transaction, so instead of creating transaction each we interact with DB, we can import @Transactional available in JPA persistence.
•	EnityManager in JPA persistence is used to manage Entities(User class whose data will be put in table)  .
@Repository
@Transactional
public class UserDAOService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public long insert(User user) { 
		entityManager.persist(user);
		return user.getId();
	}
}
•	When we did entityManager.persist(user), then not only the data is saved in DB but user will be tracked by JPA as it has now come into it’s persistence context. Anything not in persistence context is not tracked by JPA.





@Component
public class UserDAOServiceCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	UserDAOService userDaoService;
	
	private static final Logger log = LoggerFactory.getLogger(UserDAOServiceCommandLineRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		User user = new User("Jack", "Admin");
		long insert = userDaoService.insert(user);
		
		log.info("New user is created : "+user);
		
	}
}

•	In the above code we see that we have used CommandLineRunner what it will do is that it will run the Service which implements it on the SpringBoot project startup itself and we can log the details and view it in the logging of console in Eclipse.
 
•	So Basically after running above codes we were able to view the result that the User has been created but we have not connected to database!!? Or have we?!!
•	So the above snapshot clears our doubts!  The table is created and that is in H2 database(In-memory) we had added the H2 databse/dependency while Maven project creation.
•	H2 database – this database is an in-memory database and is created in running application’s memory we don’t’ have to install and run it separately. By running above codes the schema and table has been created automatically and data has been populated in it! The database will get destroyed when Application is stopped. We don’t need to do any maintenance of this database.
spring.jpa.show-sql = true
spring.h2.console.enabled = true
•	By using the above command in the properties file of the application, application.properties we will be able to see the sql commands that are used and how H2 creates the schema and populates it. 
•	We can open H2 explorer to view the tables and see the setup http://localhost:8080/h2-console .The running SpringBoot application’s port /h2-console.
•	By viewing above screenshot we can understand SpringBoot Auto Configuration creates everything related to H2 database.
•	Below are the benefits and one con of using in-memory database i.e in between restarts of the application the data is not persisted.
 
•	The thing is we will not have just User entity. In a big project we will have multiple Entities , so all these Entities number of times will run similar kind of actions like saving, searching, updating etc so what Spring Data JPA does is provide us with a pre-defined functions to do so. The below code uses Spring Data JPA repository. We just need to create an Interface and extend JpaRepository and Spring will itself do all the methods implementation !!
public interface UserRepository extends JpaRepository<User, Long>{

}
•	
@Component
public class UserDAOServiceCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	UserRepository userRepository;
//	UserDAOService userDaoService;
	
	private static final Logger log = LoggerFactory.getLogger(UserDAOServiceCommandLineRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		User user = new User("Jill", "Admin2");
//		long insert = userDaoService.insert(user);
		userRepository.save(user);
		
		
		log.info("New user is created : "+user);
		
	}
	
}
•	In the above code we can see that by just writing userRepository.save(user) we were able to save the Entity to the H2 database instead of userDaoService code which will also get repeated for every new Entity like CarDoaService, AirplaneDaoService etc .
•	We don’t anymore need the below  userDaoService code anymore , as Spring Data JPA comes to the rescue.:
@Repository
@Transactional
public class UserDAOService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public long insert(User user) { 
		entityManager.persist(user);
		return user.getId();
	}
}

