# Java Expression Language

JEL provides the ability to define Java expressions in text form that is parsed at runtime
for execution. These expressions can be stored in a properties file, database or an XML
file to enable modification without requiring a code change. At run-time these expressions 
are retrieved and ```Statement``` objects are created using the ```parse``` 
method. Once created, these objects are evaluated in sequence (by calling its 
```eval(Map)``` method). The Map passed to the ```Statement``` object 
contains attribute or variables required in the evaluation. The evaluation can return 
an object including a Boolean true or false result.

Key features
------------
- Implements the standard Java syntax for operators and flow control. Avoids the use of a
  different code syntax.
- Implements the standard flow control statements (if, for, while, do, switch/case)
- Implements all the standard operators
- Performant expression parsing
- Performant expression evaluation

Maven build
-----------
```
mvn clean install
```

Usage in Java
-------------
A Statement object is first created by parsing an expression string:
```
Statement stmt = Statement.parse("message = \"hello world\";");
```
The statement is then evaluated with a supplied Map which holds expression variables
and can be used in multiple statement evaluations:
```
Map m = new HashMap();
Object result = stmt.eval(m);
```
 
Statement Syntax
----------------
JEL statements follow the basic rules of the Java language but with some limitations (see
Limitations below).
An expression can be given precedence using parentheses (i.e. ( and ) characters),
e.g. ```4 * (3 + 5);```
 
Compound Statements
-------------------
Statements can be compound statements with each statement terminated with a semi-colon.
Unlike single statements, compound statements must be enclosed by curly braces. e.g.
```
    {
        message = "Hello";
        volts = current * resistance;
    }
```

Of course, compound statements can include ```if``` and ```return``` statements: e.g.
```
    {
        if (myobj != null && myobj.rc == 42) {
            message = "hello";
        }
        if (varint == 512) {
            message += "world";
        }
        return true;
    }
```
 
Return
------
The ```return``` statement returns an object for the Statement being evaluated.
It can be a ```true``` or ```false``` Boolean object or a String or the result
of a method call. However, only objects are returned. Primitive values that result
from arithmetic expressions or method calls are returned in their primitive wrappers.

A Statement object does not have to return a value, e.g. it could contain only
assignment statements. Without a ```return``` statement, a Statement evaluation
returns null.

A ```return``` statement within a compound statement exits the Statement.
Any remaining statements are not executed. e.g.
```
    {
        distance = speed * time;
        return "Distance=" + distance;
        volts = current * resistance; // Doesn't get executed.
    }
```
A ```return``` statement without an expression simply exits the statement
and returns null.
 
Comments
--------
Comments can be embedded in the statements. They can be single lined
(using a ```//```) or multi-lined (using ```/*``` and ```*/```). A ```//``` comment must be
terminated with a new-line character, otherwise subsequent lines may be ignored
causing errors.
 
Logical Operators
-----------------
The usual logical operators ```||``` (OR), ```&&``` (AND) and
```!``` (NOT) can be used. The  ```||``` and  ```&&```
operators are also short-circuiting.

<b>Relational Operators</b><br>
All the Java relational operators can be used:
- equality (```==```)
- inequality (```!=```)
- greater than (```>```)
- less than (```<```)
- greater than or equal to (```>=```)
- less than or equal to (```<=```)

Arithmetic Operators
--------------------
Most the usual arithmetic operators are available. The arithmetic operators also follow the
Java precedence rules, e.g. multiplication and division happen before addition and subtraction.
- addition (```+```)
- subtraction (```-```)
- division (```/```)
- modulus (```%```)
- multiplication (```*```)
- Unary minus and plus operators (e.g. ```-4```)
- prefix increment (```++i```)
- prefix decrement (```--i```)
- postfix increment (```i++```)
- postfix decrement (```i--```)
 
Object Attributes and Method Calls
---------------------------------
Objects in the map being evaluated are referenced by name, i.e. the object
of that name is retrieved from the map. An invalid name or the absence of a
map entry produces a null. The test ```(object == null)``` or
```(object != null)``` can be used.

Public object fields can be referenced using the DOT (```.```) operator, e.g.
```object.status```. Also, public object methods can be called, e.g.
with an object named ```lastName``` of type String, the String methods
can be called in the normal way, such as ```lastName.length()``` or
```lastName.equalsIgnoreCase("smith")```.

When an object is referenced that is not present in the workspace map, an 
attempt is made to reference the object in the java.lang and java.util package.  
E.g. the static methods in the Math class can be used directly, e.g.
```
    Math.max(123, 321); 
```
The static methods of classes can be called using the explicit package and 
class name, e.g.
```
    { num = 42; return java.lang.String.valueOf(num); } 
```
Since the String class is in the java.lang package, it can be used directly.
```
    { num = 42; return String.valueOf(num); }
```
Here, a class in the Apache Commons library (and present in the classpath) is used:
```
    return org.apache.commons.lang.StringEscapeUtils.unescapeJava("Hello\tWorld."); 
```
The out print stream of the System class can be used in the usual way:
```
    System.out.println("Hello Will."); 
```
Factory methods can be called to create objects:
```
    return Calendar.getInstance();
```

