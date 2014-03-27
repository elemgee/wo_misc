:title: Make "Exists" qualifiers work for you
:css: css/style.css

----

Make "Exists" qualifiers work for you
=====================================

created by Travis Cripps

demystified by Aaron Rosenzweig

aaron@chatnbike.com

Codivus, Inc.

----

:data-x: r1000
:data-y: r-1000

Why EOF is Cool:
================

* We can think in Objects

* These Objects know how to persist themselves

* We get a proprietary query language which makes a Relational-DB feel like an Object-DB

* Our query language works equally well against the Relational-DB or in Memory
 
----

:data-rotate: 70

The Problem:
============
 
To-Many relationships are *hard!*
---------------------------------

----

:data-rotate: 20
 
The Problem:
============
 
To-Many relationships are *hard!*
---------------------------------

* Many SQL joins can be costly
	
* Multiple to-many relationships in your combined qualifiers can cancel each other out.
	
* bringing all objects into Java memory to perform your qualifier outside of SQL can work but it costs both RAM and speed.
 

----

:data-rotate: 70

The Problem:
============
 
To-One relationships can be *slow!*
-----------------------------------
(sometimes)


----

:data-rotate: 20

The Problem:
============
 
To-One relationships can be *slow!*
-----------------------------------
(sometimes)

* When the tables involved in the join have lots of rows, an exists Qualifier can perform faster than a join


----

:data-rotate: 70

The Solution:
=============
 
Use ERXExistsQualifer 
----------------------

(with Codivus improvements)
----------------------------


----
 
:data-rotate: 20

ERXExistsQualifier Improvements:
==================================
 
* Optionally convert "exists" to "in" qualifier 
* Handles complex inner qualifiers
* Can work in-Memory
* Can handle long keypaths
* Misc. bug fixes


----

:data-rotate: 70

Simple Entity Model
===================

* Company
* Employee
* Profession

----

:data-rotate: 20

Sample data
===================

* "Joe's Crab Shack" has one chef, a manager, and a waiter
* "Code Mongers" has three computer scientists and one famous chef
* "Nobody Inc." has no employees


----

:data-rotate: 70

Companies with a chef (java)
=============================

.. code:: java

	EOQualifier qualifier = 
		Company.EMPLOYEES.dot(Employee.PROFESSION)
		.containsObject(Profession.chef(editingContext()));
			
	return Company.fetchCompanies(editingContext(), 
			qualifier, null /*sortOrderings*/);

----

:data-rotate: 20

Companies with a chef (sql)
=============================

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 
	INNER JOIN "EMPLOYEE" T1 
		ON t0."id" = T1."COMPANY_ID" 
	WHERE T1."PROFESSION_ID" = 1000002

----

:data-rotate: 70

Companies with a chef (sql) issues
==================================

* If there are 7 chefs in a company, that company will be repeated seven times.

* A "distinct" on your fetch will "fix" the problem. (hah!)

* You should avoid "distinct" if you can. It slows things down and smells like you are doing something wrong. 

----

:data-rotate: 20

Companies with a chef and a coder (java)
=========================================

.. code:: java

	EOQualifier chefQualifier = 
		Company.EMPLOYEES.dot(Employee.PROFESSION)
		.containsObject(Profession.chef(editingContext()));
		
	EOQualifier compSciQualifier = 
		Company.EMPLOYEES.dot(Employee.PROFESSION)
		.containsObject(Profession.computerScientist(editingContext()));
		
	EOQualifier combinedQualifier = 
		new ERXAndQualifier(chefQualifier, compSciQualifier);
		
	return Company.fetchCompanies(
		editingContext(), combinedQualifier, null /*sortOrderings*/);

----

:data-rotate: 70

Companies with a chef and a coder (sql)
=======================================

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 
	INNER JOIN "EMPLOYEE" T1 
		ON t0."id" = T1."COMPANY_ID" 
	WHERE (T1."PROFESSION_ID" = 1000004 AND T1."PROFESSION_ID" = 1000002)

----

:data-rotate: 20

Companies with a chef and a coder (sql) issues
==============================================

* This query will always return *zero* results.

* An employee only has one profession, and that value can't be two things at once.


----

:data-rotate: 70

Companies with a chef and a coder (In Memory)
==============================================

