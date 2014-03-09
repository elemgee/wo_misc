package your.app.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

public class Profession extends _Profession {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Profession.class);
	
	public static Profession chef(EOEditingContext editingContext) {
		return fetchRequiredProfession(editingContext, NAME.is("Chef"));
	}
	
	public static Profession computerScientist(EOEditingContext editingContext) {
		return fetchRequiredProfession(editingContext, NAME.is("Computer Scientist"));
	}
}