Object Instantiation
--------------------
The new operator is used to create objects.  The full package and class name can 
be specified to the new operator:
```
    obj = new java.util.ArrayList(7); // ArrayList with an initial size of 7.
```
As before the java.lang or java.util package can be omitted:
```
    obj = new ArrayList(7); 
```
Parameters to class constructors can be supplied:
```
    obj = new StringBuilder("Hello");
```

<b>String Concatenation</b><br>
The arithmetic ```+``` operator is overloaded to concatenate Strings. A string or
string literal can be concatenated with other strings or primitive types. The
```+=``` operator can also be used. Examples:
```
    {
        result = 2 * pi * r;
        message = "Circumference=" + result;
        message += "km";
    }
```

Escaping special characters
---------------------------
When special characters need to be included within strings, they need to be escaped 
in the normal way, e.g.
```
    message = "This is line one\nThis is line two\n\tThis line is tabbed.\";
```
The standard Java special characters are:
- ```\'``` - Single quotation mark
- ```\"``` - Double quotation mark
- ```\\``` - Backslash
- ```\t``` - Tab
- ```\b``` - Backspace
- ```\r``` - Carriage return
- ```\f``` - Formfeed
- ```\n``` - Newline

Assignment
----------
An assignment statement assigns an object value to a reference in the Map by
name. An identifier is therefore a Map entry that is referenced by the name (i.e.
the key) of the Map entry. The entry is added to the Map if it doesn't exist.
e.g.
```
    if (responsetime > threshold) {
        message = \"Response time exceeded \" + threshold + \" seconds.\";
        totaltime += responsetime;
        return false;
    } 
```

Values assigned to 'identifiers' in the Map are available to the caller of the
Statement object and to subsequent Statements objects that are executed. 

The following assignment operators can also be used:<br>
- ```+=```
- ```-=```
- ```/=```
- ```*=```
- ```%=```

When constants are assigned, the variable type assumes the type of the constant, 
e.g.
```
    counter = 12; // counter is an Integer 
    amount = 25.85; // amount is a Double
```
The normal Java numeric suffixes can be used to explicitly type the constant, 

e.g.
```
    distancetosun = 93000000L; // A Long
    smallpi = 3.14F; // A Float
    bigpi = 3.141592653589D; // A Double
```

instanceof operator
-------------------
The instanceof operator can be used to test whether an object is an instance of a 
specified type, e.g.
```
    { dt = new Date(); return dt instanceof Date; } // returns true
```
Since type checking does not exist during compile time, it may be necessary to 
validate an object type before accessing object attributes or making method calls.

? operator
----------
The ternary ? operator evaluates two supplied expressions depending on the result of
the evaluated condition, e.g.
```
    canvote = (age >= 18) ? \"Yes, you can vote!\" : \"No, you can't vote!\";
```
The example results in a canvote value of "No, you can't vote!" if age is less than 18.

Bitwise operators &, |, ^, ~
----------------------------
Bitwise operators & (AND), | (OR), ^ (XOR) and ~ (NOT or complement) can be used in 
expressions. According to Java, they only apply to Byte, Character, Short, Integer and 
Long types. Byte, Character and Short are up cast to Integer. e.g.
```
    i = 5 & 6;   // result is 4
    i = 5 | 6;   // result is 7
    i = 5 ^ 6;   // result is 3
    i = ~6;      // result is -7
```
Bitwise shift operators <<, >> and >>> also exist. The shift multiple is an integer:
```
    i = 12 << 2;   // result is 48
    i = -12 << 2;  // result is -48
    i = 12 >> 2;   // result is 3
    i = -12 >> 2;  // result is -3
    i = 12 >>> 2;  // result is 3
    i = -12 >>> 2; // result is 1073741821
```

For all bitwise operators and shift operators, their assignment operators also exist:
```
    i &= 6;   // if i starts with a value of 5, the resulting value of i is 4
    i |= 6;   // if i starts with a value of 5, the resulting value of i is 7
    i ^= 6;   // if i starts with a value of 5, the resulting value of i is 3
    i <<= 2;  // if i starts with a value of 12, the resulting value of i is 48
    i >>= 2;  // if i starts with a value of 12, the resulting value of i is 3
    i >>>= 2; // if i starts with a value of 12, the resulting value of i is 3
```

if statement
------------
An ```if``` statement begins with the ```if``` keyword followed by a
logical expression, a ```then``` expression and an optional ```else```
expression. The ```then``` expression is evaluated if the condition is
true otherwise the ```else``` expression is evaluated. Both ```then```
and ```else``` expressions can contain a ```return``` statement.
When the ```else``` expression is absent and the condition is not satisfied,
a ```null``` is returned.

