// DO NOT EDIT.  Make changes to Employee.java instead.
package your.app.model;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

import er.extensions.eof.*;
import er.extensions.foundation.*;

@SuppressWarnings("all")
public abstract class _Employee extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "Employee";

  // Attribute Keys
  public static final ERXKey<String> FIRST_NAME = new ERXKey<String>("firstName");
  public static final ERXKey<String> LAST_NAME = new ERXKey<String>("lastName");
  public static final ERXKey<Integer> SALARY = new ERXKey<Integer>("salary");
  // Relationship Keys
  public static final ERXKey<your.app.model.Company> COMPANY = new ERXKey<your.app.model.Company>("company");
  public static final ERXKey<your.app.model.Profession> PROFESSION = new ERXKey<your.app.model.Profession>("profession");

  // Attributes
  public static final String FIRST_NAME_KEY = FIRST_NAME.key();
  public static final String LAST_NAME_KEY = LAST_NAME.key();
  public static final String SALARY_KEY = SALARY.key();
  // Relationships
  public static final String COMPANY_KEY = COMPANY.key();
  public static final String PROFESSION_KEY = PROFESSION.key();

  private static Logger LOG = Logger.getLogger(_Employee.class);

  public Employee localInstanceIn(EOEditingContext editingContext) {
    Employee localInstance = (Employee)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String firstName() {
    return (String) storedValueForKey(_Employee.FIRST_NAME_KEY);
  }

  public void setFirstName(String value) {
    if (_Employee.LOG.isDebugEnabled()) {
    	_Employee.LOG.debug( "updating firstName from " + firstName() + " to " + value);
    }
    takeStoredValueForKey(value, _Employee.FIRST_NAME_KEY);
  }

  public String lastName() {
    return (String) storedValueForKey(_Employee.LAST_NAME_KEY);
  }

  public void setLastName(String value) {
    if (_Employee.LOG.isDebugEnabled()) {
    	_Employee.LOG.debug( "updating lastName from " + lastName() + " to " + value);
    }
    takeStoredValueForKey(value, _Employee.LAST_NAME_KEY);
  }

  public Integer salary() {
    return (Integer) storedValueForKey(_Employee.SALARY_KEY);
  }

  public void setSalary(Integer value) {
    if (_Employee.LOG.isDebugEnabled()) {
    	_Employee.LOG.debug( "updating salary from " + salary() + " to " + value);
    }
    takeStoredValueForKey(value, _Employee.SALARY_KEY);
  }

  public your.app.model.Company company() {
    return (your.app.model.Company)storedValueForKey(_Employee.COMPANY_KEY);
  }
  
  public void setCompany(your.app.model.Company value) {
    takeStoredValueForKey(value, _Employee.COMPANY_KEY);
  }

  public void setCompanyRelationship(your.app.model.Company value) {
    if (_Employee.LOG.isDebugEnabled()) {
      _Employee.LOG.debug("updating company from " + company() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setCompany(value);
    }
    else if (value == null) {
    	your.app.model.Company oldValue = company();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _Employee.COMPANY_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _Employee.COMPANY_KEY);
    }
  }
  
  public your.app.model.Profession profession() {
    return (your.app.model.Profession)storedValueForKey(_Employee.PROFESSION_KEY);
  }
  
  public void setProfession(your.app.model.Profession value) {
    takeStoredValueForKey(value, _Employee.PROFESSION_KEY);
  }

  public void setProfessionRelationship(your.app.model.Profession value) {
    if (_Employee.LOG.isDebugEnabled()) {
      _Employee.LOG.debug("updating profession from " + profession() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setProfession(value);
    }
    else if (value == null) {
    	your.app.model.Profession oldValue = profession();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _Employee.PROFESSION_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _Employee.PROFESSION_KEY);
    }
  }
  

  public static Employee createEmployee(EOEditingContext editingContext, String firstName
, String lastName
, Integer salary
, your.app.model.Company company, your.app.model.Profession profession) {
    Employee eo = (Employee) EOUtilities.createAndInsertInstance(editingContext, _Employee.ENTITY_NAME);    
		eo.setFirstName(firstName);
		eo.setLastName(lastName);
		eo.setSalary(salary);
    eo.setCompanyRelationship(company);
    eo.setProfessionRelationship(profession);
    return eo;
  }

  public static ERXFetchSpecification<Employee> fetchSpec() {
    return new ERXFetchSpecification<Employee>(_Employee.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<Employee> fetchAllEmployees(EOEditingContext editingContext) {
    return _Employee.fetchAllEmployees(editingContext, null);
  }

  public static NSArray<Employee> fetchAllEmployees(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Employee.fetchEmployees(editingContext, null, sortOrderings);
  }

  public static NSArray<Employee> fetchEmployees(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<Employee> fetchSpec = new ERXFetchSpecification<Employee>(_Employee.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<Employee> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static Employee fetchEmployee(EOEditingContext editingContext, String keyName, Object value) {
    return _Employee.fetchEmployee(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Employee fetchEmployee(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Employee> eoObjects = _Employee.fetchEmployees(editingContext, qualifier, null);
    Employee eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Employee that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Employee fetchRequiredEmployee(EOEditingContext editingContext, String keyName, Object value) {
    return _Employee.fetchRequiredEmployee(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Employee fetchRequiredEmployee(EOEditingContext editingContext, EOQualifier qualifier) {
    Employee eoObject = _Employee.fetchEmployee(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Employee that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Employee localInstanceIn(EOEditingContext editingContext, Employee eo) {
    Employee localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
