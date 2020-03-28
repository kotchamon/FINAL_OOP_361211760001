import java.util.List;

public interface PatientDAO {
    public List<Patient> getAllPatient();
    public void addPt (Patient newPt);
    public void updatePt (Patient patient);
    public void deletePt (int p_id);
    public Patient findpt (int id);

}