An ```if``` statement can be nested and curly braces (i.e. { and }
characters) can be used to delineate code blocks or compound statements.
(Since declarations do not exist, there is no implication of scope.)

while loop
----------
This is a standard while loop where a boolean condition is evaluate and the statement 
block is run zero or more times.  The statement block can contain break, continue or 
return statements. A break or continue to a label is not permitted.  E.g.
```
    {
        i=4;
        while (i>0) {
            msg=msg+\"hello\";
            i--;
        }
    }
```

do-while loop
-------------
A do-while statement executes the statement block at least once and a boolean condition 
is evaluated at the end of the statement block.  The same restriction on break, continue 
and return statements as with the while statement applies.  E.g.
```
    {
        msg = "";
        do {
            msg = msg + "hello";
            if (msg.length() >= 15) {
                break;
            }
        } while (true);
    }
```

for loop
--------
A for loop is made up of four parts:
- initializer - can be an expression or expression list
- termination - an expression
- iteration - can be an expression or expression list
- statement block

Variables are first initialized in the list of expressions in the initializer 
but variable declarations cannot be used. The statement block is executed followed 
by the iteration expression list.  This can increment or decrement variables.  
When the termination expression evaluates to false, the loop ends.  E.g.
```
    {
        msg = "";
        for (i=0, j=3; i<4; i++, j--) {
            msg = msg + "hello " + i + j;
        }
    }
```

switch statement
----------------
A switch statement evaluates a switch expression and executes the statements under 
a matching case label.  The switch/case comparison works with the Byte, Character, Short, 
Integer, Long wrapper classes and the String class. The following example sets dayString 
to "Friday":
```
    {
        day = 6;
        switch (day) {
            case 1:  dayString = "Sunday";
                break;
            case 2:  dayString = "Monday";
                break;
            case 3:  dayString = "Tuesday";
                break;
            case 4:  dayString = "Wednesday";
                break;
            case 5:  dayString = "Thursday";
                break;
            case 6:  dayString = "Friday";
                break;
            case 7:  dayString = "Saturday";
                break;
            default: dayString = "Invalid day";
                break;
        }
    }
```

Without a break statement at each case, the flow falls through to the following case 
labels. In the following, dayString ends up with a value of "ThursdayFridaySaturday":
```
    {
        day = 5;
        dayString = "";
        switch (day) {
            case 1:  dayString = dayString + "Sunday";
            case 2:  dayString = dayString + "Monday";
            case 3:  dayString = dayString + "Tuesday";
            case 4:  dayString = dayString + "Wednesday";
            case 5:  dayString = dayString + "Thursday";
            case 6:  dayString = dayString + "Friday";
            case 7:  dayString = dayString + "Saturday";
                break;
            default:
                break;
        }
    }
```

A switch statement can also have multiple case labels assigned to a case statement.
The following switch statement determines the number of days in a month, e.g. April, 
June, September and November all have 30 days:
```
    {
        month = 6;
        year = 2000;
        dayString = "";
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: 
                numDays = 31;
                break;
            case 4: case 6: case 9: case 11: 
                numDays = 30;
                break;
            case 2: 
                if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0))
                    numDays = 29;
                else
                    numDays = 28;
                break;
            default:
                numDays = 99;
                break;
        }
    }
```

Testing and experimentation
---------------------------
The JELConsole application can be used to try out JEL statements. It has a command line interface
to run expressions, allowing previous commands to be recalled and modified. The show command displays 
the contents of the work map. Type help at the prompt for help. Run it as follows:
```
java -cp .\jel-1.0.jar;.\*; com.github.wtan.jel.JELConsole
```

Limitations
-----------
- No casting
- No variable declaration (not needed since new variables are created automatically)
- No arrays
- No break or continue to a named label within while, do, and for loops. Normal break and continue statements in loops and conditional statements are allowed.</li>
- No synchronize
- No throwing or catching exceptions

More Statement Examples
-----------------------
A simple conditional statement:
```
    if (rc > 9) return false;
```

Referencing an object field:
```
    if (myobj.count > 99) return false;
```

Arithmetic operators:
```
    if ((myobj.linecount / 66) + 1 >= 12) return false;
```

A method call:
```
    if (eReasonCode.startsWith("Framework.manager.001")) return false;
```

An OR condition:
```
    if (environment.equalsIgnoreCase("UNIT") ||
        environment.equalsIgnoreCase("PROJ")) {
        return true;
    } 
```

A nested ```if``` statement:
```
    if (myobj != null) {
        if (rc > myobj.errorlimit && errormsg.length() > 0) {
            return false;
        }
    }
    else {
        return true;
    }
```