.. code:: java

	EOQualifier chefQualifier = 
		Company.EMPLOYEES.dot(Employee.PROFESSION)
		.containsObject(Profession.chef(editingContext()));
		
	EOQualifier compSciQualifier = 
		Company.EMPLOYEES.dot(Employee.PROFESSION)
		.containsObject(Profession.computerScientist(editingContext()));
		
	EOQualifier combinedQualifier = 
		new ERXAndQualifier(chefQualifier, compSciQualifier);
		
	return ERXArrayUtilities.filteredArrayWithQualifierEvaluation(
		allCompanies(), combinedQualifier);

----

:data-rotate: 20

Companies with a chef and a coder (In Memory) issues
====================================================

* If there are 1 million companies in your data, do you really want to bring them all into your java app?

* There has to be a better way! We have EOF, c'mon!


----

:data-rotate: 70

Never Forget
============

* Apple's *OperatorContains* will always execute. 

* It gives one result for in-memory evaluation and a totally different (and incorrect) result for sql evaluation

* This yields non-deterministic behavior

* We should strive for qualifiers that work the same no matter where they are used.

* 11th commandment - thou shalt not use *OperatorContains*

----

:data-rotate: 20

Never Forget (addendum)
=======================

* *OperatorContains* should not mean to do a "LIKE" comparison on character data

* Some custom WO database plugins get this wrong, notably FrontBase

* Modern Frontbase plugins can work with a property:

::

	jdbcadaptor.frontbase.frontbaseContainsOperatorFix=true


----

:data-rotate: 70

Chef and a coder (*Exists*)
==============================================

.. code:: java

	EOQualifier chefQualifier = new ERXExistsQualifier(
		Employee.PROFESSION.is(
		  Profession.chef(editingContext())) /*subqualifier*/, 
		Company.EMPLOYEES.key() /*baseKeyPath*/);
			
	EOQualifier compSciQualifier = new ERXExistsQualifier(
		Employee.PROFESSION.is(
		  Profession.computerScientist(editingContext())) /*subqualifier*/, 
		Company.EMPLOYEES.key() /*baseKeyPath*/);
			
	EOQualifier combinedQualifier = 
		new ERXAndQualifier(chefQualifier, compSciQualifier);
	return Company.fetchCompanies(
		editingContext(), combinedQualifier, null /*sortOrderings*/);


----

:data-rotate: 20

Chef and a coder (*Exists* sql)
================================================

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 
	WHERE ( 
		EXISTS ( 
			SELECT exists0."id" 
			FROM "EMPLOYEE" exists0 
			WHERE 
				exists0."PROFESSION_ID" = 1000004 AND 
				exists0."COMPANY_ID" = t0."id" 
		)  AND  
		EXISTS ( 
			SELECT exists0."id" 
			FROM "EMPLOYEE" exists0 
			WHERE 
				exists0."PROFESSION_ID" = 1000002 AND 
				exists0."COMPANY_ID" = t0."id" 
		) 
	)


----

:data-rotate: 70

Chef and a coder (*Exists* converted to "IN")
==============================================

.. code:: java

	EOQualifier chefQualifier = new ERXExistsQualifier(
		Employee.PROFESSION.is(
		  Profession.chef(editingContext())) /*subqualifier*/, 
		Company.EMPLOYEES.key() /*baseKeyPath*/,
		true /*usesInQualInstead*/);
		
	EOQualifier compSciQualifier = new ERXExistsQualifier(
		Employee.PROFESSION.is(
		  Profession.computerScientist(editingContext())) /*subqualifier*/, 
		Company.EMPLOYEES.key() /*baseKeyPath*/,
		true /*usesInQualInstead*/);
		
	EOQualifier combinedQualifier = 
		new ERXAndQualifier(chefQualifier, compSciQualifier);
	return Company.fetchCompanies(
		editingContext(), combinedQualifier, null /*sortOrderings*/);


----

:data-rotate: 20

Chef and a coder (*Exists* converted to "IN" sql)
=================================================

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 
	WHERE (
		t0."id" IN ( 
			SELECT exists0."COMPANY_ID" 
			FROM "EMPLOYEE" exists0 
			WHERE exists0."PROFESSION_ID" = 1000004 
		)  AND 
		t0."id" IN ( 
			SELECT exists0."COMPANY_ID" 
			FROM "EMPLOYEE" exists0 
			WHERE exists0."PROFESSION_ID" = 1000002 
		) 
	)

