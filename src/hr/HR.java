package hr;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
/**
 *
 * @author usuario
 */
public class HR {
    
    //Zona REGION
    public int insertarRegion(Region region){
        return -1;
    }
    
    public int borrarRegion (int regionId){
        return -1;
    }
    
    public int modificarRegion (int regionId, Region region){
        return -1;
    }
    
    public Region leerRegion(int regionId){
        return null;
    }
    
    public ArrayList<Region> leerRegions(){
        return null;
    }
    
    //zona COUNTRY
    public int insertarCountry(Country country){
        return -1;
    }
    
    public int borraCountry(String countryId) throws ExcepcionHR{
        String dml = "";
        int registrosAfectados=0;
         try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "HR", "kk");
            String llamada = "call BORRAR_COUNTRY(?)";
            CallableStatement sentenciaLlamable = conexion.prepareCall(llamada);
            
            sentenciaLlamable.setString(1, countryId);
            registrosAfectados=sentenciaLlamable.executeUpdate();
            
            sentenciaLlamable.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Error - Clase no Encontrada: " + ex.getMessage());
        } catch (SQLException ex) {
            ExcepcionHR excepcionHR=new ExcepcionHR(ex.getErrorCode(),ex.getMessage(),null,dml);
            switch (ex.getErrorCode()) {
                case 2292:  excepcionHR.setMensajeErrorUsuario("No se puede borrar porque tiene localidades asociadas");
                            break;
                default:    excepcionHR.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                            break;
            }
            throw excepcionHR;
        }
        return registrosAfectados;
    }
    
    public int modificarCountry (String countryId, Country country){
        return -1;
    }
    
    public Country leerCountry(String countryId){
        return null;
    }
    
    public ArrayList<Country> leerCountrys(){
        return null;
    }
    
    //zona DEPARTMENT
    public int insertarDepartment(Department department){
        return -1;
    }
    
    public int borrarDepartment (int departmentId){
        return -1;
    }
    
    public int modificarDepartment (int departmentId, Department department) throws ExcepcionHR{
        int registrosAfectados = 0;
        String dml = "";
        try {
            System.out.println("----- Acciones Previas");
            System.out.println("Carga del driver de Oracle en memoria");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Creación de una conexión a una BD Oracle llamada HR");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "HR", "kk");
            System.out.println("Creación de un objeto sentencia asociado a dicha conexión");
            Statement sentencia = conexion.createStatement();

            System.out.println("----- Lanzamiento de una sentencia DML");
            dml = "update DEPARTMENTS set "
                    + "DEPARTMENT_ID = "+ department.getDepartmentId() +", "
                    + "DEPARTMENT_NAME = '"+ department.getDepartmentName()+"', "
                    + "MANAGER_ID = "+ department.getManager().getEmployeeId()+", "
                    + "LOCATION_ID = "+ department.getLocation().getLocationId() +" "
                    + "where DEPARTMENT_ID = "+departmentId;
            registrosAfectados = sentencia.executeUpdate(dml);
        
            sentencia.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Error - Clase no Encontrada: " + ex.getMessage());
        } catch (SQLException ex) {
            ExcepcionHR excepcionHR = new ExcepcionHR(ex.getErrorCode(), ex.getMessage(),null, dml);
            switch (ex.getErrorCode()) {
                case 2291:
                    excepcionHR.setMensajeErrorBD("La localidad elegida no existe o el jefe de departamento no es un empleado de la empresa");
                    break;
                case 1400: 
                    excepcionHR.setMensajeErrorBD("El identificador y el nombre del departamento son obligatorios");
                    break;
                case 2290:
                    excepcionHR.setMensajeErrorBD("No se puede modificar el identificador de departamento ya que tiene empleados asociados o datos historicos asociados");
                    break;
                case 1:     
                    excepcionHR.setMensajeErrorBD("El identificador de departamento ya existe");
                    break;
                default:   
                    excepcionHR.setMensajeErrorBD("Error general de sistema consulte con el administrador");
                    break;
            }
             throw excepcionHR;
        }
        return registrosAfectados;
    }
    
    public Department leerDepartment(int departmentId){
        return null;
    }
    
    public ArrayList<Department> leerDepartments(){
        return null;
    }
     
    //zona EMPLOYEE  
    public int insertarEmployee(Employee employee) throws ExcepcionHR{
        String dml="";
        int registrosAfectados=0;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "HR", "kk");
            dml = "INSERT INTO EMPLOYEES"
                    + "(EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,PHONE_NUMBER,HIRE_DATE,JOB_ID,SALARY,COMISSION_PCT,MANAGER_ID,DEPARTMENT_ID) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            
            
            
            sentenciaPreparada.setInt(1, employee.getEmployeeId());
            sentenciaPreparada.setString(2, employee.getFirstName());
            sentenciaPreparada.setString(3, employee.getLastName());
            sentenciaPreparada.setString(4, employee.getEmail());
            sentenciaPreparada.setString(5, employee.getPhoneNumber());
            sentenciaPreparada.setDate(6, (java.sql.Date) employee.getHireDate());
            sentenciaPreparada.setString(7, employee.getJob().getJobId());
            sentenciaPreparada.setDouble(8, employee.getSalary());
            sentenciaPreparada.setDouble(9, employee.getComissionPct());
            sentenciaPreparada.setInt(10, employee.getManager().getEmployeeId());
            sentenciaPreparada.setInt(11, employee.getDepartment().getDepartmentId());
            
            registrosAfectados = sentenciaPreparada.executeUpdate();
            
            sentenciaPreparada.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Error - Clase no Encontrada: " + ex.getMessage());
        } catch (SQLException ex) {
            ExcepcionHR excepcionHR=new ExcepcionHR(ex.getErrorCode(),ex.getMessage(),null,dml);
            switch (ex.getErrorCode()) {
                case 2291:  excepcionHR.setMensajeErrorUsuario("El departamento, el trabajo o el jefe no existen.");
                            break;
                case 1400:  excepcionHR.setMensajeErrorUsuario("El email, la fecha de contratación, el apellido y el trabajo son obligatorios.");
                            break;
                case 2290:  excepcionHR.setMensajeErrorUsuario("El salario tiene que ser mayor que 0.");
                            break;
                case 1:     excepcionHR.setMensajeErrorUsuario("El identificador de usuario y el email no pueden repetirse.");
                            break;
                default:    excepcionHR.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                            break;
            }
            throw excepcionHR;
        }
        return registrosAfectados;
    }
    
    public int borrarEmployee(int employeeId){
        return 0;
    }
    
    public int modificarEmployee(int employeeId, Employee employee){
        return 0;
    }
    
    public Employee leerEmployee(int employeeId){
        return null;
    }
    
    public ArrayList<Employee> leerEmployee(){
        return null;
    }  
    
    //zona LOCATION
    public int insertarLocation(Location location){
        return -1;
    }
    
    public int borrarLocation (int locationId){
        return -1;
    }
    
    public int modificarLocation (int locationId, Location location){
        return -1;
    }
    
    public Location leerLocation(int locationId){
        return null;
    }
    
    public ArrayList<Location> leerLocations(){
        return null;
    }
    
    //zona JOB
    public int insertarJob(Job job){
        return -1;
    }
    
    public int borrarJob (String jobId){
        return -1;
    }
    
    public int modificarJob (String jobId, Job job){
        return -1;
    }
    
    public Job leerJob(String jobId){
        return null;
    }
    
    public ArrayList<Job> leerJobs(){
        return null;
    }
    
        //zona JOBHISTORY
    public int insertarJobHistory(JobHistory jobHistory){
        return -1;
    }
    
    public int borrarJobHistory (int employeeId, Date startDate){
        return -1;
    }
    
    public int modificarJobHistory (int employeeId, Date startDate, JobHistory jobHistory){
        return -1;
    }
    
    public JobHistory leerJobHistory(int employeeId, Date startDate){
        return null;
    }
    
    public ArrayList<JobHistory> leerJobHistorys(){
        return null;
    }
    

}
