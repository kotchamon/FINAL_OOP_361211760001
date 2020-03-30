import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    //connect database
    public static String driverName = "org.sqlite.JDBC";
    public static String url = "jdbc:sqlite:C:/Database/Hospital.sqlite";
    public static Connection conn = null;
    //constant operators
    //CRUD
    public static final String GET_ALL_PT = "select * from patient";
    public static final String ADD_PT = "insert into patient(p_id,p_name,p_gander,p_age,p_address,p_blood_result) values (?,?,?,?,?,?)";
    public static final String UPDATE_PT = "update patient set p_name = ?,p_gander = ? ,p_age = ? ,p_address = ? ,p_blood_result = ? where id = ?";
    public static final String DELETE_PT = "delete from patient where p_id = ?";
    public static final String FING_PT_BY_ID = "select * from patient where p_id = ?";


    //create class instant
    private static PatientDAOImpl instant = new PatientDAOImpl();

    public static PatientDAOImpl getInstance() {
        return instant;
    }

    //constructor

    public PatientDAOImpl() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Driver load successfully.");
    }


    @Override
    public List<Patient> getAllPatient() {
        List<Patient> PT = new ArrayList<Patient>();
                 try {
                     conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(GET_ALL_PT);
                     while (rs.next()){
                         int p_id = rs.getInt(1);
                         String p_name = rs.getString(2);
                         String p_gender = rs.getString(3);
                         int  p_age = rs.getInt(4);
                         String p_address = rs.getString(5);
                         String  p_blood_result = rs.getString(6);

                         //add data to object
                         PT.add(new Patient(p_id,p_name,p_gender,p_age,p_address,p_blood_result));
                     }
                     //close connection
                     rs.close();
                     stmt.close();
                     conn.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }

                 return PT;


             }//getAllEmp




    @Override
    public void addPt(Patient newPt) {

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(ADD_PT);
            //set parameter
            ps.setInt(1, newPt.getP_id());
            ps.setString(2, newPt.getP_name());
            ps.setString(3, newPt.getP_gender());
            ps.setInt(4, newPt.getP_age());
            ps.setString(5, newPt.getP_address());
            ps.setString(6, newPt.getP_blood_result());

            boolean rs = ps.execute();
            if (rs == true) {
                System.out.println("Could not add data to database.");
                System.exit(1);
            }
            System.out.println("Already add your data to database.");
            //close connection
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updatePt(Patient patient) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(UPDATE_PT);
            //set parameter
            ps.setString(1, patient.getP_name());
            ps.setString(2, patient.getP_gender());
            ps.setInt(3, patient.getP_age());
            ps.setString(4, patient.getP_address());
            ps.setString(5, patient.getP_blood_result());
            ps.setInt(6,patient.getP_id());


            int rs = ps.executeUpdate();
            if (rs != 0) {
                System.out.println("Data with p_id" + patient.getP_id() + "was updated.");
            } else {
                System.out.println("Cloud not update data with p_id" + patient.getP_id());
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void deletePt(int p_id) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(DELETE_PT);
            //set parameter
            ps.setInt(1, p_id);
            int rs = ps.executeUpdate();
            if (rs != 0) {
                System.out.println("Patient with p_id" + p_id + "was deleted.");
            } else {
                System.out.println("Could not delete Employee with p_id" + p_id);
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Patient findpt(int id) {
        Patient PT = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(FING_PT_BY_ID);
            //set parameter
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int p_id = rs.getInt(1);
                String p_name = rs.getString(2);
                String p_gender =rs.getString(3);
                int p_age = rs.getInt(4);
                String p_address =rs.getString(5);
                String p_blood_result = rs.getString(6);
                PT = new Patient(p_id, p_name, p_gender, p_age,p_address,p_blood_result);
            }else {
                System.out.println("Cloud not found Patient"+"white p_id" + id);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PT;
    }
}