----

:data-rotate: 70

Which is better *Exists* or *In*
================================

* The results are the same - performance could be different

* Generally *Exists* will be faster but you must test

* A good database will analyze and convert between *Exists* and *In* for you so in some sense it doesn't matter


----

:data-rotate: 20

Which is better *Exists* or *In*
================================

* When the subquery result set is small, use an *In*

* When the outer result set is small, use an *Exists*

----

:data-rotate: 70

*Exists* explanation
======================

This SQL

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 WHERE EXISTS ( 
		SELECT exists0."id" 
		FROM "EMPLOYEE" exists0 
		WHERE exists0."PROFESSION_ID" = 1000002 AND 
			exists0."COMPANY_ID" = t0."id" )


----

:data-rotate: 20

*Exists* explanation
======================

Is executed like:

.. code:: sql

   for x in ( select t0."id", t0."NAME" from "COMPANY" t0 )
   loop
      if ( 
      	exists ( 
      	select null from "EMPLOYEE" exists0 
      	where exists0."PROFESSION_ID" = 1000002 AND 
      		exists0."COMPANY_ID" = t0."id" 
      	)
      )
      then 
         OUTPUT THE RECORD
      end if
   end loop

It always results in a full scan of T0 (Company)



----

:data-rotate: 70

*In* explanation
======================

This SQL

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 
	WHERE t0."id" IN ( 
		SELECT exists0."COMPANY_ID" 
		FROM "EMPLOYEE" exists0 
		WHERE exists0."PROFESSION_ID" = 1000002 )

----

:data-rotate: 20

*In* explanation
======================

Is executed like:

.. code:: sql

	select t0."id", t0."NAME"
	FROM "COMPANY" t0, 
		(
			SELECT DISTINCT exists0."COMPANY_ID" 
			FROM "EMPLOYEE" exists0 
			WHERE exists0."PROFESSION_ID" = 1000002 
		) t1
 	where t0."id" = t1."COMPANY_ID";

This does a full table scan of exists0 (EMPLOYEE) as the subquery is evaluated, distinct'ed, indexed and then joined to the original table. 

----

:data-rotate: 70

For Dave Avendasora
===================

An example with not only an EXISTS0 but also an EXISTS1	replacement. It happens when you have a complex subquery with one or more joins.

----

:data-rotate: 20

Companies with chef salary between 20k and 40k (java)
===============================================================

.. code:: java

	EOQualifier qualifier = new ERXExistsQualifier(
		Employee.PROFESSION.dot(Profession.NAME).is("Chef")
			.and(
		Employee.SALARY.greaterThanOrEqualTo(20000))
			.and(
		Employee.SALARY.lessThanOrEqualTo(40000)) /*subqualifier*/, 
		Company.EMPLOYEES.key() /*baseKeyPath*/);
		
	return Company.fetchCompanies(
		editingContext(), qualifier, null /*sortOrderings*/);


----

:data-rotate: 70

Companies with chef salary between 20k and 40k (SQL)
===============================================================

.. code:: sql

	SELECT t0."id", t0."NAME" 
	FROM "COMPANY" t0 
	WHERE EXISTS ( 
		SELECT exists0."id" 
		FROM "EMPLOYEE" exists0 
		INNER JOIN "PROFESSION" exists1 ON 
			exists0."PROFESSION_ID" = exists1."id" 
		WHERE (
			exists1."NAME" = 'Chef' AND 
			exists0."salary" >= 20000 AND 
			exists0."salary" <= 40000
		) 
		AND 
		exists0."COMPANY_ID" = t0."id" 
	)

----

:data-rotate: 20

A to-one relationship example
=============================

9 times out of 10, regular joins will work just fine for to-one relationships. But, there are those odd times where the join is so painful that you will want to use an *Esists!* qualifier.

----

:data-rotate: 70

All employees that are chefs (java)
===============================================================

.. code:: java

	EOQualifier qualifier = new ERXExistsQualifier(
		Profession.NAME.is("Chef") /*subqualifier*/, 
		Employee.PROFESSION.key() /*baseKeyPath*/);
		
	return Employee.fetchEmployees(
		editingContext(), qualifier, null /*sortOrderings*/);

