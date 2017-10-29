package ch34;

public class TestDatabaseStuff {
	
	public String connectionString		= "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	public String connectionUser		= "root";
	public String connectionPass		= "P455w0rd!";
	public String connectionString2		= connectionString + "&noAccessToProcedureBodies=true";
	
	public TestDatabaseStuff() { return; }
}