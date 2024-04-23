package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import lib.EmployeeFamily;


public class Employee {
	public enum Genders{
		Pria,
		Wanita
	}
	
	private static final int GRADE_1_SALARY = 3000000;
	private static final int GRADE_2_SALARY = 5000000;
	private static final int GRADE_3_SALARY = 7000000;
    private static final double FOREIGNER_SALARY_INCREASE = 1.5;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private Genders gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
    private EmployeeFamily spouse;
    private List<EmployeeFamily> children;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, Genders gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
		int baseSalary;
		switch (grade) {
			case 1:
				baseSalary = GRADE_1_SALARY;
				break;
			case 2:
				baseSalary = GRADE_2_SALARY;
				break;
			case 3:
				baseSalary = GRADE_3_SALARY;
				break;			
			default:
				throw new IllegalArgumentException("Invalid grade: " + grade);
		}
		monthlySalary = calculateForeignerSalaryIncrease(baseSalary);
	}
	
	private int calculateForeignerSalaryIncrease(int baseSalary) {
        return isForeigner ? (int) (baseSalary * FOREIGNER_SALARY_INCREASE) : baseSalary;
    }

	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(EmployeeFamily spouse) {
        this.spouse = spouse; // data clumps
    }
	
	public void addChild(EmployeeFamily child) {
        children.add(child); // data clumps
    }
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == yearJoined) {
			monthWorkingInYear = date.getMonthValue() - monthJoined;
		}else {
			monthWorkingInYear = 12;
		}
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible,
                (spouse != null) && spouse.getIdNumber().isEmpty(), children.size());
	}
}
