package your.app.components;

import your.app.model.Company;
import your.app.model.Employee;
import your.app.model.Profession;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.qualifiers.ERXExistsQualifier;
import er.extensions.foundation.ERXArrayUtilities;
import er.extensions.qualifiers.ERXAndQualifier;

public class Main extends BaseComponent {
	public Company companyItem = null;
	public Employee employeeItem = null;
	
	public Main(WOContext context) {
		super(context);
	}
	
	public NSArray<Company> companiesWithAChef() {
		EOQualifier qualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.chef(editingContext()));
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}
	
	public NSArray<Company> companiesWithAComputerScientist() {
		EOQualifier qualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.computerScientist(editingContext()));
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}

	public NSArray<Company> companiesWithAChefAndComputerScientist() {
		EOQualifier chefQualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.chef(editingContext()));
		EOQualifier compSciQualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.computerScientist(editingContext()));
		EOQualifier combinedQualifier = new ERXAndQualifier(chefQualifier, compSciQualifier);
		return Company.fetchCompanies(editingContext(), combinedQualifier, null /*sortOrderings*/);
	}

	///////////////
	
	protected NSArray<Company> allCompanies() {
		return Company.fetchAllCompanies(editingContext());
	}

	public NSArray<Company> companiesWithAChefInMemory() {
		EOQualifier qualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.chef(editingContext()));
		return ERXArrayUtilities.filteredArrayWithQualifierEvaluation(allCompanies(), qualifier);
	}
	
	public NSArray<Company> companiesWithAComputerScientistInMemory() {
		EOQualifier qualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.computerScientist(editingContext()));
		return ERXArrayUtilities.filteredArrayWithQualifierEvaluation(allCompanies(), qualifier);
	}

	public NSArray<Company> companiesWithAChefAndComputerScientistInMemory() {
		EOQualifier chefQualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.chef(editingContext()));
		EOQualifier compSciQualifier = Company.EMPLOYEES.dot(Employee.PROFESSION).containsObject(Profession.computerScientist(editingContext()));
		EOQualifier combinedQualifier = new ERXAndQualifier(chefQualifier, compSciQualifier);
		return ERXArrayUtilities.filteredArrayWithQualifierEvaluation(allCompanies(), combinedQualifier);
	}

	///////////////

	public NSArray<Company> companiesWithAChefExists() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.chef(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}
	
	public NSArray<Company> companiesWithAComputerScientistExists() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.computerScientist(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}

	public NSArray<Company> companiesWithAChefAndComputerScientistExists() {
		EOQualifier chefQualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.chef(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		EOQualifier compSciQualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.computerScientist(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		EOQualifier combinedQualifier = new ERXAndQualifier(chefQualifier, compSciQualifier);
		return Company.fetchCompanies(editingContext(), combinedQualifier, null /*sortOrderings*/);
	}

	///////////////
	
	public NSArray<Company> companiesWithAChefExistsConvertedToIn() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.chef(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/,
				true /*usesInQualInstead*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}
	
	public NSArray<Company> companiesWithAComputerScientistExistsConvertedToIn() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.computerScientist(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/,
				true /*usesInQualInstead*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}

	public NSArray<Company> companiesWithAChefAndComputerScientistExistsConvertedToIn() {
		EOQualifier chefQualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.chef(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/,
				true /*usesInQualInstead*/);
		EOQualifier compSciQualifier = new ERXExistsQualifier(
				Employee.PROFESSION.is(Profession.computerScientist(editingContext())) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/,
				true /*usesInQualInstead*/);
		EOQualifier combinedQualifier = new ERXAndQualifier(chefQualifier, compSciQualifier);
		return Company.fetchCompanies(editingContext(), combinedQualifier, null /*sortOrderings*/);
	}
	
	
	//////////////////
	
	
	public NSArray<Company> companiesWithEmployeesMakingBetween20kAnd40k() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.SALARY.greaterThanOrEqualTo(20000)
					.and(Employee.SALARY.lessThanOrEqualTo(40000)) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}

	public NSArray<Company> companiesWithChefsMakingBetween20kAnd40k() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.PROFESSION.dot(Profession.NAME).is("Chef")
					.and(
				Employee.SALARY.greaterThanOrEqualTo(20000))
					.and(
				Employee.SALARY.lessThanOrEqualTo(40000)) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}

	public NSArray<Company> companiesWithEmployeesMakingBetween70kAnd120k() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Employee.SALARY.greaterThanOrEqualTo(70000)
					.and(Employee.SALARY.lessThanOrEqualTo(120000)) /*subqualifier*/, 
				Company.EMPLOYEES.key() /*baseKeyPath*/);
		return Company.fetchCompanies(editingContext(), qualifier, null /*sortOrderings*/);
	}
	
	public NSArray<Employee> employeeChefs() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Profession.NAME.is("Chef") /*subqualifier*/, 
				Employee.PROFESSION.key() /*baseKeyPath*/);
		return Employee.fetchEmployees(editingContext(), qualifier, null /*sortOrderings*/);
	}

	public NSArray<Employee> employeeComputerScientists() {
		EOQualifier qualifier = new ERXExistsQualifier(
				Profession.NAME.is("Computer Scientist") /*subqualifier*/, 
				Employee.PROFESSION.key() /*baseKeyPath*/,
				true /*usesInQualInstead*/);
		return Employee.fetchEmployees(editingContext(), qualifier, null /*sortOrderings*/);
	}

	@Override
	protected boolean useDefaultComponentCSS() {
		return true;
	}
}
