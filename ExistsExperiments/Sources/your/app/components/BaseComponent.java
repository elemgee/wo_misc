package your.app.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXEC;

import your.app.Application;
import your.app.Session;

public class BaseComponent extends ERXComponent {
	
	protected EOEditingContext editingContext = null;
	
	public BaseComponent(WOContext context) {
		super(context);
	}
	
	@Override
	public Application application() {
		return (Application)super.application();
	}
	
	@Override
	public Session session() {
		return (Session)super.session();
	}
	
	public EOEditingContext editingContext() {
		if (editingContext == null) {
			editingContext = ERXEC.newEditingContext();
		}
		return editingContext;
	}
}