----

:data-rotate: 20

All employees that are chefs (SQL)
===============================================================

.. code:: sql

	SELECT t0."COMPANY_ID", t0."FIRST_NAME", 
		t0."id", t0."LAST_NAME", 
		t0."PROFESSION_ID", t0."salary" 
	FROM "EMPLOYEE" t0 
	WHERE  EXISTS ( 
		SELECT exists0."id" 
		FROM "PROFESSION" exists0 
		WHERE exists0."NAME" = 'Chef' AND 
		exists0."id" = t0."PROFESSION_ID" )

----

:data-rotate: 70

Real life example!
==================

.. code:: java

	// (Aaron Dec. 31, 2013)
	// using a join is much slower than the "in" clause
	// EOQualifier forQuestionQualifier = Answer.PLACED_QUESTION
	//   .dot(PlacedQuestion.QUESTION).is(forQuestion);		
	
	EOQualifier forQuestionQualifier = new ERXExistsQualifier(
		PlacedQuestion.QUESTION.is(forQuestion), 
		Answer.PLACED_QUESTION.key(), true /*usesInQualInstead*/);

	* "Answer" table has 19,353,992 rows
	* "PlacedQuestion" table has 27,202 rows
	* "Question" table has 2,307 rows

----

:data-rotate: 20

All companies with at least one employee (java)
================================================

How would you write this?

----

:data-rotate: 70

All companies with at least one employee (java)
================================================

How would you write this?

Normally it would be impossible.

"isNotNull" qualifiers don't work for to-Many relationships.

----

:data-rotate: 20

All companies with at least one employee (java)
================================================

.. code:: java

	EOQualifier qualifier = new ERXExistsQualifier(
			new ERXTrueQualifier() /*subqualifier*/, 
			Company.EMPLOYEES.key() /*baseKeyPath*/,
			false /*usesInQualInstead*/);
			
	return Company.fetchCompanies(
		editingContext(), qualifier, null /*sortOrderings*/);
		
----

:data-rotate: 70

All companies with at least one employee (SQL)
================================================

.. code:: sql

	SELECT t0."id", t0."NAME" FROM "COMPANY" t0 
	WHERE  EXISTS ( 
		SELECT exists0."id" 
		FROM "EMPLOYEE" exists0 
		WHERE 1=1 AND 
			exists0."COMPANY_ID" = t0."id" )

----

:data-rotate: 20

All companies with no employees (java)
================================================

.. code:: java

	EOQualifier qualifier = new ERXExistsQualifier(
			new ERXTrueQualifier() /*subqualifier*/, 
			Company.EMPLOYEES.key() /*baseKeyPath*/,
			false /*usesInQualInstead*/);
	EOQualifier notQualifier = new ERXNotQualifier(qualifier);
	return Company.fetchCompanies(
		editingContext(), notQualifier, null /*sortOrderings*/);
		
----

:data-rotate: 70

All companies with no employees (SQL)
================================================

.. code:: sql

	SELECT t0."id", t0."NAME" FROM "COMPANY" t0 
	WHERE not ( EXISTS ( 
		SELECT exists0."id" 
		FROM "EMPLOYEE" exists0 
		WHERE 1=1 AND 
			exists0."COMPANY_ID" = t0."id" ) )

----

:data-rotate: 20

Good to Know:
===================

* For to-many relationships, *Exists* is your friend

* For slow to-one relationships, *Exists* can help too

----

:data-rotate: 70

Mantra:
===================

When you think you're stuck... 

*Exists!* will dig you out.

----

:data-rotate: 20

Corollary:
===================

*Exists!* solves many problems but there may come a time you need something else.

*Exists!* will give you a template to make your own qualifier.

The Houdah frameworks are also quite good.

----

:data-rotate: 70

If it is too painful:
=====================

Consider using a pure object Database. 

It might just be for the really tricky part of your business logic that you use an OODB. 


----

:data-rotate: 20

Please take our pull request
=============================

* https://github.com/wocommunity/wonder/pull/541

* https://github.com/recurve/wo_misc

* http://www.chatnbike.com/presentation_existsQualifier

* http://www.chatnbike.com/presentation_existsQualifier/exists_app_example_screen/
