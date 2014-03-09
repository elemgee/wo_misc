// DO NOT EDIT.  Make changes to Profession.java instead.
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
public abstract class _Profession extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "Profession";

  // Attribute Keys
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys

  // Attributes
  public static final String NAME_KEY = NAME.key();
  // Relationships

  private static Logger LOG = Logger.getLogger(_Profession.class);

  public Profession localInstanceIn(EOEditingContext editingContext) {
    Profession localInstance = (Profession)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String name() {
    return (String) storedValueForKey(_Profession.NAME_KEY);
  }

  public void setName(String value) {
    if (_Profession.LOG.isDebugEnabled()) {
    	_Profession.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _Profession.NAME_KEY);
  }


  public static Profession createProfession(EOEditingContext editingContext, String name
) {
    Profession eo = (Profession) EOUtilities.createAndInsertInstance(editingContext, _Profession.ENTITY_NAME);    
		eo.setName(name);
    return eo;
  }

  public static ERXFetchSpecification<Profession> fetchSpec() {
    return new ERXFetchSpecification<Profession>(_Profession.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<Profession> fetchAllProfessions(EOEditingContext editingContext) {
    return _Profession.fetchAllProfessions(editingContext, null);
  }

  public static NSArray<Profession> fetchAllProfessions(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Profession.fetchProfessions(editingContext, null, sortOrderings);
  }

  public static NSArray<Profession> fetchProfessions(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<Profession> fetchSpec = new ERXFetchSpecification<Profession>(_Profession.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<Profession> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static Profession fetchProfession(EOEditingContext editingContext, String keyName, Object value) {
    return _Profession.fetchProfession(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Profession fetchProfession(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Profession> eoObjects = _Profession.fetchProfessions(editingContext, qualifier, null);
    Profession eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Profession that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Profession fetchRequiredProfession(EOEditingContext editingContext, String keyName, Object value) {
    return _Profession.fetchRequiredProfession(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Profession fetchRequiredProfession(EOEditingContext editingContext, EOQualifier qualifier) {
    Profession eoObject = _Profession.fetchProfession(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Profession that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Profession localInstanceIn(EOEditingContext editingContext, Profession eo) {
    Profession localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
