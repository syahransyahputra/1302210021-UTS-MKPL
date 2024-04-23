package lib;

public class TaxFunction {

    private static final int ANNUAL_MONTHS = 12;
    private static final int MAX_CHILDREN = 3;
    private static final int NON_MARRIED_TAX_FREE_INCOME = 54000000;
    private static final int MARRIED_TAX_FREE_INCOME = NON_MARRIED_TAX_FREE_INCOME + 4500000;
    private static final int CHILD_TAX_FREE_INCOME_DEDUCTION = 4500000;

    /**
     * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
     * 
     * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
     * 
     * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
     * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
     * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
     * 
     */
	
    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        validateInput(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, numberOfChildren);

        int annualIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
        int taxFreeIncome = calculateTaxFreeIncome(isMarried, numberOfChildren);
        int taxableIncome = annualIncome - deductible - taxFreeIncome;

        return (int) Math.round(0.05 * taxableIncome);
    }

    private static void validateInput(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, int numberOfChildren) {
        if (numberOfMonthWorking > ANNUAL_MONTHS) {
            throw new IllegalArgumentException("Jumlah bulan bekerja tidak boleh lebih dari 12");
        }

        if (numberOfChildren > MAX_CHILDREN) {
            numberOfChildren = MAX_CHILDREN;
        }

        if (monthlySalary < 0 || otherMonthlyIncome < 0 || deductible < 0 || numberOfChildren < 0) {
            throw new IllegalArgumentException("Semua input harus bilangan bulat positif");
        }
    }

    private static int calculateTaxFreeIncome(boolean isMarried, int numberOfChildren) {
        int taxFreeIncome = NON_MARRIED_TAX_FREE_INCOME;
        if (isMarried) {
            taxFreeIncome += MARRIED_TAX_FREE_INCOME;
        }

        for (int i = 0; i < numberOfChildren; i++) {
            taxFreeIncome += CHILD_TAX_FREE_INCOME_DEDUCTION;
        }

        return taxFreeIncome;
    }
}
